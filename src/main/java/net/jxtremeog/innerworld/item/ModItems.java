package net.jxtremeog.innerworld.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jxtremeog.innerworld.InnerWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TESTITEM = registerItem("test_item",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(InnerWorld.MOD_ID, name), item);
    }

    public static void addItemstoItemGroup(){
        addToItemGroup(ModItemGroup.INNERWORLD, TESTITEM);
    }

    private static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems(){
        InnerWorld.LOGGER.info("Registering Mod Items for " + InnerWorld.MOD_ID);

        addItemstoItemGroup();
    }
}
