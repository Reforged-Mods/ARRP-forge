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
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Unit;

@Mixin (LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {

	private static int LOADED_TIMES = 0;
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/LifecycledResourceManagerImplMixin");

	@ModifyVariable(method = "<init>",
			at = @At (value = "HEAD"),
			argsOnly = true)
	private static List<ResourcePack> registerARRPs(List<ResourcePack> packs) throws ExecutionException, InterruptedException {
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