package mod.master_bw3.swindler.entityRenderer

import com.mojang.blaze3d.systems.RenderSystem
import dev.enjarai.trickster.render.SpellCircleRenderer
import dev.enjarai.trickster.spell.SpellPart
import mod.master_bw3.swindler.Swindler
import mod.master_bw3.swindler.entity.SpellEmitterEntity
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.render.entity.EndCrystalEntityRenderer
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.GuardianEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Colors
import net.minecraft.util.Identifier
import net.minecraft.util.math.RotationAxis
import java.util.*


class SpellEmitterEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<SpellEmitterEntity>(ctx) {
    private val renderer: SpellCircleRenderer = SpellCircleRenderer()

    override fun getTexture(entity: SpellEmitterEntity?): Identifier = Swindler.id("textures/entity/spell_emitter.png")

    override fun render(
        entity: SpellEmitterEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        matrices.push()
        matrices.translate(0f, 0.5f, 0f)

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.yaw))
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180 + entity.pitch))

        this.renderer.renderPart(matrices,
            vertexConsumers,
            Optional.ofNullable(entity.spell),
            0f,
            0f,
            0.5f,
            0.0,
            tickDelta
        ) { 1f }
        matrices.pop()

    }

}