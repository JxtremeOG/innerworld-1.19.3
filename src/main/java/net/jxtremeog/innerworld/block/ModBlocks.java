package net.jxtremeog.innerworld.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jxtremeog.innerworld.InnerWorld;
import net.jxtremeog.innerworld.block.custom.AutoEnchantBlock;
import net.jxtremeog.innerworld.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block TEST_BLOCK = registerBlock("test_block",
            new Block(FabricBlockSettings.of(Material.METAL)
                    .strength(4.0f).requiresTool()),
            ModItemGroup.INNERWORLD);
    public static final Block AUTO_ENCHANT_BLOCK = registerBlock("auto_enchant_block",
            new AutoEnchantBlock(FabricBlockSettings.of(Material.METAL)
                    .strength(4.0f).requiresTool()),
            ModItemGroup.INNERWORLD);

    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(InnerWorld.MOD_ID, name),block);
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        Item item = Registry.register(Registries.ITEM, new Identifier(InnerWorld.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }
    public static void registerModBlocks(){
        InnerWorld.LOGGER.info("Registering ModBlocks for " + InnerWorld.MOD_ID);
    }
}
