package org.osj.gemhuntplugin.WORLD;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager
{
    private World baseWorld;
    private int maxMine = 5;

    public WorldManager()
    {
        baseWorld = Bukkit.getWorld("base");
    }

    public World getBaseWorld()
    {
        return baseWorld;
    }
}
