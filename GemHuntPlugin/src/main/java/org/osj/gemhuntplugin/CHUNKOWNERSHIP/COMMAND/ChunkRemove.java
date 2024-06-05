package org.osj.gemhuntplugin.CHUNKOWNERSHIP.COMMAND;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.gemhuntplugin.GemHuntPlugin;

import java.util.UUID;

public class ChunkRemove implements CommandExecutor
{
    private static TextColor textColor = TextColor.color(255, 255, 255);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        Chunk currChunk = player.getChunk();

        if(!player.getWorld().equals(GemHuntPlugin.getWorldManager().getBaseWorld()))
        {
            player.sendMessage(Component.text().color(textColor).content("제거 가능한 월드가 아닙니다.").build());
            return false;
        }

        if(!GemHuntPlugin.getChunkManager().containLand(uuid, currChunk))
        {
            player.sendMessage(Component.text().color(textColor).content("소유중인 청크가 아닙니다.").build());
            return false;
        }

        GemHuntPlugin.getChunkManager().removeLand(uuid, currChunk);
        player.sendMessage(Component.text().color(textColor).content("청크를 성공적으로 제거하였습니다.").build());

        return false;
    }
}
