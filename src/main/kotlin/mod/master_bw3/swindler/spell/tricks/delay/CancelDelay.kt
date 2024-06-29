package mod.master_bw3.swindler.spell.tricks.delay

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import dev.enjarai.trickster.spell.tricks.blunder.NoPlayerBlunder
import mod.master_bw3.swindler.delayedSpell.DelayedSpellStateSaverAndLoader


object CancelDelay : Trick(Pattern.of(2, 4, 6, 0, 4, 8)) {

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {

        if (ctx.player.isPresent) {
            val delayedSpellsState = DelayedSpellStateSaverAndLoader.getServerState(ctx.world.server)
                .getPlayerState(ctx.player.get())

            delayedSpellsState.removeLastSpell()
        } else {
            throw NoPlayerBlunder(this)
        }

        return VoidFragment.INSTANCE
    }
}