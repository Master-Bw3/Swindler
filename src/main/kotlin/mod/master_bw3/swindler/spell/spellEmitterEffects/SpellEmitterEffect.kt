package mod.master_bw3.swindler.spell.spellEmitterEffects

import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound

open class SpellEmitterEffect {
    open fun onTickEffect(entity: Entity, effectData: NbtCompound) {}
}
