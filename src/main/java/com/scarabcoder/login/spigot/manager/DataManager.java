package com.scarabcoder.login.spigot.manager;

import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.CachedPlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Store and refresh cached data from the database.
 * Should only be used by other Spigot plugins, not proxy.
 */
public class DataManager {

    private static HashMap<UUID, CachedPlayerData> pData = new HashMap<UUID, CachedPlayerData>();

    public static CachedPlayerData getPlayerData(UUID id){
        return pData.get(id);
    }

    public static void refreshCache(UUID id){
        Connection c = MySQLManager.getConnection();

        try {
            System.out.println(id.toString());
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE uuid=?");
            ps.setString(1, id.toString());

            ResultSet st = ps.executeQuery();
            System.out.println(st.getFetchSize());

            if(st.next()){
                CachedPlayerData data = new CachedPlayerData(id, st.getString("username"), st.getString("hashedPass"), st.getBoolean("premium"));
                pData.put(id, data);
                System.out.println("Grabbed cached data for UUID " + id.toString() + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
