package net.newlife.lore.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.newlife.lore.Main;
import net.newlife.lore.entity.custom.Keeper;
import net.newlife.lore.entity.custom.ObsidianCreeper;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES=
            DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final RegistryObject<EntityType<Keeper>> KEEPER = ENTITY_TYPES.register("keeper",
            () -> EntityType.Builder.of(Keeper::new, MobCategory.MONSTER)
                    .sized(1f, 4f)
                    .fireImmune()
                    .build(new ResourceLocation(Main.MOD_ID, "keeper").toString()));

    public static final RegistryObject<EntityType<ObsidianCreeper>> OBSIDIAN_CREEPER = ENTITY_TYPES.register("obsidian_creeper",
            () -> EntityType.Builder.of(ObsidianCreeper::new, MobCategory.MONSTER)
                    .sized(1f, 2f)
                    .build(new ResourceLocation(Main.MOD_ID, "obsidian_creeper").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
