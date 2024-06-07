package org.osj.gemhuntplugin.USER.EVENT;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.osj.gemhuntplugin.DATAMANAGE.DB_Connect;
import org.osj.gemhuntplugin.GemHuntPlugin;
import org.osj.gemhuntplugin.USER.User;

import java.util.UUID;

public class UserJoinEvent implements Listener
{
    @EventHandler
    public void onUserLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName().toLowerCase();

        if(!GemHuntPlugin.getConfigManager().getConfig("whitelist").contains("players."+playerName))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "아쉽게도 아직 초대받지 못했습니다.");
        }
    }
    @EventHandler
    public void onUserJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        GemHuntPlugin.getUserManager().addUser(player);
        GemHuntPlugin.getServerInstance().getLogger().info("플레이어 데이터 저장");

        if(DB_Connect.getInstance().insertMember(player) != 0)
        {
            player.kick(Component.text().content("데이터베이스에서 정보를 로드중 오류가 발생 했습니다. " + DB_Connect.getInstance().insertMember(player)).build());
            return;
        }
        User joinUser = DB_Connect.getInstance().DB_PlayerInfo(uuid);
        // PlayerScoreBoard.setScoreboard(player);

        int physicalLv = joinUser.getPhysical();
        double attackDamge = 1 + (physicalLv * 0.5);
        double maxHeatlh = 20 + (physicalLv * 0.5);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamge);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHeatlh);
    }

    @EventHandler
    public void onUserQuitFromServer(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        GemHuntPlugin.getUserManager().removeUser(uuid);
        GemHuntPlugin.getServerInstance().getLogger().info("플레이어 데이터 삭제");
    }
}
