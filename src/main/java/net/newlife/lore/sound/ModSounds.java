package net.newlife.lore.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.newlife.lore.Main;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS=
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static RegistryObject<SoundEvent> OPENING_LINE=
            registerSoundEvents("opening_line");
    public static RegistryObject<SoundEvent> CREEPER=
            registerSoundEvents("creeper");
    public static RegistryObject<SoundEvent> DARKNESS=
            registerSoundEvents("darkness");
    public static RegistryObject<SoundEvent> FIREBALLS=
            registerSoundEvents("fireballs");
    public static RegistryObject<SoundEvent> LAVA_DROP=
            registerSoundEvents("lava_drop");
    public static RegistryObject<SoundEvent> LIGHTNING_SWING=
            registerSoundEvents("lightning_swing");
    public static RegistryObject<SoundEvent> OUTRO=
            registerSoundEvents("outro");
    private static RegistryObject<SoundEvent> registerSoundEvents(String name){
        ResourceLocation id= new ResourceLocation(Main.MOD_ID, name);
        return SOUND_EVENTS.register(name, ()-> new SoundEvent(id));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
