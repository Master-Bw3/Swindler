package mod.master_bw3.swindler.spell.tricks.basic

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.EntityFragment
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VectorFragment
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import dev.enjarai.trickster.spell.tricks.blunder.NoPlayerBlunder
import mod.master_bw3.swindler.spell.tricks.blunder.InvalidEntityBlunder
import mod.master_bw3.swindler.utils.vector_to_vec3d
import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.RaycastContext.FluidHandling
import net.minecraft.world.RaycastContext.ShapeType


object GetLook : Trick(Pattern.of(3, 4, 5, 2)) {

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {
        val entityFragment = expectInput(fragments, FragmentType.ENTITY, 0)
        val entity = ctx.world.getEntity(entityFragment.uuid) ?: throw InvalidEntityBlunder(this)

        return VectorFragment.of(entity.rotationVector)
    }
}