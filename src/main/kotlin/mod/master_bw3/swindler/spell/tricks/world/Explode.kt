package mod.master_bw3.swindler.spell.tricks.world

import dev.enjarai.trickster.particle.ModParticles
import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlockOccupiedBlunder
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import mod.master_bw3.swindler.Swindler
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.world.World


object Explode : Trick(Pattern.of(0, 1, 2, 5, 8, 7, 6, 3, 0)) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        val pos = expectInput(fragments, FragmentType.VECTOR, 0)
        val strength = expectInput(fragments, FragmentType.NUMBER, 1).number

        val clampedStrength = Math.clamp(strength, 0.0, 10.0)
        val blockPos = pos.toBlockPos()


        expectCanBuild(ctx, blockPos)

        ctx.world.createExplosion(
            ctx.player.orElse(null),
            pos.vector.x(),
            pos.vector.y(),
            pos.vector.z(),
            clampedStrength.toFloat(),
            true,
            World.ExplosionSourceType.TNT
        )

        return VoidFragment.INSTANCE
    }
}