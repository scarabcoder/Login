package com.scarabcoder.login.spigot.command;

import com.scarabcoder.login.MySQLManager;
import com.scarabcoder.login.spigot.CachedPlayerData;
import com.scarabcoder.login.spigot.manager.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Anastasia on 7/8/17.
 */
public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            CachedPlayerData d = DataManager.getPlayerData(p.getUniqueId());
            if(d.getHashedPassword() == null) {
                if (args.length == 1) {
                    String hashedPass = BCrypt.hashpw(args[0], BCrypt.gensalt());
                    d.setHashedPassword(hashedPass);

                    Connection c = MySQLManager.getConnection();

                    try {
                        PreparedStatement ps = c.prepareStatement("UPDATE users SET hashedPass=? WHERE uuid=?");
                        ps.setString(1, hashedPass);
                        ps.setString(2, p.getUniqueId().toString());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    p.sendMessage(ChatColor.GREEN + "Registered successfully!");


                } else {
                    p.sendMessage(ChatColor.RED + "Usage: /register <password>");
                }
            }else{
                p.sendMessage(ChatColor.RED + "Already registered! Login with /login <password>.");
            }
        }

        return true;
    }
}
