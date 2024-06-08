package org.osj.gemhuntplugin.GEMS;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.*;

public class GemManager
{
    private static HashMap<Material, Pair<String, Double>> mineralGemStoneHashMap = new HashMap<>();
    private static HashMap<Material, Pair<String, Double>> farmGemStoneHashMap = new HashMap<>();
    private static HashMap<Material, String> fishingGemStoneHashMap = new HashMap<>();
    private static HashMap<List<Material>, List<Pair<String, Double>>> woodGemStoneHashMap = new HashMap<>();
    private static HashMap<EntityType, Pair<String, Double>> huntingGemStoneHashMap = new HashMap<>();
    public GemManager()
    {
        initMineralGemStoneHashMap();
        initFarmGemStoneHashMap();
        initFishingGemStoneHashMap();
        initWoodGemStoneHashMap();
        initHuntingGemStoneHashMap();
    }

    private void initMineralGemStoneHashMap()
    {
        mineralGemStoneHashMap.put(Material.COBBLESTONE, Pair.of("stone_crystal", 0.05));
        mineralGemStoneHashMap.put(Material.COAL, Pair.of("scapolite", 0.01));
        mineralGemStoneHashMap.put(Material.RAW_COPPER, Pair.of("topaz", 0.01));
        mineralGemStoneHashMap.put(Material.RAW_IRON, Pair.of("silver", 0.01));
        mineralGemStoneHashMap.put(Material.RAW_GOLD, Pair.of("platinum", 0.01));
        mineralGemStoneHashMap.put(Material.GOLD_NUGGET, Pair.of("platinum_nugget", 0.0025));
        mineralGemStoneHashMap.put(Material.REDSTONE, Pair.of("ruby", 0.001));
        mineralGemStoneHashMap.put(Material.LAPIS_LAZULI, Pair.of("sapphire", 0.001));
        mineralGemStoneHashMap.put(Material.DIAMOND, Pair.of("black_diamond", 0.005));
        mineralGemStoneHashMap.put(Material.EMERALD, Pair.of("emerald_blue", 0.005));
        mineralGemStoneHashMap.put(Material.QUARTZ, Pair.of("red_crystal", 0.05));
        mineralGemStoneHashMap.put(Material.AMETHYST_SHARD, Pair.of("gold_crystal", 0.05));
        mineralGemStoneHashMap.put(Material.ANCIENT_DEBRIS, Pair.of("onyx", 0.1));
    }

    private void initFarmGemStoneHashMap()
    {
        farmGemStoneHashMap.put(Material.WHEAT, Pair.of("wheat_seed_crystal", 0.005));
        farmGemStoneHashMap.put(Material.CARROTS, Pair.of("carrot_crystal", 0.005));
        farmGemStoneHashMap.put(Material.POTATOES, Pair.of("potato_crystal", 0.005));
        farmGemStoneHashMap.put(Material.BEETROOTS, Pair.of("beet_seed_crystal", 0.005));
        farmGemStoneHashMap.put(Material.COCOA, Pair.of("cocoa_bean_crystal", 0.005));
        farmGemStoneHashMap.put(Material.PUMPKIN, Pair.of("pumpkin_seed_crystal", 0.001));
        farmGemStoneHashMap.put(Material.MELON, Pair.of("melon_seed_crystal", 0.001));
    }

    private void initFishingGemStoneHashMap()
    {
        fishingGemStoneHashMap.put(Material.BOW, "pearl");
        fishingGemStoneHashMap.put(Material.ENCHANTED_BOOK, "fluorite");
        fishingGemStoneHashMap.put(Material.FISHING_ROD, "aquamarine");
        fishingGemStoneHashMap.put(Material.NAME_TAG, "shell");
        fishingGemStoneHashMap.put(Material.NAUTILUS_SHELL, "ammonite");
        fishingGemStoneHashMap.put(Material.SADDLE, "coral");
    }

    private void initWoodGemStoneHashMap()
    {
        List<Material> overworldWoodList = new ArrayList<>();
        overworldWoodList.add(Material.OAK_LOG);
        overworldWoodList.add(Material.SPRUCE_LOG);
        overworldWoodList.add(Material.BIRCH_LOG);
        overworldWoodList.add(Material.JUNGLE_LOG);
        overworldWoodList.add(Material.ACACIA_LOG);
        overworldWoodList.add(Material.DARK_OAK_LOG);
        overworldWoodList.add(Material.MANGROVE_LOG);
        overworldWoodList.add(Material.CHERRY_LOG);

        List<Pair<String, Double>> overworldWoodGemList = new ArrayList<>();
        overworldWoodGemList.add(Pair.of("amber", 0.02));
        overworldWoodGemList.add(Pair.of("amber_mosquito", 0.01));
        overworldWoodGemList.add(Pair.of("amber_dragonfly", 0.01));
        overworldWoodGemList.add(Pair.of("amber_beetle", 0.01));
        overworldWoodGemList.add(Pair.of("amber_jade_green", 0.005));
        overworldWoodGemList.add(Pair.of("amber_blue", 0.005));
        overworldWoodGemList.add(Pair.of("jet", 0.0025));

        woodGemStoneHashMap.put(overworldWoodList, overworldWoodGemList);

        List<Material> crimsonWoodList = new ArrayList<>();
        crimsonWoodList.add(Material.CRIMSON_STEM);

        List<Pair<String, Double>> crimsonWoodGemList = new ArrayList<>();
        crimsonWoodGemList.add(Pair.of("amber_crimson", 0.01));

        woodGemStoneHashMap.put(crimsonWoodList, crimsonWoodGemList);

        List<Material> warpedWoodList = new ArrayList<>();
        warpedWoodList.add(Material.WARPED_STEM);

        List<Pair<String, Double>> warpedWoodGemList = new ArrayList<>();
        warpedWoodGemList.add(Pair.of("amber_warped", 0.01));

        woodGemStoneHashMap.put(warpedWoodList, warpedWoodGemList);
    }

    private void initHuntingGemStoneHashMap()
    {
        huntingGemStoneHashMap.put(EntityType.SKELETON, Pair.of("calculus", 0.01));
        huntingGemStoneHashMap.put(EntityType.WITHER_SKELETON, Pair.of("calculus_black", 0.01));
        huntingGemStoneHashMap.put(EntityType.OCELOT, Pair.of("catseye", 0.05));
        huntingGemStoneHashMap.put(EntityType.RAVAGER, Pair.of("ivory", 0.1));
        huntingGemStoneHashMap.put(EntityType.GOAT, Pair.of("reinforce_horn", 0.02));
        huntingGemStoneHashMap.put(EntityType.ELDER_GUARDIAN, Pair.of("none", 0.0));
        huntingGemStoneHashMap.put(EntityType.ENDER_DRAGON, Pair.of("dragon_heart", 0.5));
        huntingGemStoneHashMap.put(EntityType.WITHER, Pair.of("octagon", 0.25));
    }

    public Pair<String, Double> getMineralGemStone(Material targetMaterial)
    {
        if(mineralGemStoneHashMap.containsKey(targetMaterial))
        {
            return mineralGemStoneHashMap.get(targetMaterial);
        }
        return null;
    }

    public Pair<String, Double> getFarmGemStone(Material targetMaterial)
    {
        if(farmGemStoneHashMap.containsKey(targetMaterial))
        {
            return farmGemStoneHashMap.get(targetMaterial);
        }
        return null;
    }

    public String getFishingGemStone(Material targetMaterial)
    {
        if(fishingGemStoneHashMap.containsKey(targetMaterial))
        {
            return fishingGemStoneHashMap.get(targetMaterial);
        }
        return null;
    }

    public String getFishingGemStoneRandom()
    {
        Random random = new Random();
        int fishingGemListSize = fishingGemStoneHashMap.size();
        int randomIndex = random.nextInt(fishingGemListSize);
        return fishingGemStoneHashMap.values().stream().toList().get(randomIndex);
    }

    public List<Pair<String, Double>> getWoodGemStone(Material targetMaterial)
    {
        Iterator<List<Material>> keys = woodGemStoneHashMap.keySet().iterator();
        while(keys.hasNext())
        {
            List<Material> listKey = keys.next();
            if(listKey.contains(targetMaterial))
            {
                return woodGemStoneHashMap.get(listKey);
            }
        }

        return null;
    }

    public Pair<String, Double> getHuntingGemStone(EntityType targetType)
    {
        if(huntingGemStoneHashMap.containsKey(targetType))
        {
            return huntingGemStoneHashMap.get(targetType);
        }
        return null;
    }
}
