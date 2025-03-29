package de.radjaguar2005.rangaddon;


import de.radjaguar2005.rangaddon.api.DiscordWebhook;
import de.radjaguar2005.rangaddon.commands.Group;
import de.radjaguar2005.rangaddon.commands.Perms;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.JsonConfiguration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;


public final class RangAddon extends Plugin {

    private static RangAddon instance;
    private Configuration config;

    private static String prefix = "#00FFE0R#0CF7DDa#18EEDAn#24E6D7k#31DDD4A#3DD5D0d#49CCCDd#55C4CAo#61BBC7n §7» §r";



    @Override
    public void onEnable() {
        instance = this;
        loadConfig();

        getProxy().getPluginManager().registerCommand(this, new Group());
        getProxy().getPluginManager().registerCommand(this, new Perms());
    }

    private void loadConfig() {

        // Lade die Konfiguration
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            File configFile = new File(getDataFolder(), "config.json");

            if (!configFile.exists()) {
                try (InputStream is = getResourceAsStream("config.json")) {
                    Files.copy(is, configFile.toPath());
                }
            }

            config = ConfigurationProvider.getProvider(JsonConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().severe("The RangAddOn Plugin is Disable!");
    }

    public static RangAddon getInstance() {return instance;};

    public static String getPrefix() {
        return prefix;
    }

    public Configuration getConfig() {
        return config;
    }
}
