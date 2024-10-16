package com.imabanana80.permDrivenRoles.listener;

import com.imabanana80.permDrivenRoles.PermDrivenRoles;
import com.imabanana80.permDrivenRoles.manager.NameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(PermDrivenRoles.getInstance(), task -> {
            NameManager.loadRoles(player);
        }, 5);
    }
}
