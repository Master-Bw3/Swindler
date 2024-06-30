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
import dev.enjarai.trickster.spell.tricks.blunder.NoPlayerBlunder
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.nbt.NbtCompound
import java.util.*


object Beam : Trick(Pattern.of(3, 4, 5)) {
    @Throws(BlunderException::class)
    override fun activate(ctx: SpellContext, fragments: List<Fragment>): Fragment {
        val entity = ctx.world.getEntity(expectInput(fragments, FragmentType.ENTITY, 0).uuid)
        val length = expectInput(fragments, FragmentType.NUMBER, 1).number
        val strength = expectInput(fragments, FragmentType.NUMBER, 2).number
        val duration = expectInput(fragments, FragmentType.NUMBER, 3).number

        val displaySpell = SpellPart(PatternGlyph(3, 4, 5), fragments.map { Optional.of(SpellPart(it, listOf())) })


        if (entity is SpellEmitterEntity) {
            entity.spell = displaySpell
            entity.effectId = Swindler.id("beam")
            entity.effectData = run {
                val data = NbtCompound()
                data.putDouble(Swindler.id("length").toString(), length)
                data.putDouble(Swindler.id("strength").toString(), strength)
                data.putInt(Swindler.id("duration").toString(), (duration * ctx.world.tickManager.tickRate).toInt())
                data
            }
        } else throw NoPlayerBlunder(this) //todo: use proper blunder


        return VoidFragment.INSTANCE
    }
}