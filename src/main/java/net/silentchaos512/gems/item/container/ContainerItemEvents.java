package net.silentchaos512.gems.item.container;

import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.silentchaos512.gems.SilentGems;

@EventBusSubscriber(modid = SilentGems.MOD_ID)
public final class ContainerItemEvents {
    private ContainerItemEvents() {
    }

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        ItemEntity itemEntity = event.getItemEntity();
        ItemStack itemOnGround = itemEntity.getItem();
        if (itemOnGround.isEmpty()) return;

        Player player = event.getPlayer();
        int initialCount = itemOnGround.getCount();

        for (int i = 0; i < player.getInventory().getContainerSize() && !itemOnGround.isEmpty(); ++i) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (invStack.getItem() instanceof IContainerItem containerItem && containerItem.canStore(itemOnGround)) {
                insertIntoContainer(containerItem, invStack, itemOnGround);
            }
        }

        if (itemOnGround.getCount() != initialCount) {
            playPickupSound(player);
        }

        if (itemOnGround.isEmpty()) {
            event.setCanPickup(TriState.FALSE);
            itemEntity.discard();
        }
    }

    private static void insertIntoContainer(IContainerItem containerItem, ItemStack containerStack, ItemStack toInsert) {
        NonNullList<ItemStack> items = containerItem.getItems(containerStack);
        if (tryInsert(items, toInsert)) {
            containerItem.saveItems(containerStack, items);
        }
    }

    private static boolean tryInsert(NonNullList<ItemStack> items, ItemStack toInsert) {
        boolean changed = false;

        // Merge into matching stacks first
        for (int i = 0; i < items.size() && !toInsert.isEmpty(); ++i) {
            ItemStack slotStack = items.get(i);
            if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, toInsert)) {
                int space = slotStack.getMaxStackSize() - slotStack.getCount();
                if (space > 0) {
                    int moved = Math.min(space, toInsert.getCount());
                    slotStack.grow(moved);
                    toInsert.shrink(moved);
                    changed = true;
                }
            }
        }

        // Then fill empty slots
        for (int i = 0; i < items.size() && !toInsert.isEmpty(); ++i) {
            if (items.get(i).isEmpty()) {
                int moved = Math.min(toInsert.getCount(), toInsert.getMaxStackSize());
                ItemStack newStack = toInsert.copy();
                newStack.setCount(moved);
                items.set(i, newStack);
                toInsert.shrink(moved);
                changed = true;
            }
        }

        return changed;
    }

    private static void playPickupSound(Player player) {
        Level level = player.level();
        float pitch = ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F;
        level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, pitch);
    }
}
