
package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import net.devtech.arrp.api.RRPEvent;
import net.devtech.arrp.api.RRPInitEvent;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.util.IrremovableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Unit;

@Mixin (ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

    private static boolean LOADED = false;
    private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");

    @ModifyVariable(method = "reload",
            at = @At (value = "HEAD"), argsOnly = true)
    private List<ResourcePack> registerARRPs(List<ResourcePack> packs, Executor prepareExecutor,
                                             Executor applyExecutor,
                                             CompletableFuture<Unit> initialStage,
                                             List<ResourcePack> packs0) throws ExecutionException, InterruptedException {
        //ARRP.waitForPregen();
        ARRP_LOGGER.info("ARRP register - before vanilla");
        IrremovableList<ResourcePack> before = new IrremovableList<>(new ArrayList<>(), pack -> {
            if (pack instanceof RuntimeResourcePack) {
                ((RuntimeResourcePack) pack).dump();
            }
        });
        RRPEvent.BeforeVanilla beforeVanilla = new RRPEvent.BeforeVanilla(before);
        MinecraftForge.EVENT_BUS.post(beforeVanilla);
        before.addAll(packs);

        if (FMLEnvironment.dist.isClient() && !LOADED){
            ModLoader.get().postEvent(new RRPInitEvent());
            LOADED = true;
        }
        ARRP_LOGGER.info("ARRP register - after vanilla");
        List<ResourcePack> after = new IrremovableList<>(new ArrayList<>(), pack -> {
            if (pack instanceof RuntimeResourcePack) {
                ((RuntimeResourcePack) pack).dump();
            }
        });
        RRPEvent.AfterVanilla afterVanilla = new RRPEvent.AfterVanilla(after);
        MinecraftForge.EVENT_BUS.post(afterVanilla);
        before.addAll(after);


        return before;
    }
}