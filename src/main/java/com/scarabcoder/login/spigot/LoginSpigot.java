package com.scarabcoder.login.spigot;

import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.spigot.command.LoginCommand;
import com.scarabcoder.login.spigot.command.RegisterCommand;
import com.scarabcoder.login.spigot.listener.*;
import com.scarabcoder.login.spigot.manager.DataManager;
import com.scarabcoder.login.spigot.manager.LoginManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

/**
 * Created by Anastasia on 7/8/17.
 */
public class LoginSpigot extends JavaPlugin {

    private static Plugin plugin;

    private static LoginManager lm;

    public void onEnable(){

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

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
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);

    }

    public static LoginManager getLoginManager(){
        return lm;
    }

    public static Plugin getPlugin(){
        return plugin;
    }

    public static void sendLoginNotification(Player p){

        CachedPlayerData d = DataManager.getPlayerData(p.getUniqueId());

        String subtitle = "/register <password> then /login <password>";

        if(d.getHashedPassword() != null){
            subtitle = "/login <password>";
        }

        p.sendTitle(ChatColor.RED + "Please Login to Play!", subtitle, 5, 60, 5);

    }

}
