package net.newlife.lore.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.newlife.lore.Main;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class LoreTitleScreen extends TitleScreen {
    private String splash;

    private final boolean fading;
    private long fadeInStart;
    public LoreTitleScreen() {
        this.fading = false;
    }
    private static final ResourceLocation SPLASH =
            new ResourceLocation(Main.MOD_ID, "textures/gui/background/kaupenmenu.jpg");

    private static final ResourceLocation MINECRAFT_LOGO =
            new ResourceLocation("textures/gui/title/minecraft.png");

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        splash="Allow list edition";
        super.render(poseStack, mouseX, mouseY, partialTicks);
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }
        int width = this.width;
        int height = this.height;

        drawCustomTitleScreen(poseStack, width, height);
        drawMinecraftLogo(poseStack);

        ForgeHooksClient.renderMainMenu(this, poseStack, this.getMinecraft().font, width, height, -1);
        drawString(poseStack, this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!",
                width - this.font.width("Copyright Mojang AB. Do not distribute!") - 2,
                height - 10, 0xFFFFFFFF);
        for (Widget widget : this.renderables) {
            widget.render(poseStack, mouseX, mouseY, partialTicks);
        }
        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = Mth.ceil(f1 * 255.0F) << 24;
        if (this.splash != null) {
            poseStack.pushPose();
            poseStack.translate((double)(this.width / 2 + 90), 70.0D, 0.0D);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
            float f2 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
            f2 = f2 * 100.0F / (float)(this.font.width(this.splash) + 32);
            poseStack.scale(f2, f2, f2);
            drawCenteredString(poseStack, this.font, this.splash, 0, -8, 16776960 | l);
            poseStack.popPose();
        }

    }

    private void drawMinecraftLogo(@NotNull PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MINECRAFT_LOGO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);

        this.blitOutlineBlack(this.width / 2 - 137, 30, (p_169447_, p_169448_) -> {
            this.blit(poseStack, p_169447_ + 0, p_169448_, 0, 0, 99, 44);
            this.blit(poseStack, p_169447_ + 99, p_169448_, 129, 0, 27, 44);
            this.blit(poseStack, p_169447_ + 99 + 26, p_169448_, 126, 0, 3, 44);
            this.blit(poseStack, p_169447_ + 99 + 26 + 3, p_169448_, 99, 0, 26, 44);
            this.blit(poseStack, p_169447_ + 155, p_169448_, 0, 45, 155, 44);
        });
    }

    private void drawCustomTitleScreen(@NotNull PoseStack poseStack, int width, int height) {
        RenderSystem.enableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SPLASH);
        blit(poseStack, 0, 0, 0, 0, this.width, this.height, width, height);
    }
}
