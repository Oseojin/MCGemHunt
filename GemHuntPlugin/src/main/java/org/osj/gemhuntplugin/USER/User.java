package org.osj.gemhuntplugin.USER;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.osj.gemhuntplugin.DATAMANAGE.DB_Connect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class User
{
    private UUID uuid;
    private Player player;
    private String name;
    private Integer discovery;
    private Integer plenty;
    private Integer treasure;
    private Integer physical;

    public User(UUID uuid, String name, Integer discovery, Integer plenty, Integer treasure, Integer physical)
    {
        this.uuid = uuid;
        player = Bukkit.getPlayer(uuid);
        this.name = name;
        this.discovery = discovery;
        this.plenty = plenty;
        this.treasure = treasure;
        this.physical = physical;
    }

    public UUID getUUID()
    {
        return uuid;
    }
    public void setUUID(UUID uuid)
    {
        this.uuid = uuid;
        DB_Connect.getInstance().SetUUID(uuid);
    }
    public void loadUUID(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
        DB_Connect.getInstance().SetName(uuid, name);
    }
    public void loadName(String name)
    {
        this.name = name;
    }

    public Integer getDiscovery()
    {
        return discovery;
    }
    public Integer getMaxDiscovery()
    {
        return 100;
    }
    public void setDiscovery(int lv)
    {
        discovery = lv;
        DB_Connect.getInstance().SetStat(uuid, "discovery", lv);
    }
    public void loadDiscovery(int lv)
    {
        discovery = lv;
    }

    public Integer getPlenty()
    {
        return plenty;
    }
    public Integer getMaxPlenty()
    {
        return 40;
    }
    public void setPlenty(int lv)
    {
        plenty = lv;
        DB_Connect.getInstance().SetStat(uuid, "plenty", lv);
    }
    public void loadPlenty(int lv)
    {
        plenty = lv;
    }

    public Integer getTreasure()
    {
        return treasure;
    }
    public Integer getMaxTreasure()
    {
        return 30;
    }
    public void setTreasure(int lv)
    {
        treasure = lv;
        DB_Connect.getInstance().SetStat(uuid, "treasure", lv);
    }
    public void loadTreasure(int lv)
    {
        treasure = lv;
    }

    public Integer getPhysical()
    {
        return physical;
    }
    public Integer getMaxPhysical()
    {
        return 80;
    }
    public void setPhysical(int lv)
    {
        physical = lv;
        DB_Connect.getInstance().SetStat(uuid, "physical", lv);
        applyPhysical();
    }
    public void loadPhysical(int lv)
    {
        physical = lv;
        applyPhysical();
    }
    private void applyPhysical()
    {
        double attackDamge = 1 + (physical * 0.5);
        double maxHeatlh = 20 + (physical * 0.5);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamge);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHeatlh);
    }
}
