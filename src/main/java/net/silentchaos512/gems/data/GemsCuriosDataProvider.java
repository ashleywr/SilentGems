package net.silentchaos512.gems.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.silentchaos512.gems.SilentGems;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Registers the "charm" curio slot the gem bag and flower basket are worn in, and attaches it to
 * players. Only ever instantiated when Curios is loaded - see {@link net.silentchaos512.gems.compat.CuriosCompat}.
 */
public class GemsCuriosDataProvider extends CuriosDataProvider {
    public GemsCuriosDataProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(SilentGems.MOD_ID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        // Sized for both items to be worn there at once.
        this.createSlot("charm").size(2);

        this.createEntities("player_slots")
                .addPlayer()
                .addSlots("charm");
    }
}
