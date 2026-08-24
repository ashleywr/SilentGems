package net.silentchaos512.gems.item.container;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.silentchaos512.gems.SilentGems;
import org.lwjgl.glfw.GLFW;

/**
 * Keybinds to open the gem bag/flower basket while worn in a curio slot, where right-clicking
 * isn't possible. Unbound by default - see {@link net.silentchaos512.gems.setup.GemsNetwork}.
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = SilentGems.MOD_ID)
public final class ContainerKeyBinds {
    private ContainerKeyBinds() {
    }

    public static final KeyMapping OPEN_GEM_BAG = new KeyMapping("key.silentgems.open_gem_bag",
            KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, "key.categories.silentgems");
    public static final KeyMapping OPEN_FLOWER_BASKET = new KeyMapping("key.silentgems.open_flower_basket",
            KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, "key.categories.silentgems");

    @EventBusSubscriber(value = Dist.CLIENT, modid = SilentGems.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static final class Registration {
        private Registration() {
        }

        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_GEM_BAG);
            event.register(OPEN_FLOWER_BASKET);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() != GLFW.GLFW_PRESS) return;

        if (event.getKey() == OPEN_GEM_BAG.getKey().getValue()) {
            PacketDistributor.sendToServer(new OpenGemContainerPayload(false));
        } else if (event.getKey() == OPEN_FLOWER_BASKET.getKey().getValue()) {
            PacketDistributor.sendToServer(new OpenGemContainerPayload(true));
        }
    }
}
