package mod.master_bw3.swindler.spell.spellEmitterEffects

import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.nbt.NbtCompound

open class SpellEmitterEffect {
    open fun onTickEffect(emitter: SpellEmitterEntity, effectData: NbtCompound) : NbtCompound = effectData
}
