package mod.master_bw3.swindler.entity

import com.mojang.datafixers.util.Pair
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL_EFFECT
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL
import mod.master_bw3.swindler.registry.Entities
import mod.master_bw3.swindler.registry.SpellEmitterEffects
import mod.master_bw3.swindler.spell.spellEmitterEffects.SpellEmitterEffect
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.Identifier
import net.minecraft.world.World
import org.ladysnake.cca.api.v3.component.Component
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent

class SpellEmitterEntity(type: EntityType<SpellEmitterEntity>, world: World) : Entity(type, world) {

    var spell: SpellPart
        get() = SPELL.get(this).spell
        set(value) {
            SPELL.get(this).spell = value; SPELL.sync(this)
        }

    var effectId: Identifier
        get() = SPELL_EFFECT.get(this).effectId
        set(value) {
            SPELL_EFFECT.get(this).effectId = value; SPELL_EFFECT.sync(this)
        }

    var effectData: NbtCompound
        get() = SPELL_EFFECT.get(this).effectData
        set(value) {
            SPELL_EFFECT.get(this).effectData = value; SPELL_EFFECT.sync(this)
        }

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
        getEffect()?.onTickEffect(this, effectData)
    }

}

class SpellComponent : Component, AutoSyncedComponent {
    var spell: SpellPart = SpellPart()

    override fun readFromNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        if (tag.contains(Swindler.id("spell").toString())) {
            SpellPart.CODEC.decode(NbtOps.INSTANCE, tag[Swindler.id("spell").toString()])
                .resultOrPartial()
                .ifPresent { pair: Pair<SpellPart, NbtElement> ->
                    spell = pair.first
                }
        }
    }

    override fun writeToNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        SpellPart.CODEC.encodeStart(NbtOps.INSTANCE, spell)
            .resultOrPartial()
            .ifPresent { element: NbtElement? -> tag.put(Swindler.id("spell").toString(), element) }
    }
}

class SpellEffectComponent : Component, AutoSyncedComponent {
    var effectId: Identifier = Swindler.id("none")
    var effectData: NbtCompound = NbtCompound()

    override fun readFromNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        if (tag.contains(Swindler.id("effect_id").toString())) {
            Identifier.CODEC.decode(NbtOps.INSTANCE, tag[Swindler.id("effect_id").toString()])
                .resultOrPartial()
                .ifPresent { pair: Pair<Identifier, NbtElement> ->
                    effectId = pair.first
                }
        }
        if (tag.contains(Swindler.id("data").toString())) {
            effectData = tag.getCompound(Swindler.id("data").toString())
        }
    }

    override fun writeToNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        Identifier.CODEC.encodeStart(NbtOps.INSTANCE, effectId)
            .resultOrPartial()
            .ifPresent { element: NbtElement? -> tag.put(Swindler.id("effect_id").toString(), element) }
        tag.put(Swindler.id("data").toString(), effectData)
    }
}