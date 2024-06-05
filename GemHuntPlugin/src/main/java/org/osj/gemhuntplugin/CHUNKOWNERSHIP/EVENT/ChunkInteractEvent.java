package org.osj.gemhuntplugin.CHUNKOWNERSHIP.EVENT;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.osj.gemhuntplugin.GemHuntPlugin;

public class ChunkInteractEvent implements Listener
{
    @EventHandler
    public void onInteractChunk(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();

        if(player.getWorld().equals(GemHuntPlugin.getWorldManager().getBaseWorld()) && !player.isOp())
        {
            if(event.getClickedBlock() == null)
            {
                return;
            }
            Chunk interactChunk = event.getClickedBlock().getChunk();
            // 내 소유의 청크가 아니면서 누군가의 소유인 청크
            if(GemHuntPlugin.getChunkManager().containLandAllOfPlayer(interactChunk) && !GemHuntPlugin.getChunkManager().containLand(player.getUniqueId(), interactChunk))
            {
                event.setCancelled(true);
                player.sendMessage(Component.text().color(TextColor.color(255,255,255)).content("다른 플레이어 소유의 청크입니다."));
            }
        }
    }
}
