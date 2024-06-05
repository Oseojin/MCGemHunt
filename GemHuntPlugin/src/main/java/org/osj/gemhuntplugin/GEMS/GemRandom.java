package org.osj.gemhuntplugin.GEMS;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class GemRandom implements Listener
{
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // 석탄, 구리, 철, 금, 청금석, 레드스톤, 다이아, 에메랄드, 네더석영, 네더 금, 네더라이트 -> 2개씩 총 22종의 원석
        switch (block.getType())
        {
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                break;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                break;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                break;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                break;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                break;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                break;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                break;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                break;
            case NETHER_QUARTZ_ORE:
                break;
            case NETHER_GOLD_ORE:
                break;
            case ANCIENT_DEBRIS:
                break;
        }

        // 오버월드 나무(호박 7종), 빨간 나무(호박 3종), 파란 나무(호박 3종) -> 총 13종의 호박

        // 우는 흑요석 -> 마력량 표시
    }

    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent event)
    {
        // 밀, 딩근, 감자, 홍당무, 코코아콩, 수박, 호박 -> 2개씩 총 14종의 빛바랜 보석
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event)
    {
        // 보물들을 보석으로 대체 -> 2개씩 총 12 종의 부식된 보석
    }

    @EventHandler
    public void onKillMonster(EntityDeathEvent event)
    {
        // 좀비, 스켈레톤, 크리퍼, 엔더맨, 좀벌레, 슬라임, 좀비 피그맨, 피글린, 난폭한 피글린, 가스트, 위더 스켈레톤, 블레이즈, 마그마 큐브, 셜커, 엔더 드래곤, 위더 -> 1개씩 총 16종의 오염된 보석
    }
}
