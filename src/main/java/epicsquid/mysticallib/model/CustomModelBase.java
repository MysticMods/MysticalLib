package epicsquid.mysticallib.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class CustomModelBase implements IModel {
  public Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

  public CustomModelBase() {
  }

  public CustomModelBase addTexture(String name, ResourceLocation texture) {
    textures.put(name, texture);
    return this;
  }

  @Override
  public Collection<ResourceLocation> getDependencies() {
    return ImmutableList.of();
  }

  @Override
  public Collection<ResourceLocation> getTextures() {
    return textures.values();
  }

  @Override
  public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    return new BakedModelNull(state, DefaultVertexFormats.BLOCK, bakedTextureGetter, this);
  }

  @Override
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }
}
