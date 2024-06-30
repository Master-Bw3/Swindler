package mod.master_bw3.swindler.entity

import com.mojang.datafixers.util.Pair
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.SwindlerCardinalComponents.EFFECT_ID
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL
import mod.master_bw3.swindler.registry.Entities
import mod.master_bw3.swindler.registry.SpellEmitterEffects
import mod.master_bw3.swindler.spell.spellEmitterEffects.SpellEmitterEffect
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.TntEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandler
import net.minecraft.entity.data.TrackedDataHandlerRegistry
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
        set(value) { SPELL.get(this).spell = value; SPELL.sync(this)}

    var effectId: Identifier
        get() = EFFECT_ID.get(this).effectId
        set(value) { EFFECT_ID.get(this).effectId = value; EFFECT_ID.sync(this)}

    var lifetime: Int = 0

    constructor(world: World) : this(Entities.SPELL_EMITTER_ENTITY, world)

    constructor(world: World, spell: SpellPart, lifetime: Int, effectId: Identifier) : this(Entities.SPELL_EMITTER_ENTITY, world) {
        this.spell = spell
        this.lifetime = lifetime
        this.effectId = effectId
    }

    fun getEffect(): SpellEmitterEffect? = SpellEmitterEffects.REGISTRY.get(effectId)


    override fun initDataTracker(builder: DataTracker.Builder) {}

    override fun readCustomDataFromNbt(nbt: NbtCompound) {}

    override fun writeCustomDataToNbt(nbt: NbtCompound) {}

    override fun tick() {
        Swindler.logger.info(effectId.toString())
        getEffect()?.onTickEffect(this)
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
        }    }

    override fun writeToNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        SpellPart.CODEC.encodeStart(NbtOps.INSTANCE, spell)
            .resultOrPartial()
            .ifPresent { element: NbtElement? -> tag.put(Swindler.id("spell").toString(), element) }    }
}

class EffectIdComponent : Component, AutoSyncedComponent {
    var effectId: Identifier = Swindler.id("none")

    override fun readFromNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        if (tag.contains(Swindler.id("effect_id").toString())) {
            Identifier.CODEC.decode(NbtOps.INSTANCE, tag[Swindler.id("effect_id").toString()])
                .resultOrPartial()
                .ifPresent { pair: Pair<Identifier, NbtElement> ->
                    effectId = pair.first
                }
        }    }

    override fun writeToNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        Identifier.CODEC.encodeStart(NbtOps.INSTANCE, effectId)
            .resultOrPartial()
            .ifPresent { element: NbtElement? -> tag.put(Swindler.id("effect_id").toString(), element) }    }
}