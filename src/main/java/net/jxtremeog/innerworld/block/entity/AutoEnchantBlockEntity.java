package net.jxtremeog.innerworld.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jxtremeog.innerworld.networking.ModMessages;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class AutoEnchantBlockEntity extends BlockEntity {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int progress = 0;
    private final int maxProgress = 60;

    public AutoEnchantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_ENCHANT_TABLE, pos, state);
    }
    public DefaultedList<ItemStack> getItemsinTable() {
        return this.inventory;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory, true);
        nbt.putInt("auto_enchant_table.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory.clear();
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("auto_enchant_table.progress");
    }
    private void resetProgress() {
        this.progress = 0;
    }
    public static void tick(World world, BlockPos blockPos, BlockState blockState, AutoEnchantBlockEntity entity) {
        if(world.isClient()) {
            return;
        }
        if(entity.inventory.get(0) != ItemStack.EMPTY){
            entity.progress++;
            markDirty(world, blockPos, blockState);
            if(entity.progress >= entity.maxProgress) {
                entity.popOutOfInventory();
                entity.resetProgress();
            }
        }else {
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }
    }


    @Override
    public void markDirty() {
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    public void addToInventory(ItemStack stack, BlockPos blockPos, BlockState blockState){
        this.inventory.set(0, stack);
        markDirty(world, blockPos, blockState);
    }

    public void popOutOfInventory(){
        ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY()+1, (double)pos.getZ(), this.inventory.get(0));
    }

}
