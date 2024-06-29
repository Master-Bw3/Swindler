package mod.master_bw3.swindler.registry


import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object Entities {
    val SPELL_EMITTER_ENTITY: EntityType<SpellEmitterEntity> =
        EntityType.Builder.create(::SpellEmitterEntity, SpawnGroup.MISC).dimensions(1f, 1f).eyeHeight(0.5f).build()


    fun register() {
        Registry.register(Registries.ENTITY_TYPE, Swindler.id("spell_emitter"), SPELL_EMITTER_ENTITY)
    }

}