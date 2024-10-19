package com.imabanana80.permDrivenRoles.command;

import com.imabanana80.permDrivenRoles.PermDrivenRoles;
import com.imabanana80.permDrivenRoles.manager.NameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class RolesCommand implements CommandExecutor, TabCompleter {
    //roles reloadall|reload|set |<player>|<role> ||tab|display ||<format>|<format>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            if (args.length == 0) {player.sendMessage(ChatColor.RED + "Incomplete command!");}
            if (args[0].equals("reloadall")) {
                for (Player target : Bukkit.getOnlinePlayers()){
                    NameManager.loadRoles(target);
                }
                return true;
            }
            if (args.length < 2) {player.sendMessage(ChatColor.RED + "Incomplete command!");}
            if (args[0].equals("reload")){
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {player.sendMessage(ChatColor.RED + "Unknown or invalid player!");return true;}
                NameManager.loadRoles(target);
                return true;
            }
            if (args.length < 4) {player.sendMessage(ChatColor.RED + "Incomplete command!");return true;}
            if (args[0].equals("set")) {
                String role = args[1];
                if (args[2].equals("priority")) {
                    String priorityArg = args[3];
                    try {
                        Integer priority = Integer.parseInt(priorityArg);
                        PermDrivenRoles.getInstance().getConfig().set("roles." + role + "." + args[2], priority);
                        PermDrivenRoles.getInstance().saveConfig();
                        return true;
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Invalid arguments!");
                        return true;
                    }
                }
                StringBuilder formatBuilder = new StringBuilder();
                for(int i = 3; i < args.length; i++){
                    formatBuilder.append(" ").append(args[i]);
                }
                String format = formatBuilder.toString().replaceFirst(" ", "");;
                if (args[2].equals("tab") || args[2].equals("display")) {
                    PermDrivenRoles.getInstance().getConfig().set("roles." + role + "." + args[2], format);
                    PermDrivenRoles.getInstance().saveConfig();
                    return true;
                }
            }
            player.sendMessage(ChatColor.RED + "Invalid arguments!");
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("reloadall", "reload", "set");
        }
        if (args.length == 2) {
            if (args[0].equals("reload")) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
        }
        if (args.length == 3) {
            if (args[0].equals("set")) {
                return List.of("tab", "display", "priority");
            }
        }
        return List.of();
    }
}
