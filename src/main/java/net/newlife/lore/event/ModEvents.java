package net.newlife.lore.event;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.newlife.lore.Main;
import net.newlife.lore.entity.custom.Keeper;
import net.newlife.lore.sound.ModSounds;
import net.newlife.lore.util.LoreTitleScreen;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onBurnDamage(LivingDamageEvent event)
    {
        if(event.getEntity() instanceof Keeper)
        {
            if(event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.ON_FIRE)|| event.getSource().equals(DamageSource.LIGHTNING_BOLT))
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void openGui(ScreenOpenEvent event) {
        if (event.getScreen() instanceof TitleScreen && !(event.getScreen() instanceof LoreTitleScreen)) {
            event.setScreen(new LoreTitleScreen());
        }
    }
}
