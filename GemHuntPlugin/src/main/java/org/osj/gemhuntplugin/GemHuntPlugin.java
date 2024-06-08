package org.osj.gemhuntplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.osj.gemhuntplugin.CHUNKOWNERSHIP.COMMAND.ChunkPurchase;
import org.osj.gemhuntplugin.CHUNKOWNERSHIP.COMMAND.ChunkRemove;
import org.osj.gemhuntplugin.CHUNKOWNERSHIP.ChunkManager;
import org.osj.gemhuntplugin.CHUNKOWNERSHIP.EVENT.ChunkInteractEvent;
import org.osj.gemhuntplugin.DATAMANAGE.ConfigManager;
import org.osj.gemhuntplugin.GEMS.ProcessingGem;
import org.osj.gemhuntplugin.GEMS.GemManager;
import org.osj.gemhuntplugin.GEMS.GemRandom;
import org.osj.gemhuntplugin.USER.COMMAND.AddWhiteListCommand;
import org.osj.gemhuntplugin.USER.EVENT.UserJoinEvent;
import org.osj.gemhuntplugin.USER.UserManager;
import org.osj.gemhuntplugin.WORLD.WorldManager;

public final class GemHuntPlugin extends JavaPlugin
{
    private static GemHuntPlugin serverInstance;
    private static UserManager userManager;
    private static ConfigManager configManager;
    private static ChunkManager chunkManager;
    private static WorldManager worldManager;
    private static GemManager gemManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;
        configManager = new ConfigManager();
        worldManager = new WorldManager();
        userManager = new UserManager();
        chunkManager = new ChunkManager();
        gemManager = new GemManager();

        registerEvent();
        registerCommand();
    }

    private void registerEvent()
    {
        getServer().getPluginManager().registerEvents(new UserJoinEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new ChunkInteractEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new GemRandom(), serverInstance);
        getServer().getPluginManager().registerEvents(new ProcessingGem(), serverInstance);
    }
    private void registerCommand()
    {
        serverInstance.getServer().getPluginCommand("purchaseChunk").setExecutor(new ChunkPurchase());
        serverInstance.getServer().getPluginCommand("removeChunk").setExecutor(new ChunkRemove());
        serverInstance.getServer().getPluginCommand("invite").setExecutor(new AddWhiteListCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GemHuntPlugin getServerInstance()
    {
        return serverInstance;
    }
    public static UserManager getUserManager()
    {
        return userManager;
    }
    public static ConfigManager getConfigManager()
    {
        return configManager;
    }
    public static ChunkManager getChunkManager()
    {
        return chunkManager;
    }
    public static WorldManager getWorldManager()
    {
        return worldManager;
    }
    public static GemManager getGemManager()
    {
        return gemManager;
    }
}
