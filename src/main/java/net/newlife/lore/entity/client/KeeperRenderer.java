package net.newlife.lore.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.newlife.lore.Main;
import net.newlife.lore.entity.custom.Keeper;
import org.checkerframework.checker.units.qual.K;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KeeperRenderer extends GeoEntityRenderer<Keeper> {
    public KeeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KeeperModel());
        this.shadowRadius=1;
    }
    @Override
    public ResourceLocation getTextureLocation(Keeper object) {
        return new ResourceLocation(Main.MOD_ID,"textures/entity/keeper/keeper.png");
    }
    @Override
    public RenderType getRenderType(Keeper animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
