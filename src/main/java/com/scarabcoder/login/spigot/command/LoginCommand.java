package com.scarabcoder.login.spigot.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.scarabcoder.login.CachedPlayerData;
import com.scarabcoder.login.spigot.LoginSpigot;
import com.scarabcoder.login.spigot.manager.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mindrot.jbcrypt.BCrypt;

/**
 * /login command for players
 */
public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        //
        //    Will use BCrypt to compare pass argument with stored (hashed) password in the cached user info
        //

        if(sender instanceof Player){
            Player p = (Player) sender;
            CachedPlayerData d = DataManager.getPlayerData(p.getUniqueId());
            if(d.getHashedPassword() != null) {
                if (!LoginSpigot.getLoginManager().isLoggedIn(p.getUniqueId())) {
                    if (args.length == 1) {
                        if(BCrypt.checkpw(args[0], d.getHashedPassword())){
                            p.sendMessage(ChatColor.GREEN + "Logged in successfully.");
                            LoginSpigot.getLoginManager().setLoggedIn(p.getUniqueId(), true);

                            ByteArrayDataOutput out = ByteStreams.newDataOutput();

                            out.writeUTF("SetLoggedIn");
                            out.writeUTF(p.getUniqueId().toString());
                            out.writeBoolean(true);
                            p.sendPluginMessage(LoginSpigot.getPlugin(), "Login", out.toByteArray());

                        }else{
                            p.sendMessage(ChatColor.RED + "Incorrect password!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Usage: /login <password>");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Already logged in!");
                }
            }else{
                p.sendMessage(ChatColor.RED + "You must register with /register <password> before you can login!");
            }
        }

        return true;
    }
}
