package mod.master_bw3.swindler.spell.tricks.world

import dev.enjarai.trickster.particle.ModParticles
import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.PatternGlyph
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.SpellPart
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlockOccupiedBlunder
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import mod.master_bw3.swindler.registry.Entities
import net.minecraft.block.Blocks
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.fluid.Fluids
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.asin
import kotlin.math.atan2


object Beam : Trick(Pattern.of(3, 4, 5)) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        val pos = expectInput(fragments, FragmentType.VECTOR, 0).vector
        val target = expectInput(fragments, FragmentType.VECTOR, 1).vector
        val strength = expectInput(fragments, FragmentType.NUMBER, 2).number

        val emitter = SpellEmitterEntity(ctx.world, SpellPart(PatternGlyph(3, 4, 5), listOf()), 20, Swindler.id("beam"))
        emitter.setPos(pos.x(), pos.y(), pos.z())
        emitter.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, Vec3d(target.x(), target.y(), target.z()))

        ctx.world.spawnEntity(emitter)

        return VoidFragment.INSTANCE
    }
}