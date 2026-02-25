package net.devtech.arrp;

import net.devtech.arrp.api.RRPInitEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod.EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public void particleFactoryRegister(RegisterParticleProvidersEvent event){
        if (FMLEnvironment.dist.isClient()){
            ModLoader.get().postEvent(new RRPInitEvent());
        }
    }
}
