package net.newlife.lore.event;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.newlife.lore.Main;
import net.newlife.lore.entity.custom.Keeper;
import net.newlife.lore.util.LoreTitleScreen;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onBurnDamage(LivingDamageEvent event)
    {
        if(event.getEntity() instanceof Keeper)
        {
            if(event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.ON_FIRE))
            {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public void keeperHit(AttackEntityEvent event){
        System.out.println("hitt");
    }
}
