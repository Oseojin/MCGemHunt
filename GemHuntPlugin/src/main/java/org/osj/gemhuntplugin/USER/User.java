package org.osj.gemhuntplugin.USER;

import org.bukkit.Bukkit;
import org.osj.gemhuntplugin.DATAMANAGE.DB_Connect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class User
{
    private UUID uuid;
    private String name;

    public User(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
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
}
