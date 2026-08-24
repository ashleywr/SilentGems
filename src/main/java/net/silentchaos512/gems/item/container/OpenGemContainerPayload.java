package net.silentchaos512.gems.item.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.silentchaos512.gems.SilentGems;

/**
 * Sent client -> server when the player presses the "open gem bag"/"open flower basket" keybind,
 * to open the item's menu even when it's worn in a curio slot rather than held.
 */
public record OpenGemContainerPayload(boolean flowerBasket) implements CustomPacketPayload {
    public static final Type<OpenGemContainerPayload> TYPE = new Type<>(SilentGems.getId("open_gem_container"));

    public static final StreamCodec<FriendlyByteBuf, OpenGemContainerPayload> STREAM_CODEC = StreamCodec.of(
            (buf, data) -> buf.writeBoolean(data.flowerBasket()),
            buf -> new OpenGemContainerPayload(buf.readBoolean())
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
