package com.scarabcoder.login.spigot.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.scarabcoder.login.spigot.listener.LoginListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Anastasia on 7/8/17.
 */
public class TestCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("IsLoggedIn");
            out.writeUTF(p.getUniqueId().toString());

            //p.sendPluginMessage(LoginListener.getPlugin(), "Login", out.toByteArray());
            p.sendMessage(ChatColor.GREEN + "Sent plugin message.");

        }

        return true;
    }
}
