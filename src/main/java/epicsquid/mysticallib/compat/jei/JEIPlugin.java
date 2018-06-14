package epicsquid.mysticallib.compat.jei;


import epicsquid.mysticallib.event.RegisterJEICategoriesEvent;
import epicsquid.mysticallib.event.RegisterJEIHandlingEvent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.common.MinecraftForge;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
  @Override
  public void register(IModRegistry registry) {
    MinecraftForge.EVENT_BUS.post(new RegisterJEIHandlingEvent(this, registry));
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    MinecraftForge.EVENT_BUS.post(new RegisterJEICategoriesEvent(this, registry));
  }
}
