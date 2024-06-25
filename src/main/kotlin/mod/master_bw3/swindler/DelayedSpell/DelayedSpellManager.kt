package mod.master_bw3.swindler.DelayedSpell

import dev.enjarai.trickster.spell.PlayerSpellContext
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler.MOD_ID
import net.minecraft.entity.EquipmentSlot
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

object DelayedSpellManager {
    private const val TAG_SPELLS = "$MOD_ID:spells"

    var spells: MutableList<DelayedSpell> = mutableListOf()

    var shouldClearOnWrite = false

    fun add(spell: SpellPart, delay: Long, player: ServerPlayerEntity, world: ServerWorld) {
        spells.add(DelayedSpell(spell, world.time + delay, player.uuid))
    }

    fun triggerSpells(server: MinecraftServer) {
        for (spell in spells) {
            if (spell.triggerTick <= server.overworld.time) {
                val caster = server.playerManager.playerList.find { it.uuid.equals(spell.caster) }
                if (caster != null)
                    spell.spell.runSafely(PlayerSpellContext(caster, EquipmentSlot.MAINHAND))
            }
        }

        spells = spells.filter { it.triggerTick > server.overworld.time }.toMutableList()
    }

    fun readFromNbt(nbtCompound: NbtCompound, world: ServerWorld) {

        if (nbtCompound.contains(TAG_SPELLS)) {
            val spellsTag = nbtCompound.getList(TAG_SPELLS, NbtElement.COMPOUND_TYPE.toInt())

            for (spell in spellsTag) {
                val delayedSpell = DelayedSpell.deserialize(spell as NbtCompound)
                spells.add(delayedSpell)
            }
        }
    }

    fun writeToNbt(nbt: NbtCompound) {

        val spellsTag = NbtList()
        for (spell in spells) {
            spellsTag.add(spell.serialize())
        }
        nbt.put(TAG_SPELLS, spellsTag)

        if (shouldClearOnWrite) {
            spells.clear()
        }

    }
}