package org.osj.gemhuntplugin.GEMS;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.gemhuntplugin.GemHuntPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GemRandom implements Listener
{
    private static List<Material> mineralList = new ArrayList<>();
    private static List<Material> woodList = new ArrayList<>();
    private static List<Material> cropList = new ArrayList<>();
    public GemRandom()
    {
        initMineralList();
        initWoodList();
        initCropList();
    }
    private void initMineralList()
    {
        mineralList.add(Material.STONE);
        mineralList.add(Material.DEEPSLATE_COAL_ORE);
        mineralList.add(Material.COPPER_ORE);
        mineralList.add(Material.DEEPSLATE_COPPER_ORE);
        mineralList.add(Material.IRON_ORE);
        mineralList.add(Material.DEEPSLATE_IRON_ORE);
        mineralList.add(Material.GOLD_ORE);
        mineralList.add(Material.DEEPSLATE_GOLD_ORE);
        mineralList.add(Material.NETHER_GOLD_ORE);
        mineralList.add(Material.REDSTONE_ORE);
        mineralList.add(Material.DEEPSLATE_REDSTONE_ORE);
        mineralList.add(Material.LAPIS_ORE);
        mineralList.add(Material.DEEPSLATE_LAPIS_ORE);
        mineralList.add(Material.DIAMOND_ORE);
        mineralList.add(Material.DEEPSLATE_DIAMOND_ORE);
        mineralList.add(Material.EMERALD_ORE);
        mineralList.add(Material.DEEPSLATE_EMERALD_ORE);
        mineralList.add(Material.ANCIENT_DEBRIS);
    }
    private void initWoodList()
    {
        woodList.add(Material.OAK_LOG);
        woodList.add(Material.SPRUCE_LOG);
        woodList.add(Material.BIRCH_LOG);
        woodList.add(Material.JUNGLE_LOG);
        woodList.add(Material.ACACIA_LOG);
        woodList.add(Material.DARK_OAK_LOG);
        woodList.add(Material.MANGROVE_LOG);
        woodList.add(Material.CHERRY_LOG);
        woodList.add(Material.CRIMSON_STEM);
        woodList.add(Material.WARPED_STEM);
    }
    private void initCropList()
    {
        cropList.add(Material.WHEAT);
        cropList.add(Material.CARROTS);
        cropList.add(Material.POTATOES);
        cropList.add(Material.BEETROOTS);
        cropList.add(Material.COCOA);
    }

    @EventHandler
    public void onMineralDropItem(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        Bukkit.getConsoleSender().sendMessage("광물 " + block.getType());

        if(!mineralList.contains(block.getType())) // 지정된 블럭이 아니면 리턴
        {
            Bukkit.getConsoleSender().sendMessage("광물 아님");
            return;
        }

        List<ItemStack> dropItemList = block.getDrops().stream().toList();
        Material dropItemMaterial = dropItemList.get(0).getType();

        Pair<String, Double> mineralPair = GemHuntPlugin.getGemManager().getMineralGemStone(dropItemMaterial);
        if(mineralPair == null) // 해당 아이템 아니면 리턴
        {
            return;
        }

        Bukkit.getConsoleSender().sendMessage("광물페어 " + mineralPair);

        String itemID = mineralPair.left(); // 커스텀 아이템 네임스페이스 id
        double probability = mineralPair.right() + tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS); // 커스텀 아이템 등장 확률
        int gemNum = 0;
        Random random = new Random();
        double selectedNum = random.nextDouble();
        Bukkit.getConsoleSender().sendMessage("행운레벨: " + tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + " 현재확률: " + probability);

        if(selectedNum <= probability)
        {
            gemNum++;
        }

        if(gemNum == 0) // 안뽑혔으면 리턴
        {
            return;
        }

        /*CustomStack gemCustomStack = CustomStack.getInstance("gemhunt:"+itemID);
        ItemStack gemItemStack = gemCustomStack.getItemStack();
        gemItemStack.setAmount(gemNum);

        block.getWorld().dropItem(event.getItems().get(0).getLocation(), gemItemStack);*/
    }

    @EventHandler
    public void onWoodDropItem(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        Bukkit.getConsoleSender().sendMessage("나무 " + block.getType());

        if(!woodList.contains(block.getType())) // 해당 아이템 아니면 리턴
        {
            Bukkit.getConsoleSender().sendMessage("나무 아님");
            return;
        }

        Material material = block.getType();

        List<Pair<String, Double>> woodPairList = GemHuntPlugin.getGemManager().getWoodGemStone(material);
        if(woodPairList == null) // 해당 아이템 아니면 리턴
        {
            return;
        }
        List<String> woodGemRandomList = new ArrayList<>(); // 리스트 순서의 확률 문제 때문에 뽑힌 애들을 모아서 한 번 더 랜덤 추출

        Bukkit.getConsoleSender().sendMessage("나무페어 " + woodPairList);

        Random random = new Random();

        for(int i = 0; i < woodPairList.size(); i++)
        {
            String itemID = woodPairList.get(i).left();
            double probability = woodPairList.get(i).right() + tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            Bukkit.getConsoleSender().sendMessage("행운레벨: " + tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + " 현재확률: " + probability);
            double selectedNum = random.nextDouble();

            if(selectedNum <= probability)
            {
                woodGemRandomList.add(itemID);
            }
        }

        if(woodGemRandomList.isEmpty()) // 아무것도 안뽑혔으면 리턴
        {
            return;
        }

        /*String selectedItemID = woodGemRandomList.get(random.nextInt(woodGemRandomList.size()));
        CustomStack gemCustomStack = CustomStack.getInstance("gemhunt:"+selectedItemID);
        ItemStack gemItemStack = gemCustomStack.getItemStack();

        block.getWorld().dropItem(block.getLocation(), gemItemStack);*/
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event)
    {
        // 밀, 딩근, 감자, 홍당무, 코코아콩, 수박, 호박 -> 2개씩 총 14종의 빛바랜 보석
        Block crop = event.getBlock();
        Bukkit.getConsoleSender().sendMessage("작물 " + crop.getType());
        if(!cropList.contains(crop.getType())) // 해당 아이템 아니면 리턴
        {
            Bukkit.getConsoleSender().sendMessage("작물 아님");
            return;
        }

        Pair<String, Double> cropPair = GemHuntPlugin.getGemManager().getFarmGemStone(crop.getType());
        if(cropPair == null) // 해당 아이템 아니면 리턴
        {
            return;
        }

        Random random = new Random();

        Bukkit.getConsoleSender().sendMessage("작물페어 " + cropPair);

        String cropID = cropPair.left();
        double probability = cropPair.right();
        double selectedNum = random.nextDouble();

        Bukkit.getConsoleSender().sendMessage("확률 " + selectedNum);


        if(selectedNum > probability) // 안뽑히면 리턴
        {
            return;
        }

        new BukkitRunnable() // 임시로 나온거
        {
            @Override
            public void run()
            {
                crop.getWorld().getBlockAt(crop.getLocation()).setType(Material.EMERALD_BLOCK);
            }
        }.runTaskLater(GemHuntPlugin.getServerInstance(), 1L);

        //CustomBlock.place("gemhunt:" + cropID, crop.getLocation());
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event)
    {
        // 보물들을 보석으로 대체 -> 2개씩 총 12 종의 부식된 보석
        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH)
        {
            Item CaughtItem = (Item) event.getCaught();
            Material caughtMaterial = CaughtItem.getItemStack().getType();
            String gemID = GemHuntPlugin.getGemManager().getFishingGemStone(caughtMaterial);
            if(gemID == null)
            {
                return;
            }

            event.getPlayer().sendMessage("" + gemID);

            /*CustomStack gemCustomStack = CustomStack.getInstance("gemhunt:"+gemID);
            ItemStack gemItemStack = gemCustomStack.getItemStack();

            CaughtItem.setItemStack(gemItemStack);*/
        }
    }

    @EventHandler
    public void onKillMonster(EntityDeathEvent event)
    {
        // 좀비, 스켈레톤, 크리퍼, 엔더맨, 좀벌레, 슬라임, 좀비 피그맨, 피글린, 난폭한 피글린, 가스트, 위더 스켈레톤, 블레이즈, 마그마 큐브, 셜커, 엔더 드래곤, 위더 -> 1개씩 총 16종의 오염된 보석
    }
}
