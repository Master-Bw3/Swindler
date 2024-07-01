package mod.master_bw3.swindler.entity

import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL_EFFECT
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL
import mod.master_bw3.swindler.registry.Entities
import mod.master_bw3.swindler.registry.SpellEmitterEffects
import mod.master_bw3.swindler.spell.spellEmitterEffects.SpellEmitterEffect
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.world.World


class SpellEmitterEntity(type: EntityType<SpellEmitterEntity>, world: World) : Entity(type, world) {

    var spell: SpellPart
        get() = SPELL.get(this).spell
        set(value) { SPELL.get(this).spell = value }

    var effectId: Identifier
        get() = SPELL_EFFECT.get(this).effectId
        set(value) { SPELL_EFFECT.get(this).effectId = value }

    var effectData: NbtCompound
        get() = SPELL_EFFECT.get(this).effectData
        set(value) { SPELL_EFFECT.get(this).effectData = value }


    var lifetime: Int = 0

    constructor(world: World) : this(Entities.SPELL_EMITTER_ENTITY, world)

    constructor(
        world: World,
        spell: SpellPart,
        lifetime: Int,
        effectId: Identifier
    ) : this(Entities.SPELL_EMITTER_ENTITY, world) {
        this.spell = spell
        this.lifetime = lifetime
        this.effectId = effectId
    }

    fun getEffect(): SpellEmitterEffect? = SpellEmitterEffects.REGISTRY.get(effectId)


    override fun initDataTracker(builder: DataTracker.Builder) {}

    override fun readCustomDataFromNbt(nbt: NbtCompound) {}

    override fun writeCustomDataToNbt(nbt: NbtCompound) {}

    override fun tick() {
        effectData = getEffect()?.onTickEffect(this, effectData) ?: effectData
    }

}