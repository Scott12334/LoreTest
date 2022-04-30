package net.newlife.lore.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.newlife.lore.block.ModBlocks;
import net.newlife.lore.block.custom.TombStone;
import net.newlife.lore.entity.ModEntityTypes;
import net.newlife.lore.entity.custom.Keeper;
import net.newlife.lore.util.PlayerDamageStore;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class ServerEvents {
    @SubscribeEvent
    public void tombstone(LivingDeathEvent event){
        if(event.getEntity() instanceof Player){
            BlockPos pos= new BlockPos(event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ());
            ((Player) event.getEntity()).setHealth(((Player) event.getEntity()).getMaxHealth());
            BlockState oldBlockState= event.getEntity().level.getBlockState(pos);
            event.getEntity().getLevel().sendBlockUpdated(pos,oldBlockState,ModBlocks.TOMBSTONE.get().defaultBlockState(), 1);
            event.getEntity().level.setBlock(pos, ModBlocks.TOMBSTONE.get().defaultBlockState(), 1);
            event.setCanceled(true);
        }
        if(event.getEntity() instanceof Keeper){
            Keeper dyingBoss= (Keeper) event.getEntity();
            dyingBoss.playDeath();
            if(dyingBoss.isReadyToDie()==true){
                event.setCanceled(true);
            }
            else{
                System.out.println("test");
                dyingBoss.setHealth(5);
                event.setCanceled(false);
            }
        }
    }
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event){
        Block blockIn = event.getState().getBlock();
        Player player= event.getPlayer();
        if(blockIn instanceof TombStone){

        }
    }

    @SubscribeEvent
    public void keeperHit(LivingDamageEvent event){
        if(event.getEntity() instanceof Keeper){
            if(event.getSource().getEntity() instanceof Player){
                Player attacker= (Player) event.getSource().getEntity();
                Keeper victim= (Keeper) event.getEntity();
                PlayerDamageStore newStore= new PlayerDamageStore(attacker);
                newStore.damageCaused(event.getAmount());
                victim.newAttacker(newStore);
            }
        }
    }
}
