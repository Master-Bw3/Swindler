package mod.master_bw3.swindler.spell.tricks.basic

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VectorFragment
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import dev.enjarai.trickster.spell.tricks.blunder.NoPlayerBlunder
import mod.master_bw3.swindler.utils.vector_to_vec3d
import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.RaycastContext.FluidHandling
import net.minecraft.world.RaycastContext.ShapeType


object RaycastBlock : Trick(Pattern.of(6, 4, 1, 2, 5, 4, 2)) {
    const val RAYCAST_DISTANCE: Double = 32.0


    fun raycastEnd(origin: Vec3d, look: Vec3d): Vec3d =
        origin.add(look.normalize().multiply(RAYCAST_DISTANCE))

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {
        val origin = expectInput(fragments, FragmentType.VECTOR, 0)
        val look = expectInput(fragments, FragmentType.VECTOR, 1)

        val entity = ctx.player.orElseThrow {
            NoPlayerBlunder(this)
        } as ServerPlayerEntity


        val blockHitResult = ctx.world.raycast(
            RaycastContext(
                vector_to_vec3d(origin.vector),
                raycastEnd(vector_to_vec3d(origin.vector), vector_to_vec3d(look.vector)),
                ShapeType.COLLIDER,
                FluidHandling.NONE,
                ctx.player.orElse(null) as Entity
            )
        )

        return if (blockHitResult.type == HitResult.Type.BLOCK) {
            VectorFragment.of(blockHitResult.blockPos)
        } else {
            VoidFragment.INSTANCE
        }
    }
}