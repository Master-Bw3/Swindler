package mod.master_bw3.swindler.DelayedSpell

import dev.enjarai.trickster.spell.PlayerSpellContext
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.Swindler.MOD_ID
import net.minecraft.entity.EquipmentSlot
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import java.util.function.Supplier

class DelayedSpellManager() : PersistentState() {
    var spells: MutableList<DelayedSpell> = mutableListOf()

    var shouldClearOnWrite = false

    fun add(spell: SpellPart, delay: Long, player: ServerPlayerEntity, world: ServerWorld) {
        spells.add(DelayedSpell(spell, world.time + delay, player.uuid))
    }

    fun triggerSpells(server: MinecraftServer) {
        for (spell in spells) {
            if (spell.triggerTick <= server.overworld.time) {

                val caster = server.playerManager.playerList.find { it.uuid.equals(spell.caster) }
                if (caster != null) {
                    spell.spell.runSafely(PlayerSpellContext(caster, EquipmentSlot.MAINHAND))
                }

            }
        }

        spells = spells.filter { it.triggerTick > server.overworld.time }.toMutableList()
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?): NbtCompound {
        val nbt = nbt ?: NbtCompound()

        val spellsTag = NbtList()
        for (spell in spells) {
            spellsTag.add(spell.serialize())
        }
        nbt.put("$MOD_ID:spells", spellsTag)

        if (shouldClearOnWrite) {
            spells.clear()
        }

        return nbt
    }

    companion object {
        private fun createFromNbt(tag: NbtCompound, registryLookup: WrapperLookup?): DelayedSpellManager {
            val state: DelayedSpellManager = DelayedSpellManager()

            if (tag.contains("$MOD_ID:spells")) {
                val spellsTag = tag.getList("$MOD_ID:spells", NbtElement.COMPOUND_TYPE.toInt())

                for (spell in spellsTag) {
                    val delayedSpell = DelayedSpell.deserialize(spell as NbtCompound)
                    state.spells.add(delayedSpell)
                }
            }

            return state
        }

        private var type: Type<DelayedSpellManager> = Type<DelayedSpellManager>(
            Supplier<DelayedSpellManager> { DelayedSpellManager() },
            DelayedSpellManager::createFromNbt,
            null
        )

        fun getServerState(server: MinecraftServer): DelayedSpellManager {
            val persistentStateManager = server.getWorld(World.OVERWORLD)!!.persistentStateManager

            val state: DelayedSpellManager = persistentStateManager.getOrCreate(type, MOD_ID)

            state.markDirty()

            return state
        }
    }
}