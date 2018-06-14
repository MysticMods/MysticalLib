package epicsquid.mysticallib.recipe;


import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.event.RegisterModRecipesEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeRegistry {
  public static ResourceLocation getRL(String s) {
    return new ResourceLocation(LibRegistry.getActiveModid() + ":" + s);
  }

  public static void registerShaped(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapedOreRecipe(getRL(name), result, ingredients).setRegistryName(getRL(name)));
  }

  public static void registerShapedMirrored(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapedOreRecipe(getRL(name), result, ingredients).setMirrored(true).setRegistryName(getRL(name)));
  }

  public static void registerShapeless(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapelessOreRecipe(getRL(name), result, ingredients).setRegistryName(getRL(name)));
  }

  @SubscribeEvent
  public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
    MinecraftForge.EVENT_BUS.post(new RegisterModRecipesEvent(event.getRegistry()));
  }
}
