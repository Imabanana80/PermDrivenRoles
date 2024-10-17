package com.imabanana80.permDrivenRoles;

import com.imabanana80.permDrivenRoles.command.RolesCommand;
import com.imabanana80.permDrivenRoles.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermDrivenRoles extends JavaPlugin {

    private static PermDrivenRoles instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("roles").setExecutor(new RolesCommand());
    }

    public static PermDrivenRoles getInstance() {return instance;}
}
