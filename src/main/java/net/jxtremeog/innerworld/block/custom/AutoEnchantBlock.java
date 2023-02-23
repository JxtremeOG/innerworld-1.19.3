package net.jxtremeog.innerworld.block.custom;

import net.jxtremeog.innerworld.block.entity.AutoEnchantBlockEntity;
import net.jxtremeog.innerworld.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;


public class AutoEnchantBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public AutoEnchantBlock(Settings settings) {
        super(settings);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //BLOCK ENTITY BELOW

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
    Spawns items that are in container in world when destroyed
     */
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AutoEnchantBlockEntity) {
                ItemScatterer.spawn(world, pos, (AutoEnchantBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    Random random = new Random() {
        @Override
        public Random split() {
            return null;
        }

        @Override
        public RandomSplitter nextSplitter() {
            return null;
        }

        @Override
        public void setSeed(long seed) {

        }

        @Override
        public int nextInt() {
            return 0;
        }

        @Override
        public int nextInt(int bound) {
            return 0;
        }

        @Override
        public long nextLong() {
            return 0;
        }

        @Override
        public boolean nextBoolean() {
            return false;
        }

        @Override
        public float nextFloat() {
            return 0;
        }

        @Override
        public double nextDouble() {
            return 0;
        }

        @Override
        public double nextGaussian() {
            return 0;
        }
    };
    java.util.Random rn = new java.util.Random();

    /**
    Executed when block is right-clicked
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if(hand == Hand.MAIN_HAND && player.getStackInHand(hand).isEnchantable() ){
                List list = Registries.ENCHANTMENT.stream().filter(Enchantment::isAvailableForEnchantedBookOffer).collect(Collectors.toList());
                Enchantment enchantment = null;
                while(enchantment == null || !(enchantment.isAcceptableItem(player.getStackInHand(hand)))){
                    enchantment = (Enchantment)list.get(rn.nextInt(list.size()));
                }
                int maxLevel = 1;
                if(enchantment.getMaxLevel() == 2)
                    maxLevel = 3;
                else if(enchantment.getMaxLevel() == 3)
                    maxLevel = 5;
                else if(enchantment.getMaxLevel() == 4)
                    maxLevel = 7;
                else if(enchantment.getMaxLevel() == 5)
                    maxLevel = 10;

                player.getStackInHand(hand).addEnchantment(enchantment, rn.nextInt(maxLevel)+1);
            }else {
                if(player.getStackInHand(hand).hasEnchantments()){
                    System.out.println(player.getStackInHand(hand).getEnchantments());
                }else {
                    System.out.println("Its not enchantable");
                }
            }
        }

        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AutoEnchantBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.AUTO_ENCHANT_TABLE, AutoEnchantBlockEntity::tick);
    }
}
