package mod.master_bw3.swindler.DelayedSpell

import com.mojang.datafixers.util.Pair
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.Swindler.MOD_ID
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtOps
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import java.util.UUID

data class DelayedSpell(val spell: SpellPart, var triggerTick: Long, val caster: UUID?) {
    fun serialize(): NbtCompound {
        val nbt = NbtCompound()

        SpellPart.CODEC.encodeStart(NbtOps.INSTANCE, spell)
            .resultOrPartial { err: String? ->
                Swindler.logger.warn(
                    "Failed to save spell",
                    err
                )
            }
            .ifPresent { element: NbtElement? -> nbt.put("$MOD_ID:spell", element) }

        nbt.putLong("$MOD_ID:trigger_tick", triggerTick)

        nbt.putUuid("$MOD_ID:caster", caster)

        return nbt
    }

    companion object {
        fun deserialize(nbt: NbtCompound): DelayedSpell {
            var spell = SpellPart()

            if (nbt.contains("$MOD_ID:spell")) {
                SpellPart.CODEC.decode(NbtOps.INSTANCE, nbt["spell"])
                    .resultOrPartial { err: String? ->
                        Swindler.logger.warn(
                            "Failed to load spell",
                            err
                        )
                    }
                    .ifPresent { pair: Pair<SpellPart, NbtElement> ->
                        spell = pair.first
                    }
            }

            val triggerTick = nbt.getLong("$MOD_ID:trigger_tick")

            val caster = try {
                nbt.getUuid("$MOD_ID:caster")
            } catch (e: Exception) {
                null
            }

            return DelayedSpell(spell, triggerTick, caster)
        }
    }
}