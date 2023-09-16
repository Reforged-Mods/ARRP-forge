package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPEvent;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.util.IrremovableList;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.resource.ResourcePackLoader;
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
		List<ResourcePack> copy = new ArrayList<>(packs);
		ARRP_LOGGER.info("ARRP register - before vanilla");
		RRPEvent.BeforeVanilla beforeVanilla = new RRPEvent.BeforeVanilla(Lists.reverse(copy), type);
		ARRP.EVENT_BUS.post(beforeVanilla);
		OptionalInt optionalInt = IntStream.range(0, copy.size()).filter(i -> copy.get(i) instanceof PathPackResources && copy.get(i).getClass().isAnonymousClass()).findFirst();

		if (optionalInt.isPresent()) {
			ARRP_LOGGER.info("ARRP register - between vanilla and mods");
			int initialCopyLength = copy.size();
			RRPEvent.BetweenVanillaAndMods betweenVanillaAndMods = new RRPEvent.BetweenVanillaAndMods(copy.subList(0, optionalInt.getAsInt()), type);
			ARRP.EVENT_BUS.post(betweenVanillaAndMods);
			ARRP_LOGGER.info("ARRP register - between mods and user");
			int finalCopyLength = copy.size();
			RRPEvent.BetweenModsAndUser betweenModsAndUser = new RRPEvent.BetweenModsAndUser(copy.subList(0, optionalInt.getAsInt()+1+(finalCopyLength-initialCopyLength)), type);
			ARRP.EVENT_BUS.post(betweenModsAndUser);
		}
		
		ARRP_LOGGER.info("ARRP register - after vanilla");
		RRPEvent.AfterVanilla afterVanilla = new RRPEvent.AfterVanilla(copy, type);
		ARRP.EVENT_BUS.post(afterVanilla);
		return copy;
	}
}
