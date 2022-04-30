package net.newlife.lore.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.newlife.lore.Main;
import net.newlife.lore.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES=
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<TombStoneEntity>> TOMB_STONE=
            BLOCK_ENTITIES.register("tomb_stone",()->
                    BlockEntityType.Builder.of(TombStoneEntity::new, ModBlocks.TOMBSTONE.get()).build(null));
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
