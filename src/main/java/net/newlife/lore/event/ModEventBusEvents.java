package net.newlife.lore.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.newlife.lore.Main;
import net.newlife.lore.entity.ModEntityTypes;
import net.newlife.lore.entity.custom.Keeper;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.KEEPER.get(), Keeper.setAttributes());
        event.put(ModEntityTypes.OBSIDIAN_CREEPER.get(), Keeper.setAttributes());
    }
    @SubscribeEvent
    public void onBurnDamage(LivingDamageEvent event)
    {
        if(event.getEntity() instanceof Keeper)
        {
            if(event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.LAVA) || event.getSource().equals(DamageSource.ON_FIRE)) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public void keeperHit(AttackEntityEvent event){
        System.out.println("hitt");
    }

}
