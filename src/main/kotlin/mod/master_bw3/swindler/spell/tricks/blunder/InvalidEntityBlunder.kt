package mod.master_bw3.swindler.spell.tricks.blunder

import dev.enjarai.trickster.spell.tricks.Trick
import dev.enjarai.trickster.spell.tricks.blunder.TrickBlunderException
import net.minecraft.text.MutableText


class InvalidEntityBlunder(source: Trick?) : TrickBlunderException(source) {
    override fun createMessage(): MutableText {
        return super.createMessage().append("Entity not found")
    }
}