package epicsquid.mysticallib.model;

import epicsquid.mysticallib.event.RegisterCustomModelsEvent;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CustomModelLoader implements ICustomModelLoader {

  public static Map<ResourceLocation, IUnbakedModel> blockmodels = new HashMap<>();
  public static Map<ResourceLocation, IUnbakedModel> itemmodels = new HashMap<>();

  public static FaceBakery faceBakery = new FaceBakery();

  @Override
  public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
    blockmodels.clear();
    itemmodels.clear();
    MinecraftForge.EVENT_BUS.post(new RegisterCustomModelsEvent());
  }

  @Override
  public boolean accepts(@Nonnull ResourceLocation modelLocation) {
    return blockmodels.containsKey(modelLocation) || itemmodels.containsKey(modelLocation);
  }

  @Override
  @Nonnull
  public IUnbakedModel loadModel(@Nonnull ResourceLocation modelLocation) {
    if (blockmodels.containsKey(modelLocation)) {
      return blockmodels.get(modelLocation);
    } else if (itemmodels.containsKey(modelLocation)) {
      return itemmodels.get(modelLocation);
    }
    // TODO return a null model, not null
    return null;
  }
}
