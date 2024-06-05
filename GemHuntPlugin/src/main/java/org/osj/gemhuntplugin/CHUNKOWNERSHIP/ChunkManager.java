package org.osj.gemhuntplugin.CHUNKOWNERSHIP;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.osj.gemhuntplugin.GemHuntPlugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ChunkManager
{
    FileConfiguration chunkConfig = GemHuntPlugin.getConfigManager().getConfig("chunkownership");
    private HashMap<UUID, List<Long>> userChunkDataMap = new HashMap<>();

    public ChunkManager()
    {
        loadFromConfig();
    }

    public void addLand(UUID uuid, Chunk chunk)
    {
        if(userChunkDataMap.get(uuid) == null)
        {
            List<Long> newChunkList = new LinkedList<>();
            newChunkList.add(chunk.getChunkKey());
            userChunkDataMap.put(uuid, newChunkList);
        }
        else
        {
            userChunkDataMap.get(uuid).add(chunk.getChunkKey());
        }
        chunkConfig.set("chunks." + chunk.getChunkKey(), uuid.toString());
        GemHuntPlugin.getConfigManager().saveConfig("chunkownership");
    }

    private void loadFromConfig()
    {
        // Config에 청크 키 값들 저장해서 해시맵에 다시 다 집어넣기
        if(chunkConfig.getConfigurationSection("chunks.") == null)
        {
            return;
        }

        List<String> configChunkKeyList = chunkConfig.getConfigurationSection("chunks.").getKeys(true).stream().toList();
        for(int i = 0; i < configChunkKeyList.size(); i++)
        {
            UUID uuid = UUID.fromString(chunkConfig.getString("chunks." + configChunkKeyList.get(i)));
            Chunk chunk = GemHuntPlugin.getWorldManager().getBaseWorld().getChunkAt(Long.parseLong(configChunkKeyList.get(i)));
            addLand(uuid, chunk);
        }
    }

    public void removeLand(UUID uuid, Chunk chunk)
    {
        if(!containLand(uuid, chunk))
        {
            return;
        }

        userChunkDataMap.get(uuid).remove(chunk.getChunkKey());
        chunkConfig.set("chunks." + chunk.getChunkKey(), null);
        GemHuntPlugin.getConfigManager().saveConfig("chunkownership");
    }

    public boolean containLand(UUID player, Chunk chunk)
    {
        if(!userChunkDataMap.containsKey(player) || !userChunkDataMap.get(player).contains(chunk.getChunkKey()))
        {
            return false;
        }

        return true;
    }

    public boolean containLandAllOfPlayer(Chunk chunk)
    {
        for(UUID key : userChunkDataMap.keySet())
        {
            if(userChunkDataMap.get(key).contains(chunk.getChunkKey()))
            {
                return true;
            }
        }
        return false;
    }
}
