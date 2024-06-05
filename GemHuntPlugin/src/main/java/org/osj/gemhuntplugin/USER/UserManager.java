package org.osj.gemhuntplugin.USER;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserManager
{
    private HashMap<UUID, User> onlineUserData = new HashMap<>();

    public void addUser(Player player)
    {
        User newUser = new User(
                player.getUniqueId(),
                player.getName()
        );
        onlineUserData.put(player.getUniqueId(), newUser);
    }

    public void removeUser(UUID uuid)
    {
        onlineUserData.remove(uuid);
    }

    public User getUserData(UUID uuid)
    {
        return onlineUserData.get(uuid);
    }
}
