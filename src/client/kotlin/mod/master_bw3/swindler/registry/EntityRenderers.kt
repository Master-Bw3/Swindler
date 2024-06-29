package mod.master_bw3.swindler.registry

import mod.master_bw3.swindler.entityRenderer.SpellEmitterEntityRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry

object EntityRenderers {


    fun register() {
        EntityRendererRegistry.register(Entities.SPELL_EMITTER_ENTITY) { context -> SpellEmitterEntityRenderer(context) }
    }
}