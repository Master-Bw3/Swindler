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
import mod.master_bw3.swindler.DelayedSpell.DelayedSpellManager
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.spell.tricks.blunder.InvalidEntityBlunder
import mod.master_bw3.swindler.utils.vector_to_vec3d
import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.RaycastContext.FluidHandling
import net.minecraft.world.RaycastContext.ShapeType


object Delay : Trick(Pattern.of(0, 6, 4, 2, 8, 4, 0)) {

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {
        val spell = expectInput(fragments, FragmentType.SPELL_PART, 0)
        val delay = expectInput(fragments, FragmentType.NUMBER, 1)

        if (ctx.player.isPresent) {
            val delayedSpellsState = DelayedSpellManager.getServerState(ctx.world.server)
            delayedSpellsState.add(
                spell,
                delay.number.toLong() * ctx.world.server.tickManager.tickRate.toLong(),
                ctx.player.get(),
                ctx.world
            )
        } else {
            throw NoPlayerBlunder(this)
        }

        return VoidFragment.INSTANCE
    }
}