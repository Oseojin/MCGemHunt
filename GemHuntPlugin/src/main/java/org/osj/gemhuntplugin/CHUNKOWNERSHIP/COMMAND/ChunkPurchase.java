package org.osj.gemhuntplugin.CHUNKOWNERSHIP.COMMAND;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.osj.gemhuntplugin.GemHuntPlugin;

import java.util.UUID;

public class ChunkPurchase implements CommandExecutor
{
    private Vector lobbyStartVec = new Vector(20, 0, 20);
    private Vector lobbyEndVec = new Vector(-20, 0 , -20);
    private static TextColor textColor = TextColor.color(255, 255, 255);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;

        if(!player.getWorld().equals(GemHuntPlugin.getWorldManager().getBaseWorld()))
        {
            player.sendMessage(Component.text().color(textColor).content("구매 가능한 월드가 아닙니다.").build());
            return false;
        }

        UUID uuid = player.getUniqueId();
        Chunk currChunk = player.getChunk();

        if(GemHuntPlugin.getChunkManager().containLandAllOfPlayer(currChunk))
        {
            player.sendMessage(Component.text().color(textColor).content("이미 주인이 있는 청크입니다.").build());
            return false;
        }

        if((player.getLocation().getX() <= lobbyStartVec.getX() && player.getLocation().getX() >= lobbyEndVec.getX()) && (player.getLocation().getZ() <= lobbyStartVec.getZ() && player.getLocation().getZ() >= lobbyEndVec.getZ()))
        {
            player.sendMessage(Component.text().color(textColor).content("로비 구역입니다.").build());
            return false;
        }

        GemHuntPlugin.getChunkManager().addLand(uuid, currChunk);
        player.sendMessage(Component.text().color(textColor).content("청크를 성공적으로 구매하였습니다.").build());

        return false;
    }
}
