package de.radjaguar2005.rangaddon.commands;

import com.google.gson.JsonObject;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.radjaguar2005.rangaddon.RangAddon;
import de.radjaguar2005.rangaddon.api.ColorAPI;
import de.radjaguar2005.rangaddon.utils.PermissionUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.*;

public class Group extends Command {

    public Group() {
        super("group", "", "rank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            Configuration config = RangAddon.getInstance().getConfig();
            if (args.length < 2) {
                // Ungenügende Argumente, sende eine Usage-Nachricht
                sender.sendMessage(config.getString("GroupUsage"));
                return;
            }

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            String subCommand = args[0];

            IPermissionManagement permissionManagement = CloudNetDriver.getInstance().getPermissionManagement();
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);



            switch (subCommand.toLowerCase()) {
                case "add":
                    if (args.length < 4) {
                        sender.sendMessage(config.getString("Rank.Add.Usage"));
                        return;
                    }
                    String addGroup = args[2];


                    if (sender.hasPermission("rangaddon.rang.add")) {
                        if (target != null && target.isConnected()) {
                            if (permissionManagement.getGroups().stream().anyMatch(g -> g.getName().equalsIgnoreCase(addGroup))) {
                                String time = args[3];
                                PermissionUtils.addGroup(target, addGroup, time);
                                sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.GiveRankMessage").replace("%target%", target.getName()).replace("%rank%", addGroup)));
                                if (config.getBoolean("Rank.Add.KickPlayer.Kick")) {
                                    target.disconnect(config.getString("Rank.Add.KickPlayer.Message"));
                                } else {
                                    target.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.NewRank")));
                                }
                            } else {
                                sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.RankNotFound")));
                            }
                        } else  {
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.PlayerNotOnline")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.NoPermission")));
                    }
                    break;
                case "remove":
                    if (args.length < 3) {
                        sender.sendMessage(config.getString("Rank.Remove.Usage"));
                        return;
                    }
                    String removeGroup = args[2];
                    if (sender.hasPermission("rangaddon.rang.remove")) {
                        if (permissionManagement.getGroups().stream().anyMatch(g -> g.getName().equalsIgnoreCase(removeGroup))) {
                            if (target != null) {
                                PermissionUtils.removeGroup(target, removeGroup);
                                sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Remove.RemoveRankMessage").replace("%target%", target.getName()).replace("%rank%", removeGroup)));
                            }
                        } else {
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Remove.RankNotFound")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Rank.Add.NoPermission")));
                    }
                    break;
                // TODO Info Command
                // Name des Spielers
                // Rang des Spielers
                // Rang länge des Spielers
                /*case "info":
                    String getGroup = args[2];
                    if (sender.hasPermission("rangaddon.rang.info")) {
                        if (target != null) {
                            sender.sendMessage(ColorAPI.formatHexColor(RangAddon.getPrefix() + "§f" + target.getName()));
                            IPermissionUser  permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(target.getUniqueId());
                            if (permissionUser != null) {
                                IPermissionGroup highestGroup = CloudNetDriver.getInstance().getPermissionManagement().getFirstUserAsync(permissionUser )
                                if (h)
                            }
                        }
                    }*/
                default:
                    handleUnknownSubcommand(sender, subCommand);
                    break;
            }
        }
    }


    private void handleUnknownSubcommand(CommandSender sender, String subCommand) {
        sender.sendMessage("Unknown Subcommand: " + subCommand);
    }
}
