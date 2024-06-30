package mod.master_bw3.swindler.spell.spellEmitterEffects

import mod.master_bw3.swindler.Swindler
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.DustParticleEffect
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.function.Predicate

class BeamEffect : SpellEmitterEffect() {
    override fun onTickEffect(entity: Entity, effectData: NbtCompound) {
        val distance = if (effectData.contains(Swindler.id("length").toString())) {
            effectData.getDouble(Swindler.id("length").toString())
        } else 1.0

        val strength = if (effectData.contains(Swindler.id("strength").toString())) {
            effectData.getDouble(Swindler.id("strength").toString())
        } else 1.0


        if (entity.world.isClient) {

            val step = 0.1f

            (0..(distance / step).toInt()).forEach {
                val direction = entity.rotationVector.toVector3f()
                val position = direction.mul(it.toFloat() * step)
                    .add(entity.eyePos.toVector3f())

                entity.world.addParticle(
                    DustParticleEffect(DustParticleEffect.RED, 1.2f),
                    position.x.toDouble(), position.y.toDouble(), position.z.toDouble(),
                    0.0, 0.0, 0.0)

            }
        }

        val endPos = entity.eyePos.add(entity.rotationVector.multiply(distance.toDouble()))

        val hitResult = getEntityHitResult(entity, entity.world, entity.eyePos, endPos, Box(entity.eyePos, endPos), {true},1_000_000.0)

        hitResult?.entity?.damage(entity.damageSources.generic(), strength.toFloat() * 0.5f)
    }

    fun getEntityHitResult(
        entity: Entity?, world: World, startPos: Vec3d, endPos: Vec3d,
        aabb: Box, isValid: Predicate<Entity>, maxSqrLength: Double): EntityHitResult? {
        var sqrLength = maxSqrLength
        var hitEntity: Entity? = null
        var hitPos: Vec3d? = null
        val allValidInAABB: Iterator<*> = world.getOtherEntities(entity, aabb, isValid).iterator()

        while (allValidInAABB.hasNext()) {
            val nextEntity = allValidInAABB.next() as Entity
            val hitBox = nextEntity.boundingBox.expand(nextEntity.targetingMargin.toDouble())
            val overlapBox = hitBox.raycast(startPos, endPos)
            if (hitBox.contains(startPos)) {
                if (sqrLength >= 0.0) {
                    hitEntity = nextEntity
                    hitPos = overlapBox.orElse(startPos)
                    sqrLength = 0.0
                }
            } else if (overlapBox.isPresent) {
                val maybePos = overlapBox.get()
                val sqrDist = startPos.squaredDistanceTo(maybePos)
                if (sqrDist < sqrLength || sqrLength == 0.0) {
                    if (nextEntity.rootVehicle === entity?.rootVehicle) {
                        if (sqrLength == 0.0) {
                            hitEntity = nextEntity
                            hitPos = maybePos
                        }
                    } else {
                        hitEntity = nextEntity
                        hitPos = maybePos
                        sqrLength = sqrDist
                    }
                }
            }
        }
        return if (hitEntity == null) {
            null
        } else EntityHitResult(hitEntity, hitPos!!) // hitEntity != null <=> hitPos != null
    }
}