package epicsquid.mysticallib.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CustomModelBase implements IUnbakedModel {
  public Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

  public CustomModelBase() {
  }

  @Nonnull
  public CustomModelBase addTexture(@Nonnull String name, @Nonnull ResourceLocation texture) {
    textures.put(name, texture);
    return this;
  }

  @Override
  @Nonnull
  public Collection<ResourceLocation> getDependencies() {
    return ImmutableList.of();
  }

  @Override
  @Nonnull
  public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
    return textures.values();
  }

  @Nullable
  @Override
  public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
    return new BakedModelNull(DefaultVertexFormats.BLOCK, spriteGetter);
  }

  @Override
  @Nonnull
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }
}
