package com.scarabcoder.login.bungeecord.listener;

import com.scarabcoder.login.CachedPlayerData;
import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.UUIDUtil;
import com.scarabcoder.login.bungeecord.LoginBungeeCord;
import com.scarabcoder.login.bungeecord.LoginManager;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Init and register player data
 */
public class PlayerLoginListener implements Listener {


    //
    //    Handle the registration and initiation of offline-mode users.
    //    PreLoginEvent only returns username (no UUID), and is the only time an internal UUID can be set.
    //
    @EventHandler
    public void onPrePlayerLogin(PreLoginEvent e){

        CachedPlayerData data = null;

        Connection c = MySQLManager.getConnection();

        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE username=?");

            ps.setString(1, e.getConnection().getName());

            ResultSet st = ps.executeQuery();

            if(st.next()){
                data = new CachedPlayerData(UUID.fromString(st.getString("uuid")), st.getString("username"), st.getString("hashedPass"), st.getBoolean("premium"));
                if(!e.getConnection().isOnlineMode())
                    e.getConnection().setUniqueId(UUID.fromString(st.getString("uuid")));
            }else{

                boolean isPremium = e.getConnection().isOnlineMode();

                if(!isPremium) {

                    UUID uuid = UUID.randomUUID();

                    ps = c.prepareStatement("INSERT INTO users (uuid, username, premium) VALUES (?, ?, ?)");
                    ps.setString(1, uuid.toString());
                    ps.setString(2, e.getConnection().getName());
                    ps.setBoolean(3, isPremium);

                    data = new CachedPlayerData(uuid, e.getConnection().getName(), null, isPremium);

                    ps.executeUpdate();
                    e.getConnection().setUniqueId(uuid);
                    LoginBungeeCord.getLoginManager().setLoggedIn(uuid, false);
                }
            }


        } catch (SQLException e1) {
            e1.printStackTrace();
        }


    }


    //
    //    Handle the registration and initiation for online-mode users.
    //    Must be done as soon as the UUID is present (LoginEvent), and only for online mode players.
    //
    @EventHandler
    public void playerLogin(LoginEvent e){
        if(e.getConnection().isOnlineMode()){
            Connection c = MySQLManager.getConnection();
            try {
                PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE uuid=?");
                ps.setString(1, e.getConnection().getUniqueId().toString());

                ResultSet st = ps.executeQuery();
                if(!st.next()){
                    ps = c.prepareStatement("INSERT INTO users (uuid, username, premium) VALUES (?, ?, ?)");
                    ps.setString(1,e.getConnection().getUniqueId().toString());
                    ps.setString(2, e.getConnection().getName());
                    ps.setBoolean(3, true);
                    ps.executeUpdate();
                    LoginBungeeCord.getLoginManager().setLoggedIn(e.getConnection().getUniqueId(), true);

                }else{
                    LoginBungeeCord.getLoginManager().setLoggedIn(e.getConnection().getUniqueId(), false);

                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
