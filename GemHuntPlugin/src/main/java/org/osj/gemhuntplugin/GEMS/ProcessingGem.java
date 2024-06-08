package org.osj.gemhuntplugin.GEMS;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Clamp
{
    double clamp(double value, double min, double max)
    {
        return Math.max(min, Math.min(max, value));
    }
}

public class ProcessingGem implements Listener
{
    @EventHandler
    public void onProcessGem(CraftItemEvent event)
    {
        CraftingInventory inventory = event.getInventory();
        ItemStack itemStack = inventory.getResult();
        CustomStack customStack = CustomStack.byItemStack(itemStack);
        if(customStack == null) // 커스텀 아이템이 아닌 경우
        {
            return;
        }
        if(!customStack.getPermission().equals("ia.gemhunt:processed")) // 가공된 보석이 아닌경우
        {
            return;
        }
        Random random = new Random();
        Clamp clamp = new Clamp();
        int purity = (int)(clamp.clamp(random.nextGaussian(), 0.0, 2.0) * 50); // 1 ~ 100%

        String color = "";
        switch (purity)
        {
            case 2:
                color = ChatColor.WHITE + "";
                break;
            case 4:
                color = ChatColor.GREEN + "";
                break;
            case 6:
                color = ChatColor.AQUA + "";
                break;
            case 8:
                color = ChatColor.LIGHT_PURPLE + "";
                break;
            case 10:
                color = ChatColor.RED + "";
                break;
        }

        List<String> lore = new ArrayList<>();
        lore.add(color + "[순도]\t" + purity + "%");
        itemStack.setLore(lore);
    }
}
