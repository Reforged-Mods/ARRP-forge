package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Lists;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPEvent;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.util.IrremovableList;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraftforge.fml.ModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;

@Mixin(LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/ReloadableResourceManagerImplMixin");

	@ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
	private static List<ResourcePack> registerARRPs(List<ResourcePack> packs, ResourceType type, List<ResourcePack> packs0) throws ExecutionException, InterruptedException {
		IrremovableList<ResourcePack> before = new IrremovableList<>(new ArrayList<>(packs), pack -> {
			if (pack instanceof RuntimeResourcePack) {
				((RuntimeResourcePack) pack).dump();
			}
		});
		ARRP_LOGGER.info("ARRP register - before vanilla");
		RRPEvent.BeforeVanilla beforeVanilla = new RRPEvent.BeforeVanilla(Lists.reverse(before), type);
		ModLoader.get().postEvent(beforeVanilla);

		ARRP_LOGGER.info("ARRP register - after vanilla");
		RRPEvent.AfterVanilla afterVanilla = new RRPEvent.AfterVanilla(before, type);
		ModLoader.get().postEvent(afterVanilla);
		return before;
	}
}
