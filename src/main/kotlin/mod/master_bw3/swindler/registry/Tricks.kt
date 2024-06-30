package mod.master_bw3.swindler.registry

import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.Tricks
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.spell.tricks.basic.*
import mod.master_bw3.swindler.spell.tricks.delay.CancelDelay
import mod.master_bw3.swindler.spell.tricks.delay.Delay
import mod.master_bw3.swindler.spell.tricks.spellEmitter.Beam
import mod.master_bw3.swindler.spell.tricks.spellEmitter.CreateSpellEmitter
import mod.master_bw3.swindler.spell.tricks.world.Explode
import net.minecraft.registry.Registry


object Tricks {
    // Math
    val ONE: Trick = register("two", NumberConstant(1.0, Pattern.of(6, 7, 4, 1)))
    val ZERO: Trick = register("two", NumberConstant(0.0, Pattern.of(8, 7, 4, 1)))


    // Basic
    val GET_CASTER: Trick = register("get_caster", GetCaster)
    val GET_LOOK: Trick = register("get_entity_look", GetLook)
    val GET_POS: Trick = register("get_entity_pos", GetPos)

    // Delay
    val DELAY: Trick = register("delay", Delay)
    val CANCEL_DELAY: Trick = register("cancel_delay", CancelDelay)


    // Raycast
    val ACTUAL_RAYCAST: Trick = register("raycast", RaycastBlock)


    // World
    val EXPLODE: Trick = register("explode", Explode)

    // Spell Emitter
    val CREATE_EMITTER: Trick = register("create_spell_emitter", CreateSpellEmitter)
    val BEAM: Trick = register("beam", Beam)


    private fun <T : Trick> register(path: String, trick: T): T {
        return Registry.register(Tricks.REGISTRY, Swindler.id(path), trick)
    }

    fun lookup(pattern: Pattern): Trick? {
        return Tricks.lookup(pattern)
    }

    fun register() {
        // this does nothing I guess?
    }
}