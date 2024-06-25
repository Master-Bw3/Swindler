package mod.master_bw3.swindler.spell.tricks

import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.Tricks
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.spell.tricks.basic.One
import mod.master_bw3.swindler.spell.tricks.world.Explode
import net.minecraft.registry.Registry


object Tricks {
    private val LOOKUP: MutableMap<Pattern, Trick> = HashMap()

    // Basic
    val ONE: One = register("two", One)

    // World
    val EXPLODE: Explode = register("explode", Explode)

    private fun <T : Trick?> register(path: String, trick: T): T {
        return Registry.register(Tricks.REGISTRY, Swindler.id(path), trick)
    }

    fun lookup(pattern: Pattern): Trick? {
        return LOOKUP[pattern]
    }

    fun register() {
        // this does nothing I guess?
    }
}