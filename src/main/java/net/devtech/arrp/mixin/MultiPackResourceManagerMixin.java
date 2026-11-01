package net.devtech.arrp.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPEvent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MultiPackResourceManager.class)
public abstract class MultiPackResourceManagerMixin {
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/MultiPackResourceManagerMixin");

	@ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
	private static List<PackResources> registerARRPs(List<PackResources> packs, PackType type, List<PackResources> packs0) throws ExecutionException, InterruptedException {
		List<PackResources> copy = new ArrayList<>(packs);
		ARRP_LOGGER.info("ARRP register - before vanilla");
		RRPEvent.BeforeVanilla beforeVanilla = new RRPEvent.BeforeVanilla(Lists.reverse(copy), type);
		ARRP.EVENT_BUS.post(beforeVanilla);
		OptionalInt firstPackIndex = IntStream.range(0, copy.size()).filter(i -> copy.get(i).getClass().getName().equals("net.minecraftforge.resource.ResourcePackLoader$1")).findFirst();
		OptionalInt lastPackIndex = IntStream.range(0, copy.size()).filter(i -> copy.get(i).getClass().getName().equals("net.minecraftforge.resource.ResourcePackLoader$1")).reduce((first, second) -> second);
		if (firstPackIndex.isPresent() && lastPackIndex.isPresent()) {
			ARRP_LOGGER.info("ARRP register - between vanilla and mods");
			int initialCopyLength = copy.size();
			RRPEvent.BetweenVanillaAndMods betweenVanillaAndMods = new RRPEvent.BetweenVanillaAndMods(copy.subList(0, firstPackIndex.getAsInt()), type);
			ARRP.EVENT_BUS.post(betweenVanillaAndMods);
			ARRP_LOGGER.info("ARRP register - between mods and user");
			int finalCopyLength = copy.size();
			RRPEvent.BetweenModsAndUser betweenModsAndUser = new RRPEvent.BetweenModsAndUser(copy.subList(0, lastPackIndex.getAsInt()+1+(finalCopyLength-initialCopyLength)), type);
			ARRP.EVENT_BUS.post(betweenModsAndUser);
		}
		
		ARRP_LOGGER.info("ARRP register - after vanilla");
		RRPEvent.AfterVanilla afterVanilla = new RRPEvent.AfterVanilla(copy, type);
		ARRP.EVENT_BUS.post(afterVanilla);
		return copy;
	}
}
