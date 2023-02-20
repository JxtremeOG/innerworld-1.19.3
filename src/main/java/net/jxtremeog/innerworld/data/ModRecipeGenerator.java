package net.jxtremeog.innerworld.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jxtremeog.innerworld.block.ModBlocks;
import net.jxtremeog.innerworld.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModItems.TESTITEM, RecipeCategory.DECORATIONS,
                ModBlocks.TEST_BLOCK);

        //EXAMPLE
//        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_CITRINE)
//                .pattern("SSS")
//                .pattern("SCS")
//                .pattern("SSS")
//                .input('S', Items.STONE)
//                .input('C', ModItems.CITRINE)
//                .criterion(FabricRecipeProvider.hasItem(Items.STONE),
//                        FabricRecipeProvider.conditionsFromItem(Items.STONE))
//                .criterion(FabricRecipeProvider.hasItem(ModItems.CITRINE),
//                        FabricRecipeProvider.conditionsFromItem(ModItems.CITRINE))
//                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RAW_CITRINE)));
    }
}
