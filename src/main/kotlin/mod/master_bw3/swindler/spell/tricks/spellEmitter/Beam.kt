package mod.master_bw3.swindler.spell.tricks.spellEmitter

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.PatternGlyph
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.SpellPart
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.util.math.Vec3d
import java.util.*


object Beam : Trick(Pattern.of(3, 4, 5)) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        val length = expectInput(fragments, FragmentType.NUMBER, 1).number
        val strength = expectInput(fragments, FragmentType.NUMBER, 2).number

        val displaySpell = SpellPart(PatternGlyph(3, 4, 5), fragments.map { Optional.of(SpellPart(it, listOf())) })

        val emitter = SpellEmitterEntity(ctx.world, displaySpell, 20, Swindler.id("beam"))
        ctx.world.spawnEntity(emitter)

        return VoidFragment.INSTANCE
    }
}