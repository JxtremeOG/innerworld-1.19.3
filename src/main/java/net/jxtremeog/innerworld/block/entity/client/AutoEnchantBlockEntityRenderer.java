package net.jxtremeog.innerworld.block.entity.client;

import net.jxtremeog.innerworld.block.custom.AutoEnchantBlock;
import net.jxtremeog.innerworld.block.entity.AutoEnchantBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class AutoEnchantBlockEntityRenderer implements BlockEntityRenderer<AutoEnchantBlockEntity> {
    public AutoEnchantBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }
    @Override
    public void render(AutoEnchantBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(0.5f, 0.645f, 1f);
        matrices.scale(0.2f, 0.2f, 0.2f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(-90));

        switch (entity.getCachedState().get(AutoEnchantBlock.FACING)) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(180));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(270));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(0));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(90));
        }
    }
}
