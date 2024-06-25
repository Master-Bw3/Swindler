package mod.master_bw3.swindler.spell.tricks.basic

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.NumberFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException


object One : Trick(Pattern.of(6, 7, 4, 1)) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        return NumberFragment(1.0)
    }
}