package net.devtech.arrp.api;

import net.minecraft.resource.ResourcePack;
import net.minecraftforge.eventbus.api.Event;

import java.util.Arrays;
import java.util.List;

public class RRPEvent extends Event {
    private final List<ResourcePack> runTimeResourcePacks;

    public RRPEvent(List<ResourcePack> pack) {
        this.runTimeResourcePacks = pack;
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

    public static class BeforeVanilla extends RRPEvent{

        public BeforeVanilla(List<ResourcePack> pack) {
            super(pack);
        }
    }

    public static class AfterVanilla extends RRPEvent{
        public AfterVanilla(List<ResourcePack> pack) {
            super(pack);
        }
    }
}
