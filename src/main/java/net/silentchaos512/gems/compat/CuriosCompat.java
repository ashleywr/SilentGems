package net.silentchaos512.gems.compat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.silentchaos512.gems.setup.GemsItems;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Curios API integration. This class must never be referenced unless the "curios" mod is
 * loaded - referencing it triggers classloading, which would throw NoClassDefFoundError if
 * Curios (an optional dependency) isn't present.
 */
public final class CuriosCompat {
    private CuriosCompat() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> (ICurio) () -> stack,
                GemsItems.GEM_BAG.get(), GemsItems.FLOWER_BASKET.get());
    }

    /**
     * Runs the given action for every item stack currently worn in a curio slot.
     */
    public static void forEachEquipped(Player player, BiConsumer<SlotResult, ItemStack> action) {
        Optional<ICuriosItemHandler> handler = CuriosApi.getCuriosInventory(player);
        if (handler.isEmpty()) return;

        for (SlotResult result : handler.get().findCurios(s -> true)) {
            action.accept(result, result.stack());
        }
    }

    /**
     * Writes a mutated stack back to a specific curio slot result.
     */
    public static void saveEquipped(Player player, SlotResult slotResult, ItemStack stack) {
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.setEquippedCurio(
                slotResult.slotContext().identifier(), slotResult.slotContext().index(), stack));
    }

    /**
     * Finds a stack of the given item currently worn in a curio slot, if any.
     */
    public static ItemStack findEquipped(Player player, Class<?> itemClass) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(s -> itemClass.isInstance(s.getItem())))
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }

    /**
     * Writes a mutated stack back to whichever curio slot currently holds the given item, if any.
     * A curio slot's stack isn't guaranteed to be a live reference the way a hand/inventory slot
     * is, so this must be called explicitly rather than relying on in-place mutation.
     */
    public static void saveEquipped(Player player, Class<?> itemClass, ItemStack stack) {
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findFirstCurio(s -> itemClass.isInstance(s.getItem())).ifPresent(result ->
                        handler.setEquippedCurio(result.slotContext().identifier(), result.slotContext().index(), stack)));
    }
}
