package mod.master_bw3.swindler.spell.spellEmitterEffects

import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.DustParticleEffect
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.function.Predicate

class BeamEffect : SpellEmitterEffect() {
    override fun onTickEffect(emitter: SpellEmitterEntity, effectData: NbtCompound): NbtCompound {
        val distance = if (effectData.contains(Swindler.id("length").toString())) {
            effectData.getDouble(Swindler.id("length").toString())
        } else 1.0

        val strength = if (effectData.contains(Swindler.id("strength").toString())) {
            effectData.getDouble(Swindler.id("strength").toString())
        } else 1.0

        val duration = if (effectData.contains(Swindler.id("duration").toString())) {
            effectData.getInt(Swindler.id("duration").toString())
        } else 20

        if (duration <= 0) {
            emitter.spell = SpellPart()
            emitter.effectId = Swindler.id("none")
            return NbtCompound()
        }

        if (emitter.world.isClient) {

            val step = 0.1f

            (0..(distance / step).toInt()).forEach {
                val direction = emitter.rotationVector.toVector3f()
                val position = direction.mul(it.toFloat() * step)
                    .add(emitter.eyePos.toVector3f())

                emitter.world.addParticle(
                    DustParticleEffect(DustParticleEffect.RED, 1.2f),
                    position.x.toDouble(), position.y.toDouble(), position.z.toDouble(),
                    0.0, 0.0, 0.0)

            }
        }

        val endPos = emitter.eyePos.add(emitter.rotationVector.multiply(distance.toDouble()))

        val hitEntities = emitter.world.getOtherEntities(emitter, Box(emitter.eyePos, endPos)) { true }

        hitEntities.forEach{ it.damage(emitter.damageSources.generic(), strength.toFloat() * 0.5f) }

        effectData.putInt(Swindler.id("duration").toString(), duration - 1)
        return effectData
    }
}