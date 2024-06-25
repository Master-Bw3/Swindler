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

    fun add(spell: SpellPart, delay: Long, world: ServerWorld) {
        spells.add(DelayedSpell(spell, world.time + delay))
    }

    fun triggerSpells(server: MinecraftServer, caster: ServerPlayerEntity) {
        for (spell in spells) {
            if (spell.triggerTick <= server.overworld.time) {
                spell.spell.runSafely(PlayerSpellContext(caster, EquipmentSlot.MAINHAND))

            }
        }

        spells = spells.filter { it.triggerTick > server.overworld.time }.toMutableList()
    }
}