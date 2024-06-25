package mod.master_bw3.swindler.spell.tricks.basic

import dev.enjarai.trickster.spell.Fragment
import dev.enjarai.trickster.spell.Pattern
import dev.enjarai.trickster.spell.SpellContext
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VectorFragment
import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException
import mod.master_bw3.swindler.spell.tricks.blunder.InvalidEntityBlunder


object GetPos : Trick(Pattern.of(1, 3, 4, 5, 1)) {

    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment?>?): Fragment {
        val entityFragment = expectInput(fragments, FragmentType.ENTITY, 0)
        val entity = ctx.world.getEntity(entityFragment.uuid) ?: throw InvalidEntityBlunder(this)

        return VectorFragment.of(entity.eyePos)
    }
}