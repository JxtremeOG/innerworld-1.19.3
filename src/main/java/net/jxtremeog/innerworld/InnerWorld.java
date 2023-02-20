package net.jxtremeog.innerworld;

import net.fabricmc.api.ModInitializer;

import net.jxtremeog.innerworld.block.ModBlocks;
import net.jxtremeog.innerworld.item.ModItemGroup;
import net.jxtremeog.innerworld.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InnerWorld implements ModInitializer {
	public static final String MOD_ID = "innerworld";
	public static final Logger LOGGER = LoggerFactory.getLogger("innerworld");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}