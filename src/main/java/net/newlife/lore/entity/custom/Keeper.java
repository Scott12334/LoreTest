package net.newlife.lore.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.newlife.lore.block.ModBlocks;
import net.newlife.lore.entity.ModEntityTypes;
import net.newlife.lore.sound.ModSounds;
import net.newlife.lore.util.PlayerDamageStore;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;

public class Keeper extends Monster implements IAnimatable {
    private AnimationFactory factory= new AnimationFactory(this);
    public boolean stripped;
    int lightningCooldown;
    List entities;
    ArrayList<PlayerDamageStore> attackers;
    PlayerDamageStore mostDamage;
    int creeperCooldown;
    int lavalCooldown;
    int blazeCooldown;
    int darknessEffect;
    float oldHealth;
    float oldHealth33;
    boolean readyToDie;
    int tillDie;
    boolean outroPlayed;
    public Keeper(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        stripped=false;
        lightningCooldown=20*30; //has sounds
        creeperCooldown=20*60; //has sound
        lavalCooldown=20*45; //has sound
        blazeCooldown=20*15; //has sound
        darknessEffect=0;
        oldHealth=this.getMaxHealth();
        oldHealth33=this.getMaxHealth();
        readyToDie=false;
        tillDie=0;
        outroPlayed=false;
        attackers= new ArrayList<PlayerDamageStore>();
        entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX()-100, this.getY()-15, this.getZ()-100, this.getX()+100, this.getY()+15, this.getZ()+100));
    }

    public void newAttacker(PlayerDamageStore newStore){
        boolean alreadyAttacked=false;
        for(int i=0; i<attackers.size();i++){
            if(mostDamage==null){
                mostDamage=newStore;
                attackers.add(newStore);
            }
            else if(attackers.get(i).getPlayer()==newStore.getPlayer()){
                attackers.get(i).damageCaused(newStore.getDamageCaused());
                alreadyAttacked=true;
                break;
            }
        }
        if(alreadyAttacked==false){
            attackers.add(newStore);
        }
    }
    public boolean isReadyToDie(){
        return readyToDie;
    }
    @Override
    public void tick(){
        super.tick();
        if(stripped==false){
            entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX()-100, this.getY()-15, this.getZ()-100, this.getX()+100, this.getY()+15, this.getZ()+100));
            for(int i=0;i<entities.size();i++){
                if(i==0){
                    mostDamage= new PlayerDamageStore((Player) entities.get(i));
                }
                Player currentPlayer= (Player) entities.get(i);
                this.level.playSound(currentPlayer,currentPlayer.getOnPos(), ModSounds.OPENING_LINE.get(), SoundSource.HOSTILE,1,1);
                currentPlayer.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                currentPlayer.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                currentPlayer.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
                currentPlayer.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
            }
            stripped=true;
        }
        if(outroPlayed==false) {

            for (int i = 0; i < attackers.size(); i++) {
                if (mostDamage != null) {
                    if (attackers.get(i).getDamageCaused() > mostDamage.getDamageCaused()) {
                        mostDamage = attackers.get(i);
                    }
                }
            }
            if (lightningCooldown <= 0) {
                entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX() - 10, this.getY() - 10, this.getZ() - 10, this.getX() + 10, this.getY() + 10, this.getZ() + 10));
                if (entities.size() >= 1) {
                    Player victim = (Player) entities.get(0);
                    this.level.playSound(victim, victim.getOnPos(), ModSounds.LIGHTNING_SWING.get(), SoundSource.HOSTILE, 1, 1);
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, victim.level);
                    bolt.setPos(victim.getX(), victim.getY(), victim.getZ());
                    bolt.setDamage(10);
                    victim.level.addFreshEntity(bolt);
                    System.out.println("strike me down");
                    for (int i = 0; i < 10; i++) {
                        LightningBolt randomBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, victim.level);
                        int randomX = random.nextInt(-10, 10);
                        int randomZ = random.nextInt(-10, 10);
                        while (randomX <= 2 && randomX >= -2) {
                            randomX = random.nextInt(-10, 10);
                        }
                        while (randomZ <= 2 && randomZ >= -2) {
                            randomZ = random.nextInt(-10, 10);
                        }
                        randomBolt.setPos(this.getX() + randomX, this.getY() - 1, this.getZ() + randomZ);
                        victim.level.addFreshEntity(randomBolt);
                    }
                    lightningCooldown = 20 * 30;
                }
            }
            if (creeperCooldown <= 0) {
                entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX() - 100, this.getY() - 100, this.getZ() - 100, this.getX() + 100, this.getY() + 100, this.getZ() + 100));
                for (int i = 0; i < entities.size(); i++) {
                    Player victim = (Player) entities.get(i);
                    this.level.playSound(victim, victim.getOnPos(), ModSounds.CREEPER.get(), SoundSource.HOSTILE, 1, 1);
                }
                for (int i = 0; i < 6; i++) {
                    ObsidianCreeper creeperMinion = new ObsidianCreeper(ModEntityTypes.OBSIDIAN_CREEPER.get(), this.level);
                    int randomX = random.nextInt(-10, 10);
                    int randomZ = random.nextInt(-10, 10);
                    creeperMinion.setPos(this.getX() + randomX, this.getY() + 2, this.getZ() + randomZ);
                    this.level.addFreshEntity(creeperMinion);
                }
                creeperCooldown = 20 * 60;
            }
            if (lavalCooldown <= 0) {
                entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX() - 100, this.getY() - 100, this.getZ() - 100, this.getX() + 100, this.getY() + 100, this.getZ() + 100));
                for (int i = 0; i < entities.size(); i++) {
                    Player victim = (Player) entities.get(i);
                    this.level.playSound(victim, victim.getOnPos(), ModSounds.LAVA_DROP.get(), SoundSource.HOSTILE, 1, 1);
                }
                int randomX = random.nextInt(2, 10);
                int randomZ = random.nextInt(2, 10);
                BlockPos pos = new BlockPos(this.getX() + randomX, this.getY(), this.getZ() + randomZ);
                BlockState oldBlockState = this.level.getBlockState(pos);
                this.level.sendBlockUpdated(pos, oldBlockState, ModBlocks.TOMBSTONE.get().defaultBlockState(), 1);
                this.level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 1);
                System.out.println("lavaaa");
                lavalCooldown = 20 * 45;
            }
            if (blazeCooldown <= 0) {
                entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX() - 100, this.getY() - 100, this.getZ() - 100, this.getX() + 100, this.getY() + 100, this.getZ() + 100));
                for (int i = 0; i < entities.size(); i++) {
                    Player victim = (Player) entities.get(i);
                    this.level.playSound(victim, victim.getOnPos(), ModSounds.FIREBALLS.get(), SoundSource.HOSTILE, 1, 1);
                }
                if (entities.size() >= 1) {
                    Player victim = (Player) entities.get(0);
                    for (int i = 0; i < 10; i++) {
                        double d0 = this.distanceToSqr(random.nextDouble(5, 15), random.nextDouble(5, 15), random.nextDouble(5, 15));
                        double d1 = random.nextDouble(this.getX() - 15, this.getX() + 15) - this.getX();
                        double d2 = this.getY() + 5 - this.getY(0.5D);
                        double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
                        double d3 = random.nextDouble(this.getZ() - 15, this.getZ() + 15) - this.getZ();
                        SmallFireball smallfireball = new SmallFireball(this.level, this, d1 + this.getRandom().nextGaussian() * d4, -5, d3 + this.getRandom().nextGaussian() * d4);
                        smallfireball.setPos(smallfireball.getX(), this.getY() + 3, smallfireball.getZ());
                        victim.level.addFreshEntity(smallfireball);
                    }
                    blazeCooldown = random.nextInt(100, 500);
                }
            }
            if (this.oldHealth33 >= this.getHealth() + (this.getMaxHealth() * .33) && oldHealth33 > 0) {
                entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX() - 100, this.getY() - 100, this.getZ() - 100, this.getX() + 100, this.getY() + 100, this.getZ() + 100));
                for (int i = 0; i < entities.size(); i++) {
                    Player currentPlayer = (Player) entities.get(i);
                    currentPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 15, 1));
                    this.level.playSound(currentPlayer, currentPlayer.getOnPos(), ModSounds.DARKNESS.get(), SoundSource.HOSTILE, 1, 1);
                }
                this.oldHealth33 = this.getHealth();
                darknessEffect = 300;
            }
            if (darknessEffect > 0) {
                this.setSpeed(.3f);
                darknessEffect--;
            } else {
                this.setSpeed(.2f);
            }
            if (this.oldHealth >= this.getHealth() + (this.getMaxHealth() * .05) && oldHealth > 0) {
                this.oldHealth = this.getHealth();
                if (mostDamage != null) {
                    Player highestDamage = mostDamage.getPlayer();
                    if (highestDamage.level.isClientSide == false) {
                        teleportTo(highestDamage.getX(), highestDamage.getY(), highestDamage.getZ());
                        //highestDamage.setHealth(highestDamage.getHealth() - 14);
                        System.out.println("5% attack");
                    }
                }
            }
        }else{
            this.setDeltaMovement(0,0,0);
        }
        if(tillDie>0){
            tillDie--;
        }
        if(tillDie<=0 && outroPlayed==true){
            readyToDie=true;
            this.kill();
        }
        blazeCooldown--;
        lavalCooldown--;
        creeperCooldown--;
        lightningCooldown--;
    }
    public void playDeath(){
        entities = this.level.getEntitiesOfClass(Player.class, new AABB(this.getX()-100, this.getY()-100, this.getZ()-100, this.getX()+100, this.getY()+100, this.getZ()+100));
        for(int i=0; i<entities.size();i++){
            if(outroPlayed==false){
                Player currentPlayer= (Player) entities.get(i);
                this.level.playSound(currentPlayer,currentPlayer.getOnPos(), ModSounds.OUTRO.get(), SoundSource.HOSTILE,1,1);
                tillDie=20*12;
                outroPlayed=true;
            }
        }
    }
    public static AttributeSupplier setAttributes(){
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH,50)
                .add(Attributes.ATTACK_DAMAGE,8)
                .add(Attributes.ATTACK_SPEED,5)
                .add(Attributes.MOVEMENT_SPEED,.2f)
                .build();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2,new MeleeAttackGoal(this,1,true));
        this.targetSelector.addGoal(6, (new HurtByTargetGoal(this)).setAlertOthers());
    }
    protected void playSetpSound(BlockPos pos, BlockState blockIn){
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,0.15f,1f);
    }
    protected SoundEvent getAmbientSound(){
        return SoundEvents.CAT_STRAY_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn){
        return SoundEvents.DOLPHIN_HURT;
    }
    protected SoundEvent getDeathSound(){
        return SoundEvents.DOLPHIN_DEATH;
    }
    protected float getSoundVolume(){
        return 0.2F;
    }
    /* ANIMATIONS */

    /*private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.keeper.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.keeper.idle", true));
        return PlayState.CONTINUE;
    }
*/
    @Override
    public void registerControllers(AnimationData data) {

    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


}
