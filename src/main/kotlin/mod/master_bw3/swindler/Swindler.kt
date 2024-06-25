package mod.master_bw3.swindler

import mod.master_bw3.swindler.DelayedSpell.DelayedSpellManager
import mod.master_bw3.swindler.DelayedSpell.DelayedSpellStateSaverAndLoader
import mod.master_bw3.swindler.registry.Tricks
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Swindler : ModInitializer {
	val logger = LoggerFactory.getLogger("swindler")

	const val MOD_ID = "swindler"
	const val FILE_DELAYED_SPELL_MANAGER = "${MOD_ID}_spell_manager"


	fun id(path: String): Identifier = Identifier.of(MOD_ID, path)


	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Tricks.register();
		initListeners()
	}

	private fun initListeners() {
		ServerTickEvents.START_SERVER_TICK.register { server ->
			val delayedSpellsState = DelayedSpellStateSaverAndLoader.getServerState(server)

			server.playerManager.playerList.forEach { player ->
				val delayedSpells = delayedSpellsState.getPlayerState(player)

				delayedSpells.triggerSpells(player)
			}
		}
	}
}