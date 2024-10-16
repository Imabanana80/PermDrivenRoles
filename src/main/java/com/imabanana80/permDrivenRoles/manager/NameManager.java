package com.imabanana80.permDrivenRoles.manager;

import com.imabanana80.permDrivenRoles.PermDrivenRoles;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashSet;
import java.util.Set;

public class NameManager {
    public static void loadRoles(Player player){
        Set<String> permissions = new HashSet<>();
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            if (!permission.getValue()){continue;}
            permissions.add(permission.getPermission());
        }
        for (String permission : permissions) {
            if (!permission.startsWith("permrole.")){continue;}
            String role = permission.replaceFirst("permrole.", "");
            System.out.println(role);
            String display = PermDrivenRoles.getInstance().getConfig().getString("roles." + role + ".display");
            String tab = PermDrivenRoles.getInstance().getConfig().getString("roles." + role + ".tab");
            if (display == null) {break;}
            display = display.replace("{playername}", player.getName());
            display = display.replace("&", "§");
            if (tab == null) {tab = display;}
            tab = tab.replace("{playername}", player.getName());
            tab = tab.replace("&", "§");
            player.setDisplayName(display);
            player.setPlayerListName(tab);
        }
    }
}
