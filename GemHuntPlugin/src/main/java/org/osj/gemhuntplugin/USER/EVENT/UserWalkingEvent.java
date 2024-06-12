package org.osj.gemhuntplugin.USER.EVENT;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.gemhuntplugin.GEMS.GemRandom;
import org.osj.gemhuntplugin.GemHuntPlugin;
import org.osj.gemhuntplugin.USER.User;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class UserWalkingEvent implements Listener
{
    private HashMap<UUID, Integer> growCoolDown = new HashMap<>();
    @EventHandler
    public void onUserWalking(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        User user = GemHuntPlugin.getUserManager().getUserData(player.getUniqueId());
        ItemStack tool = player.getInventory().getItemInMainHand();

        switch (tool.getType()) // 다이아 호미, 네더라이트 호미만 적용
        {
            case DIAMOND_HOE:
            case NETHERITE_HOE:
                break;
            default:
                return;
        }
        int plenty = user.getPlenty();

        if(plenty == 0)
        {
            return;
        }

        int cooldown;

        if(!growCoolDown.containsKey(player.getUniqueId()))
        {
            growCoolDown.put(player.getUniqueId(), 0);
            cooldown = 0;
        }
        else
        {
            cooldown = growCoolDown.get(player.getUniqueId()) + 1;
            growCoolDown.put(player.getUniqueId(), cooldown);
        }

        if(cooldown < 5)
        {
            return;
        }
        else
        {
            growCoolDown.put(player.getUniqueId(), 0);
        }

        int enchantLevel = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        double growthChance = ((plenty * 0.5) + enchantLevel) / 100.0;

        Random random = new Random();
        double randomValue = random.nextDouble();

        if(randomValue > growthChance) // 확률 아니면 리턴
        {
            return;
        }

        Location locBlock = event.getTo().clone().add(0, 1, 0);
        Block cropList[][] = new Block[3][3];

        for(int x = -1; x <= 1; x++)
        {
            for(int z = -1; z <= 1; z++)
            {
                cropList[x+1][z+1] = locBlock.clone().add(x, 0, z).getBlock();
            }
        }

        if(plenty >= 20) // 풍요 레벨 20 이상은 1칸이 아니라 3*3 총 9칸 성장
        {
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(GemHuntPlugin.getGemManager().getFarmGemStone(cropList[i][j].getType()) == null) // 농작물 블럭 아니면 리턴
                    {
                        continue;
                    }
                    Ageable crop = (Ageable) cropList[i][j].getBlockData();
                    if(crop.getAge() >= crop.getMaximumAge()) // 다 자란 블럭이면 리턴
                    {
                        if(plenty >= 30) // 30 레벨 이상은 수확하고 다시 심기까지 활성화
                        {

                        }
                        continue;
                    }
                    crop.setAge(crop.getAge()+1);
                    cropList[i][j].setBlockData(crop);
                    player.sendMessage(growthChance + " " + crop.getAge() + " " + (i*3 + j + 1) + " " + cropList[i][j].getLocation());
                    GemRandom.randomCrop(cropList[i][j]);
                }
            }
        }
        else
        {
            if(GemHuntPlugin.getGemManager().getFarmGemStone(cropList[1][1].getType()) == null) // 농작물 블럭 아니면 리턴
            {
                return;
            }
            Ageable crop = (Ageable) cropList[1][1].getBlockData();
            if(crop.getAge() >= crop.getMaximumAge()) // 다 자란 블럭이면 리턴
            {
                return;
            }
            crop.setAge(crop.getAge()+1);
            cropList[1][1].setBlockData(crop);
            player.sendMessage(growthChance + " " + crop.getAge());
            GemRandom.randomCrop(cropList[1][1]);
        }
    }
}
