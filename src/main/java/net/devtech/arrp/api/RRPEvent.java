package net.devtech.arrp.api;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class RRPEvent extends Event implements IModBusEvent {
    private final List<ResourcePack> runTimeResourcePacks;
    private final ResourceType type;

    public RRPEvent(List<ResourcePack> pack, ResourceType type) {
        this.runTimeResourcePacks = pack;
        this.type = type;
    }

    public void addPack(ResourcePack pack) {
        runTimeResourcePacks.add(pack);
    }

    public void addPacks(ResourcePack... packs) {
        runTimeResourcePacks.addAll(Arrays.asList(packs));
    }

    public void addPacks(List<ResourcePack> packs) {
        runTimeResourcePacks.addAll(packs);
    }

    @Nullable
    public ResourceType getType() {
        return type;
    }

    /**
     * Register your resource pack at a lower priority than minecraft and mod resources
     */
    public static class BeforeVanilla extends RRPEvent{

        public BeforeVanilla(List<ResourcePack> pack, ResourceType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack at a higher priority than minecraft and mod resources
     */
    public static class AfterVanilla extends RRPEvent{
        public AfterVanilla(List<ResourcePack> pack, ResourceType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack at a higher priority than minecraft and mod resources, but lower priority than user resources.
     */
    public static class BeforeUser extends RRPEvent {
        public BeforeUser(List<ResourcePack> pack, ResourceType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack between minecraft and mod resources
     */
    public static class BetweenVanillaAndMods extends RRPEvent {
        public BetweenVanillaAndMods(List<ResourcePack> pack, ResourceType type) {
            super(pack, type);
        }
    }

    /**
     * Register your resource pack between mod resources and user resources. This is similar to the BEFORE_USER event,
     * but is always enabled and not visible in the resource pack selection screen.
     */
    public static class BetweenModsAndUser extends RRPEvent {
        public BetweenModsAndUser(List<ResourcePack> pack, ResourceType type) {
            super(pack, type);
        }
    }
}
