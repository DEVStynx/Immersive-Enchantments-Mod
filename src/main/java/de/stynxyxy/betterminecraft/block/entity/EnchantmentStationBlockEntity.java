package de.stynxyxy.betterminecraft.block.entity;

import de.stynxyxy.betterminecraft.recipe.EnchantmentStationRecipe;
import de.stynxyxy.betterminecraft.screen.EnchantmentStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EnchantmentStationBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemhandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public EnchantmentStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.Enchantment_STATION.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EnchantmentStationBlockEntity.this.progress;
                    case 1 -> EnchantmentStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> EnchantmentStationBlockEntity.this.progress = pValue;
                    case 1 -> EnchantmentStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Enchantment Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player pPlayer) {
        return new EnchantmentStationMenu(id,inventory,this,this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemhandler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemhandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemhandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemhandler.getSlots());
        for (int i = 0; i < itemhandler.getSlots(); i++) {
            inventory.setItem(i, itemhandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state,EnchantmentStationBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        if (hasRecipe(entity)) {
            entity.progress++;
            setChanged(level,blockPos,state);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level,blockPos,state);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(EnchantmentStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemhandler.getSlots());
        for (int i = 0; i < entity.itemhandler.getSlots(); i++) {
            inventory.setItem(i,entity.itemhandler.getStackInSlot(i));
        }

        Optional<EnchantmentStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(EnchantmentStationRecipe.Type.INSTANCE, inventory, level);
        if (hasRecipe(entity)) {
            entity.itemhandler.extractItem(1,1,false);
            entity.itemhandler.setStackInSlot(2,new ItemStack(recipe.get().getResultItem(RegistryAccess.EMPTY).getItem(),entity.itemhandler.getStackInSlot(2).getCount() +1));
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(EnchantmentStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemhandler.getSlots());
        for (int i = 0; i < entity.itemhandler.getSlots(); i++) {
            inventory.setItem(i,entity.itemhandler.getStackInSlot(i));
        }

        Optional<EnchantmentStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(EnchantmentStationRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertAmountInntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(RegistryAccess.EMPTY));

    }

    private static boolean canInsertAmountInntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory,ItemStack itemstack) {
        return inventory.getItem(2).getItem() == itemstack.getItem() || inventory.getItem(2).isEmpty();
    }
}
