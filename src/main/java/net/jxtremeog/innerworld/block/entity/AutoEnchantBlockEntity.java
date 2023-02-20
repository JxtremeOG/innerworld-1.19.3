package net.jxtremeog.innerworld.block.entity;

import net.jxtremeog.innerworld.item.ModItems;
import net.jxtremeog.innerworld.screen.AutoEnchantScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AutoEnchantBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public AutoEnchantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_ENCHANT_TABLE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return AutoEnchantBlockEntity.this.progress;
                    case 1: return AutoEnchantBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AutoEnchantBlockEntity.this.progress = value; break;
                    case 1: AutoEnchantBlockEntity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 2;
            }
        };
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Auto Enchant Table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AutoEnchantScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("auto_enchant_table.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("auto_enchant_table.progress");
    }
    private void resetProgress() {
        this.progress = 0;
    }
    public static void tick(World world, BlockPos blockPos, BlockState blockState, AutoEnchantBlockEntity entity) {
        if(world.isClient()) {
            return;
        }

        if(hasRecipe(entity)){
            entity.progress++;
            markDirty(world, blockPos, blockState);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        }else {
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }
    }

    private static void craftItem(AutoEnchantBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if(hasRecipe(entity)) {
            entity.removeStack(1, 1);

            entity.setStack(2, new ItemStack(ModItems.TESTITEM,
                    entity.getStack(2).getCount() + 1));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(AutoEnchantBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasRawGemInFirstSlot = entity.getStack(1).getItem() == ModItems.TESTITEM;

        return hasRawGemInFirstSlot && canInsertAmountIntoOutputSlot(inventory, 1)
                && canInsertItemIntoOutputSlot(inventory, ModItems.TESTITEM);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory, int count) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount() + count;
    }

}
