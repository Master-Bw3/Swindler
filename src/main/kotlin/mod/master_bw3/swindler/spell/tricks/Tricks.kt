package mod.master_bw3.swindler.spell.tricks

import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.Tricks
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.spell.tricks.basic.GetCaster
import mod.master_bw3.swindler.spell.tricks.basic.GetLook
import mod.master_bw3.swindler.spell.tricks.basic.One
import mod.master_bw3.swindler.spell.tricks.basic.RaycastBlock
import mod.master_bw3.swindler.spell.tricks.world.Explode
import net.minecraft.registry.Registry


object Tricks {
    private val LOOKUP: MutableMap<Pattern, Trick> = HashMap()

    // Basic
    val ONE: Trick = register("two", One)
    val ACTUAL_RAYCAST: Trick = register("raycast", RaycastBlock)
    val GET_CASTER: Trick = register("get_caster", GetCaster)
    val GET_LOOK: Trick = register("get_entity_look", GetLook)

    // World
    val EXPLODE: Trick = register("explode", Explode)

    private fun <T : Trick> register(path: String, trick: T): T {
        return Registry.register(Tricks.REGISTRY, Swindler.id(path), trick)
    }

    fun lookup(pattern: Pattern): Trick? {
        return LOOKUP[pattern]
    }

    fun register() {
        // this does nothing I guess?
    }
}