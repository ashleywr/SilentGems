package net.silentchaos512.gems.setup;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.compat.CuriosCompat;
import net.silentchaos512.gems.item.container.FlowerBasketItem;
import net.silentchaos512.gems.item.container.GemBagItem;
import net.silentchaos512.gems.item.container.GemContainerItem;
import net.silentchaos512.gems.item.container.OpenGemContainerPayload;

@EventBusSubscriber(modid = SilentGems.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class GemsNetwork {
    private GemsNetwork() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(
                OpenGemContainerPayload.TYPE,
                OpenGemContainerPayload.STREAM_CODEC,
                (data, ctx) -> ctx.enqueueWork(() -> handleOpenGemContainer(data, ctx.player()))
        );
    }

    private static void handleOpenGemContainer(OpenGemContainerPayload data, Player player) {
        if (!ModList.get().isLoaded("curios")) return;

        GemContainerItem item = data.flowerBasket() ? GemsItems.FLOWER_BASKET.get() : GemsItems.GEM_BAG.get();
        Class<? extends GemContainerItem> itemClass = data.flowerBasket() ? FlowerBasketItem.class : GemBagItem.class;

        // Only open if it's actually worn - the player doesn't otherwise reach this path, since
        // right-clicking a held one already opens it directly via GemContainerItem#use.
        if (!CuriosCompat.findEquipped(player, itemClass).isEmpty()) {
            item.openMenu(player);
        }
    }
}
