package com.scarabcoder.login.spigot;

import com.scarabcoder.login.CachedPlayerData;
import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.spigot.command.LoginCommand;
import com.scarabcoder.login.spigot.command.RegisterCommand;
import com.scarabcoder.login.spigot.listener.*;
import com.scarabcoder.login.spigot.manager.DataManager;
import com.scarabcoder.login.spigot.manager.LoginManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

import static java.awt.SystemColor.text;

/**
 * Main plugin class
 */
public class LoginSpigot extends JavaPlugin {

    private static Plugin plugin;

    private static LoginManager lm;

    public void onEnable(){

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        //
        //    Get the MySQL connection data from the config
        //

        String host = this.getConfig().getString("mysql.host");
        String port = this.getConfig().getString("mysql.port");
        String database = this.getConfig().getString("mysql.database");
        String user = this.getConfig().getString("mysql.user");
        String password = this.getConfig().getString("mysql.password");

        try {
            MySQLManager.startConnection(host, port, user, password, database);
            MySQLManager.initializeDatabases();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        plugin = this;
        lm = new LoginManager();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "Login");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "Login", new PluginMessageListener());
        this.registerListeners();

        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());

    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListeners(), this);

    }

    public static LoginManager getLoginManager(){
        return lm;
    }

    public static Plugin getPlugin(){
        return plugin;
    }


    /**
     * Send the login notification players get when they aren't logged in.
     * @param p Player to be sent to
     */
    public static void sendLoginNotification(Player p){

        CachedPlayerData d = DataManager.getPlayerData(p.getUniqueId());

        String subtitle = "/register <password> then /login <password>";

        if(d.getHashedPassword() != null){
            subtitle = "/login <password>";
        }

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "Please Login to Play!" + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        IChatBaseComponent subtitleText = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\",color:" + ChatColor.WHITE.name().toLowerCase() + "}");

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleText);
        PacketPlayOutTitle length = new PacketPlayOutTitle(0, 60, 5);


        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitlePacket);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);

        //p.sendTitle(ChatColor.RED + "Please Login to Play!", subtitle, 0, 60, 5);

    }

}
