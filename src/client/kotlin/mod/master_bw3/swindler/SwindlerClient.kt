package mod.master_bw3.swindler

import mod.master_bw3.swindler.registry.EntityRenderers
import net.fabricmc.api.ClientModInitializer


object SwindlerClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRenderers.register()
	}

}