package epicsquid.mysticallib.event;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterJEIHandlingEvent extends Event {
  protected IModRegistry registry;
  protected IModPlugin plugin;

  public RegisterJEIHandlingEvent(IModPlugin plugin, IModRegistry registry) {
    this.plugin = plugin;
    this.registry = registry;
  }

  public IModPlugin getPlugin() {
    return plugin;
  }

  public IModRegistry getRegistry() {
    return registry;
  }
}
