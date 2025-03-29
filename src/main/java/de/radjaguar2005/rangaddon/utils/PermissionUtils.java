package de.radjaguar2005.rangaddon.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PermissionUtils {

    public static void addPermission(String Rang, String Perm) {
        CloudNetDriver.getInstance().getPermissionManagement().modifyGroup(Rang, permissionGroup -> {
            permissionGroup.removePermission(Perm); //removes a permission
        });
    }

    public static void removePermission(String Rang, String Perm) {
        CloudNetDriver.getInstance().getPermissionManagement().modifyGroup(Rang, permissionGroup -> {
            permissionGroup.removePermission(Perm); //removes a permission
        });
    }

    public static void addGroup(ProxiedPlayer player, String Rang, String Time) {
        if (Time.equalsIgnoreCase("lifetime")) {
            CloudNetDriver.getInstance().getPermissionManagement().modifyUser(player.getUniqueId(), permissionUser -> {
                permissionUser.addGroup(Rang);
            });
        } else {
            CloudNetDriver.getInstance().getPermissionManagement().modifyUser(player.getUniqueId(), permissionUser -> {
                permissionUser.addGroup(Rang, Long.parseLong(Time), TimeUnit.DAYS);
            });
        }
    }

    public static void removeGroup(ProxiedPlayer player, String Rang) {
        CloudNetDriver.getInstance().getPermissionManagement().modifyUser(player.getUniqueId(), permissionUser -> {
            permissionUser.removeGroup(Rang);
        });
    }
}
