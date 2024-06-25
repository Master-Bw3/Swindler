package mod.master_bw3.swindler

import mod.master_bw3.swindler.registry.Tricks
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Swindler : ModInitializer {
    val logger = LoggerFactory.getLogger("swindler")

	const val MOD_ID = "swindler"

	fun id(path: String): Identifier = Identifier.of(MOD_ID, path)


	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		Tricks.register();
	}
}