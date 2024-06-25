package mod.master_bw3.swindler.DelayedSpell

import io.wispforest.owo.offline.DataSavedEvents.PlayerData
import mod.master_bw3.swindler.Swindler.MOD_ID
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.MinecraftServer
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier


class DelayedSpellStateSaverAndLoader() : PersistentState() {

    var players: HashMap<UUID, DelayedSpellManager> = HashMap()


    fun getPlayerState(player: LivingEntity): DelayedSpellManager {
        val serverState: DelayedSpellStateSaverAndLoader = getServerState(player.world.server!!)

        // Either get the player by the uuid, or we don't have data for him yet, make a new player state
        val playerState: DelayedSpellManager = serverState.players.computeIfAbsent(player.uuid) { DelayedSpellManager() }

        return playerState
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?): NbtCompound {
        val nbt = nbt ?: NbtCompound()

        val playersNbt = NbtCompound()
        players.forEach { (uuid: UUID, playerData: DelayedSpellManager) ->
            val playerNbt = NbtCompound()

            val spellsTag = NbtList()
            for (spell in playerData.spells) {
                spellsTag.add(spell.serialize())
            }
            playerNbt.put("$MOD_ID:spells", spellsTag)

            if (playerData.shouldClearOnWrite) {
                playerData.spells.clear()
            }

            playersNbt.put(uuid.toString(), playerNbt)
        }
        nbt.put("players", playersNbt)

        return nbt
    }

    companion object {
        private fun createFromNbt(tag: NbtCompound, registryLookup: WrapperLookup?): DelayedSpellStateSaverAndLoader {
            val state: DelayedSpellStateSaverAndLoader = DelayedSpellStateSaverAndLoader()

            val playersNbt = tag.getCompound("players")
            playersNbt.keys.forEach(Consumer<String> { key: String ->
                val playerData = DelayedSpellManager()

                if (playersNbt.getCompound(key).contains("$MOD_ID:spells")) {
                    val spellsTag = playersNbt.getCompound(key).getList("$MOD_ID:spells", NbtElement.COMPOUND_TYPE.toInt())

                    for (spell in spellsTag) {
                        val delayedSpell = DelayedSpell.deserialize(spell as NbtCompound)
                        playerData.spells.add(delayedSpell)
                    }
                }

                val uuid = UUID.fromString(key)
                state.players[uuid] = playerData
            })



            return state
        }

        private var type: Type<DelayedSpellStateSaverAndLoader> = Type<DelayedSpellStateSaverAndLoader>(
            Supplier<DelayedSpellStateSaverAndLoader> { DelayedSpellStateSaverAndLoader() },
            DelayedSpellStateSaverAndLoader::createFromNbt,
            null
        )

        fun getServerState(server: MinecraftServer): DelayedSpellStateSaverAndLoader {
            val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager

            val state: DelayedSpellStateSaverAndLoader = persistentStateManager.getOrCreate(type, MOD_ID)

            state.markDirty()

            return state
        }
    }
}