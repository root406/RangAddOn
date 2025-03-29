package de.radjaguar2005.rangaddon.commands;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.radjaguar2005.rangaddon.RangAddon;
import de.radjaguar2005.rangaddon.api.ColorAPI;
import de.radjaguar2005.rangaddon.utils.PermissionUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class Perms extends Command {

    public Perms() {
        super("perm", "", "permission");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            Configuration config = RangAddon.getInstance().getConfig();
            if (args.length < 3) {
                // Ungenügende Argumente, sende eine Usage-Nachricht
                sender.sendMessage(config.getString("PermsUsage"));
                return;
            }

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            String subCommand = args[0];
            String group = args[1];

            IPermissionManagement permissionManagement = CloudNetDriver.getInstance().getPermissionManagement();


            switch (subCommand.toLowerCase()) {
                case "add":
                    if (args.length < 3) {
                        // Ungenügende Argumente, sende eine Usage-Nachricht
                        sender.sendMessage(config.getString("Perms.Add.Usage"));
                        return;
                    }
                    String setperm = args[2];
                    if (sender.hasPermission("rangaddon.perm.add")) {
                        if (permissionManagement.getGroups().stream().anyMatch(g -> g.getName().equalsIgnoreCase(group))) {
                            PermissionUtils.addPermission(group, setperm);
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Add.AddPermMessage").replace("%rank%", group).replace("%perm%", setperm)));
                        } else {
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Add.RankNotFound")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Add.NoPermission")));
                    }
                    break;
                case "remove":
                    if (args.length < 3) {
                        // Ungenügende Argumente, sende eine Usage-Nachricht
                        sender.sendMessage(config.getString("Perms.Remove.Usage"));
                        return;
                    }
                    String removeperm = args[2];
                    if (sender.hasPermission("rangaddon.perm.remove")) {
                        if (permissionManagement.getGroups().stream().anyMatch(g -> g.getName().equalsIgnoreCase(group))) {
                            PermissionUtils.removePermission(group, removeperm);
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Remove.RemovePermMessage").replace("%perm%", removeperm).replace("%rank%", group)));
                        } else {
                            sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Remove.RankNotFound")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.formatHexColor(config.getString("Prefix") + config.getString("Perms.Remove.NoPermission")));
                    }
                    break;
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
