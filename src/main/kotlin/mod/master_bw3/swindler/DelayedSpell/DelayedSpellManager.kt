package mod.master_bw3.swindler.DelayedSpell

import dev.enjarai.trickster.spell.PlayerSpellContext
import dev.enjarai.trickster.spell.SpellPart
import net.minecraft.entity.EquipmentSlot
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld


class DelayedSpellManager() {

    var spells: MutableList<DelayedSpell> = mutableListOf()

    var shouldClearOnWrite = false

    fun add(spell: SpellPart, delay: Long) {
        spells.add(DelayedSpell(spell, delay))
    }

    fun triggerSpells(caster: ServerPlayerEntity) {
        spells.forEach { it.triggerTick -= 1 }

        val spellsToRun = spells.filter { it.triggerTick <= 0 }
        spellsToRun.forEach {it.spell.runSafely(PlayerSpellContext(caster, EquipmentSlot.MAINHAND))}

        spells = spells.filter { it.triggerTick > 0 }.toMutableList()
    }
}