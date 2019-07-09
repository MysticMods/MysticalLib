package epicsquid.mysticallib;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID)
public class LibRegistry {

  private static ArrayList<SoundEvent> sounds = new ArrayList<>();

  @SubscribeEvent
  public void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
    for (SoundEvent s : sounds) {
      event.getRegistry().register(s);
    }
  }
}
