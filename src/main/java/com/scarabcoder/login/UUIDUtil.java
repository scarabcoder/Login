package com.scarabcoder.login;

import com.google.common.base.Charsets;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Anastasia on 7/8/17.
 */
public class UUIDUtil {

    public static boolean isPremium(ProxiedPlayer player){

        return isPremium(player.getUniqueId(), player.getName());

    }

    public static boolean isPremium(UUID id, String username){
        UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
        return !offlineUUID.equals(id);
    }

    public static boolean isPremium(Player player){
        return isPremium(player.getUniqueId(), player.getName());
    }

}
