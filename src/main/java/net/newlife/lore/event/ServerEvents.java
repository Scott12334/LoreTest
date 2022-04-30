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
    }
    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event){
        Block blockIn = event.getState().getBlock();
        Player player= event.getPlayer();
        if(blockIn instanceof TombStone){

        }

    }
}
