package net.jxtremeog.innerworld;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.jxtremeog.innerworld.data.ModLootTableGenerator;
import net.jxtremeog.innerworld.data.ModModelProvider;
import net.jxtremeog.innerworld.data.ModRecipeGenerator;

public class InnerWorldDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModModelProvider::new);
	}
}
