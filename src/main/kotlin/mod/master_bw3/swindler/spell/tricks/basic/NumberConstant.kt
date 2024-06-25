package mod.master_bw3.swindler.spell.tricks.basic

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.NumberFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException


class NumberConstant(private val number: Double, pattern: Pattern) : Trick(pattern) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        return NumberFragment(number)
    }
}