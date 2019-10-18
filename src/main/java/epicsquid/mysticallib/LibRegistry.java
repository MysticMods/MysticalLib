package epicsquid.mysticallib;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID)
public class LibRegistry {

  private static final String SWORD = "SWORD";
  private static final String KNIFE = "KNIFE";
  private static final String PICKAXE = "PICKAXE";
  private static final String AXE = "AXE";
  private static final String SHOVEL = "SHOVEL";
  private static final String HOE = "HOE";
  private static final String SPEAR = "SPEAR";

  private static ArrayList<SoundEvent> sounds = new ArrayList<>();

  @SubscribeEvent
  public void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
    for (SoundEvent s : sounds) {
      event.getRegistry().register(s);
    }
  }
}
