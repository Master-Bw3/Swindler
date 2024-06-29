package mod.master_bw3.swindler.spell.tricks.delay

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import dev.enjarai.trickster.spell.tricks.blunder.NoPlayerBlunder
import mod.master_bw3.swindler.delayedSpell.DelayedSpellStateSaverAndLoader


object Delay : Trick(Pattern.of(0, 6, 4, 2, 8, 4, 0)) {

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {
        val spell = expectInput(fragments, FragmentType.SPELL_PART, 0)
        val delay = expectInput(fragments, FragmentType.NUMBER, 1)

        if (ctx.player.isPresent) {
            val delayedSpellsState = DelayedSpellStateSaverAndLoader.getServerState(ctx.world.server)
                .getPlayerState(ctx.player.get())

            delayedSpellsState.addSpell(
                spell,
                delay.number.toLong() * ctx.world.server.tickManager.tickRate.toLong(),
            )
        } else {
            throw NoPlayerBlunder(this)
        }

        return VoidFragment.INSTANCE
    }
}