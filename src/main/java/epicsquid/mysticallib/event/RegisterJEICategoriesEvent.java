package epicsquid.mysticallib.event;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterJEICategoriesEvent extends Event {
  protected IRecipeCategoryRegistration registry;
  protected IModPlugin plugin;

  public RegisterJEICategoriesEvent(IModPlugin plugin, IRecipeCategoryRegistration registry) {
    this.plugin = plugin;
    this.registry = registry;
  }

  public IModPlugin getPlugin() {
    return plugin;
  }

  public IRecipeCategoryRegistration getRegistry() {
    return registry;
  }
}
