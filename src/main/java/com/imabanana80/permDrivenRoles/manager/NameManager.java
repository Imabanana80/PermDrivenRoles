package com.imabanana80.permDrivenRoles.manager;

import com.imabanana80.permDrivenRoles.PermDrivenRoles;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public class NameManager {
    public static void loadRoles(Player player){
        Set<String> permissions = new HashSet<>();
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if (!permission.getValue()){continue;}
            permissions.add(permission.getPermission());
        }
        HashMap<String, Integer> possibleRoles = new HashMap<>();
        for (String permission : permissions) {
            if (!permission.startsWith("permrole.")){continue;}
            String role = permission.replaceFirst("permrole.", "");
            possibleRoles.put(role, PermDrivenRoles.getInstance().getConfig().getInt("roles." + role + ".priority"));
        }

        int highestPriority = -1;
        String highestRole = null;
        for (String role : possibleRoles.keySet()) {
            int priority = possibleRoles.get(role);
            String display = PermDrivenRoles.getInstance().getConfig().getString("roles." + role + ".display");
            if (display == null) {continue;}
            if (priority <= highestPriority) {continue;}
            highestPriority = priority;
            highestRole = role;
        }

        if (highestRole == null) {return;}
        String display = PermDrivenRoles.getInstance().getConfig().getString("roles." + highestRole + ".display");
        String tab = PermDrivenRoles.getInstance().getConfig().getString("roles." + highestRole + ".tab");
        assert display != null;
        display = display.replace("{playername}", player.getName());
        display = display.replace("&", "§");
        if (tab == null) {tab = display;}
        tab = tab.replace("{playername}", player.getName());
        tab = tab.replace("&", "§");
        player.setDisplayName(display);
        player.setPlayerListName(tab);
    }
}
