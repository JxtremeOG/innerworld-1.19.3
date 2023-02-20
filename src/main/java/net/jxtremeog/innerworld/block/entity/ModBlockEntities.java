package net.jxtremeog.innerworld.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jxtremeog.innerworld.InnerWorld;
import net.jxtremeog.innerworld.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<AutoEnchantBlockEntity> AUTO_ENCHANT_TABLE;

    public static void registerBlockEntities() {
        AUTO_ENCHANT_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(InnerWorld.MOD_ID, "auto_enchant_table"),
                FabricBlockEntityTypeBuilder.create(AutoEnchantBlockEntity::new,
                        ModBlocks.AUTO_ENCHANT_BLOCK).build(null));
    }
}
