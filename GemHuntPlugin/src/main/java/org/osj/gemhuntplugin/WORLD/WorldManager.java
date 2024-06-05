package org.osj.gemhuntplugin.WORLD;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager
{
    private World baseWorld;
    private World aMinuteAgoWorld;
    private World hiddenWorld;
    private int maxMine = 5;

    public WorldManager()
    {
        baseWorld = Bukkit.getWorld("base");
        aMinuteAgoWorld = Bukkit.getWorld("a_minute_ago");
        hiddenWorld = Bukkit.getWorld("hidden");
    }

    public World getBaseWorld()
    {
        return baseWorld;
    }
    public World getaMinuteAgoWorld()
    {
        return aMinuteAgoWorld;
    }
    public World getHiddenWorld()
    {
        return hiddenWorld;
    }
}
