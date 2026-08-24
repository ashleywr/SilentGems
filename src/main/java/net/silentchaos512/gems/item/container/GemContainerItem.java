package net.silentchaos512.gems.item.container;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class GemContainerItem extends Item implements IContainerItem {
    private final Component containerName;

    public GemContainerItem(String containerName, Properties properties) {
        super(properties);
        this.containerName = Component.translatable("container.silentgems." + containerName);
    }

    protected abstract MenuType<? extends GemContainer> getContainerType();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable(Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this)) + ".desc"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            openMenu(player);
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    /**
     * Opens this item's menu for the given player, server-side. Used both by right-clicking the
     * item in hand ({@link #use}) and by the keybind used to open a curio-worn one.
     */
    public void openMenu(Player player) {
        player.openMenu(new SimpleMenuProvider((id, playerInventory, p) ->
                this.getContainerType().create(id, playerInventory),
                this.containerName));
    }
}
