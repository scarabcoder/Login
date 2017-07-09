package com.scarabcoder.login.bungeecord;

import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.bungeecord.listener.PlayerLoginListener;
import com.scarabcoder.login.bungeecord.listener.PluginMessageListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class LoginBungeeCord extends Plugin {


    private static HashMap<UUID, Boolean> loggedIn = new HashMap<UUID, Boolean>();

    private static LoginManager loginManager;

    public void onEnable(){

        if(!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        File configFile = new File(this.getDataFolder(), "config.yml");

        if(!configFile.exists()){
            try {
                Files.copy(this.getResourceAsStream("config.yml"), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loginManager = new LoginManager();

        Configuration config = null;

        try {
            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String host = config.getString("mysql.host");
        String port = config.getString("mysql.port");
        String database = config.getString("mysql.database");
        String user = config.getString("mysql.user");
        String password = config.getString("mysql.password");

        try {
            MySQLManager.startConnection(host, port, user, password, database);
            MySQLManager.initializeDatabases();
        } catch (SQLException e) {
            e.printStackTrace();

        }




        this.getProxy().registerChannel("Login");
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
        this.getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());




    }

    public static LoginManager getLoginManager(){
        return loginManager;
    }

}
