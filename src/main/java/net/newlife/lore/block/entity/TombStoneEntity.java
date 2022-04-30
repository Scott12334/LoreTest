package net.newlife.lore.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.List;

public class TombStoneEntity extends BlockEntity {
    Player blockOwner;
    boolean playerSaved;
    static List entities;
    public TombStoneEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.TOMB_STONE.get(), pWorldPosition, pBlockState);
        playerSaved=false;
    }
    public void teleport(){
        if(blockOwner.getLevel().isClientSide){
            blockOwner.setHealth(blockOwner.getMaxHealth()-14);
            blockOwner.getFoodData().setFoodLevel(3);
            System.out.println("hurt please");
        }
        blockOwner.setHealth(blockOwner.getMaxHealth()-14);
        blockOwner.getFoodData().setFoodLevel(3);
        blockOwner.teleportTo(this.getBlockPos().getX(),this.getBlockPos().getY(),this.getBlockPos().getZ());
    }
    public void setBlockOwner(Player dead){
        blockOwner=dead;
    }
    public void savePlayer(){
        playerSaved=true;
    }
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TombStoneEntity pBlockEntity) {
        entities = pLevel.getEntitiesOfClass(Player.class, new AABB(pPos.getX()-2, pPos.getY()-2, pPos.getZ()-2, pPos.getX()+2, pPos.getY()+2, pPos.getZ()+2));
        if(entities.size()>=1 && pBlockEntity.playerSaved==false && pLevel.isClientSide==false){
            pBlockEntity.setBlockOwner((Player) entities.get(0));
            pBlockEntity.savePlayer();
            System.out.println("PlayerAdded");
            ((Player) entities.get(0)).teleportTo(18,64,-16);
        }

    }
}
