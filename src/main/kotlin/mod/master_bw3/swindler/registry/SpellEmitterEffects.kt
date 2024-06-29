package mod.master_bw3.swindler.registry

import com.mojang.serialization.Lifecycle
import mod.master_bw3.swindler.Swindler.id
import mod.master_bw3.swindler.spell.spellEmitterEffects.BeamEffect
import mod.master_bw3.swindler.spell.spellEmitterEffects.SpellEmitterEffect
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleRegistry

object SpellEmitterEffects {
    val REGISTRY_KEY: RegistryKey<Registry<SpellEmitterEffect>> = RegistryKey.ofRegistry(
        id("spell_emitter_effect")
    )
    val REGISTRY: Registry<SpellEmitterEffect> = SimpleRegistry(REGISTRY_KEY, Lifecycle.stable())

    val BEAM_EFFECT: BeamEffect = register("beam", BeamEffect())

    private fun <T : SpellEmitterEffect> register(path: String, trick: T): T {
        return Registry.register(REGISTRY, id(path), trick)
    }

    fun register() {
    }
}