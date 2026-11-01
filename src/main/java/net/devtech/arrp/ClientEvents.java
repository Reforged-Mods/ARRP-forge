package net.devtech.arrp;

import net.devtech.arrp.api.RRPInitEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = "arrp", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void particleFactoryRegister(RegisterParticleProvidersEvent event){
        if (FMLEnvironment.dist.isClient()){
            ModLoader.postEvent(new RRPInitEvent());
        }
    }
}
