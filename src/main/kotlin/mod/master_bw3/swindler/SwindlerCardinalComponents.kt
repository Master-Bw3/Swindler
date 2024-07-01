package mod.master_bw3.swindler

import com.mojang.datafixers.util.Pair
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL
import mod.master_bw3.swindler.SwindlerCardinalComponents.SPELL_EFFECT
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.Identifier
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer

object SwindlerCardinalComponents : EntityComponentInitializer {


    val SPELL: ComponentKey<SpellComponent> = ComponentRegistry.getOrCreate(
        Swindler.id("spell"),
        SpellComponent::class.java
    )

    val SPELL_EFFECT: ComponentKey<SpellEffectComponent> = ComponentRegistry.getOrCreate(
        Swindler.id("spell_effect"),
        SpellEffectComponent::class.java
    )

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerFor(
            SpellEmitterEntity::class.java,
            SPELL,
            ::SpellComponent
        )

        registry.registerFor(
            SpellEmitterEntity::class.java,
            SPELL_EFFECT,
            ::SpellEffectComponent
        )
    }

}


class SpellComponent(private val entity: Entity) : AutoSyncedComponent {
    var spell: SpellPart = SpellPart()
        set(value) {
            field = value
            SPELL.sync(entity)
        }

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

class SpellEffectComponent(private val entity: Entity) : AutoSyncedComponent {
    var effectId: Identifier = Swindler.id("none")
        set(value) {
            field = value
            SPELL_EFFECT.sync(entity)
        }

    var effectData: NbtCompound = NbtCompound()
        set(value) {
            field = value
            SPELL_EFFECT.sync(entity)
        }

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