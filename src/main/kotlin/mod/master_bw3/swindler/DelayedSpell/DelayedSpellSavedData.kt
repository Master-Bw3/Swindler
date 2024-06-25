package mod.master_bw3.swindler.DelayedSpell

import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.PersistentState
import java.util.function.Supplier


class DelayedSpellSavedData : PersistentState {
    constructor()


    constructor(nbt: NbtCompound, world: ServerWorld) {
        DelayedSpellManager.readFromNbt(nbt, world)
    }


    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?): NbtCompound {
        val nbt = nbt ?: NbtCompound()

        DelayedSpellManager.writeToNbt(nbt)

        return nbt
    }
}