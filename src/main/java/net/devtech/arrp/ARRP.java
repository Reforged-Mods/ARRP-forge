package net.devtech.arrp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.devtech.arrp.api.RRPInitEvent;
import net.neoforged.bus.api.BusBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("arrp")
public class ARRP {
	private static final Logger LOGGER = LogManager.getLogger("ARRP");
	private static List<Future<?>> futures;

	public static final IEventBus EVENT_BUS = BusBuilder.builder().build();

	public ARRP(){
		LOGGER.info("I used the json to destroy the json");
	}

	public void onPreLaunch() {
		/*FabricLoader loader = FabricLoader.getInstance();
		List<Future<?>> futures = new ArrayList<>();
		for (RRPPreGenEntrypoint entrypoint : loader.getEntrypoints("rrp:pregen", RRPPreGenEntrypoint.class)) {
			futures.add(RuntimeResourcePackImpl.EXECUTOR_SERVICE.submit(entrypoint::pregen));
		}*/
		ARRP.futures = futures;
	}

	public static void waitForPregen() throws ExecutionException, InterruptedException {
		if(futures != null) {
			for(Future<?> future : futures) {
				future.get();
			}
			futures = null;
		}
	}
}
