package mod.master_bw3.swindler

import mod.master_bw3.swindler.DelayedSpell.DelayedSpellManager
import mod.master_bw3.swindler.registry.Tricks
import net.fabricmc.api.ModInitializer
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
		logger.info("Hello Fabric world!")
		Tricks.register();
		initListeners()
	}

	private fun initListeners() {
		ServerTickEvents.START_SERVER_TICK.register {
			DelayedSpellManager.triggerSpells(it)
		}

//		ServerLifecycleEvents.SERVER_STARTED.register {
//			val cellSavedData = {nbt: NbtCompound -> DelayedSpellSavedData(nbt, it.overworld)}
//			val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::DelayedSpellSavedData, FILE_DELAYED_SPELL_MANAGER)
//			savedData.markDirty()
//		}
//		ServerLifecycleEvents.SERVER_STOPPING.register {
//			val cellSavedData = {nbt: NbtCompound -> DelayedSpellSavedData(nbt, it.overworld)}
//			val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::DelayedSpellSavedData, FILE_DELAYED_SPELL_MANAGER)
//			CellManager.shouldClearOnWrite = true
//			savedData.markDirty()
//		}

	}
}