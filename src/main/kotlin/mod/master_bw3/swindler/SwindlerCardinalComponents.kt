package mod.master_bw3.swindler

import mod.master_bw3.swindler.entity.EffectIdComponent
import mod.master_bw3.swindler.entity.SpellComponent
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer

object SwindlerCardinalComponents : EntityComponentInitializer {


    val SPELL: ComponentKey<SpellComponent> = ComponentRegistry.getOrCreate(
        Swindler.id("spell"),
        SpellComponent::class.java
    )

    val EFFECT_ID: ComponentKey<EffectIdComponent> = ComponentRegistry.getOrCreate(
        Swindler.id("effect_id"),
        EffectIdComponent::class.java
    )

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerFor(
            SpellEmitterEntity::class.java,
            SPELL
        ) { SpellComponent() }

        registry.registerFor(
            SpellEmitterEntity::class.java,
            EFFECT_ID
        ) { EffectIdComponent() }
    }

}