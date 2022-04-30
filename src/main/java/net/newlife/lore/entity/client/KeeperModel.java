package net.newlife.lore.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.newlife.lore.Main;
import net.newlife.lore.entity.custom.Keeper;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KeeperModel extends AnimatedGeoModel<Keeper> {
    @Override
    public ResourceLocation getModelLocation(Keeper object) {
        return new ResourceLocation(Main.MOD_ID,"geo/keeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Keeper object) {
        return new ResourceLocation(Main.MOD_ID,"textures/entity/keeper/keeper.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Keeper animatable) {
        return new ResourceLocation(Main.MOD_ID,"animations/keeper.animation.json");
    }
}
