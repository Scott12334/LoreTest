package net.newlife.lore.util;

import net.minecraft.world.entity.player.Player;

public class PlayerDamageStore {
    float damageCaused;
    Player player;
    public PlayerDamageStore(Player player){
        this.player=player;
    }
    public void damageCaused(float damageCaused){
        this.damageCaused+=damageCaused;
    }
    public float getDamageCaused(){
        return damageCaused;
    }
    public Player getPlayer(){
        return player;
    }
}
