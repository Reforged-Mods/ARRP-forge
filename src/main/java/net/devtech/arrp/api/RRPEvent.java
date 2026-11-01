package net.devtech.arrp.api;

import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class RRPEvent extends Event {
    private final List<PackResources> runTimeResourcePacks;
    private final PackType type;

    public RRPEvent(List<PackResources> pack, PackType type) {
        this.runTimeResourcePacks = pack;
        this.type = type;
    }

    public void addPack(PackResources pack) {
        runTimeResourcePacks.add(pack);
    }

    public void addPacks(PackResources... packs) {
        runTimeResourcePacks.addAll(Arrays.asList(packs));
    }

    public void addPacks(List<PackResources> packs) {
        runTimeResourcePacks.addAll(packs);
    }

    @Nullable
    public PackType getType() {
        return type;
    }

    /**
     * Register your resource pack at a lower priority than minecraft and mod resources
     */
    public static class BeforeVanilla extends RRPEvent{

        public BeforeVanilla(List<PackResources> pack, PackType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack at a higher priority than minecraft and mod resources
     */
    public static class AfterVanilla extends RRPEvent{
        public AfterVanilla(List<PackResources> pack, PackType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack at a higher priority than minecraft and mod resources, but lower priority than user resources.
     */
    public static class BeforeUser extends RRPEvent {
        public BeforeUser(List<PackResources> pack, PackType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack between minecraft and mod resources
     */
    public static class BetweenVanillaAndMods extends RRPEvent {
        public BetweenVanillaAndMods(List<PackResources> pack, PackType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack between mod resources and user resources. This is similar to the BEFORE_USER event,
     * but is always enabled and not visible in the resource pack selection screen.
     */
    public static class BetweenModsAndUser extends RRPEvent {
        public BetweenModsAndUser(List<PackResources> pack, PackType type) {
            super(pack, type);
        }
    }
}
