package mod.master_bw3.swindler.spell.tricks.spellEmitter

import dev.enjarai.trickster.spell.*
import dev.enjarai.trickster.spell.fragment.EntityFragment
import dev.enjarai.trickster.spell.fragment.FragmentType
import dev.enjarai.trickster.spell.fragment.VoidFragment
import dev.enjarai.trickster.spell.tricks.Trick
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import org.joml.Vector3d
import java.util.*

object CreateSpellEmitter : Trick(Pattern.of(0, 1, 2, 8, 7, 6, 0)) {
    override fun activate(ctx: SpellContext, fragments: MutableList<Fragment>): Fragment {
        val pos = expectInput(fragments, FragmentType.VECTOR, 0).vector
        val rotation = expectInput(fragments, FragmentType.VECTOR, 1).vector

        val target = Vector3d()
        rotation.normalize(target)
        target.add(pos)

        val emitter = SpellEmitterEntity(ctx.world)
        emitter.setPos(pos.x(), pos.y(), pos.z())
        emitter.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET,
            Vec3d(target.x(), target.y(), target.z()))

        ctx.world.spawnEntity(emitter)

        return EntityFragment(emitter.uuid, Text.translatable("entity.swindler.emitter"))
    }
}