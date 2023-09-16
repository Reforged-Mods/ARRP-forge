package net.devtech.arrp.mixin;

import net.devtech.arrp.ARRP;
import net.devtech.arrp.api.RRPEvent;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.util.IrremovableList;
import net.minecraft.resource.*;
import net.minecraftforge.fml.ModLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Mixin(FileResourcePackProvider.class)
public class FileResourcePackProviderMixin {
	private static final ResourcePackSource RUNTIME = ResourcePackSource.nameAndSource("pack.source.runtime");
	private static final Logger ARRP_LOGGER = LogManager.getLogger("ARRP/FileResourcePackProviderMixin");

	@Inject(method = "register", at = @At("HEAD"))
	public void register(Consumer<ResourcePackProfile> adder, ResourcePackProfile.Factory factory, CallbackInfo ci) throws ExecutionException, InterruptedException {
		List<ResourcePack> list = new IrremovableList<>(new ArrayList<>(), pack -> {
			if (pack instanceof RuntimeResourcePack) {
				((RuntimeResourcePack) pack).dump();
			}
		});
		ARRP.waitForPregen();
		ARRP_LOGGER.info("ARRP register - before user");
		RRPEvent.BeforeUser beforeUser = new RRPEvent.BeforeUser(list, null);
		ARRP.EVENT_BUS.post(beforeUser);

		for (ResourcePack pack : list) {
			adder.accept(ResourcePackProfile.of(
				pack.getName(),
				false,
				() -> pack,
				factory,
				ResourcePackProfile.InsertionPosition.TOP,
				RUNTIME
			));
		}
	}
}
