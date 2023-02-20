package net.jxtremeog.innerworld.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jxtremeog.innerworld.InnerWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup INNERWORLD;

    public static void registerItemGroups(){
        INNERWORLD = FabricItemGroup.builder(new Identifier(InnerWorld.MOD_ID, "test_item"))
                .displayName(Text.translatable("itemgroup.innerworld"))
                .icon(() -> new ItemStack(ModItems.TESTITEM)).build();
    }
}
