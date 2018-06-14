package epicsquid.mysticallib.model.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.DefaultTransformations;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.IModelState;

public class BakedModelItem implements IBakedModel {
  Function<ResourceLocation, TextureAtlasSprite> getter;
  VertexFormat format;
  ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
  List<TextureAtlasSprite> layers = new ArrayList<>();
  CustomModelItem model = null;
  List<BakedQuad> layerQuads = new ArrayList<>();

  public BakedModelItem(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, CustomModelItem model) {
    this.getter = bakedTextureGetter;
    this.format = format;
    int i = 0;
    this.model = model;
    while (model.textures.containsKey("layer" + i)) {
      layers.add(getter.apply(model.textures.get("layer" + i)));
      layerQuads.addAll(ItemLayerModel.getQuadsForSprite(i, layers.get(i), format, Optional.empty()));
      i++;
    }
  }

  @Override
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
    return layerQuads;
  }

  @Override
  public boolean isAmbientOcclusion() {
    return true;
  }

  @Override
  public boolean isGui3d() {
    return true;
  }

  @Override
  public boolean isBuiltInRenderer() {
    return false;
  }

  @Override
  public TextureAtlasSprite getParticleTexture() {
    return null;
  }

  @Override
  public ItemOverrideList getOverrides() {
    return new ItemOverrideList(Arrays.asList());
  }

  @Override
  public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
    Matrix4f matrix = null;
    if (model.handheld) {
      if (DefaultTransformations.handheldTransforms.containsKey(type)) {
        matrix = DefaultTransformations.handheldTransforms.get(type).getMatrix();
        return Pair.of(this, matrix);
      }
    } else {
      if (DefaultTransformations.itemTransforms.containsKey(type)) {
        matrix = DefaultTransformations.itemTransforms.get(type).getMatrix();
        return Pair.of(this, matrix);
      }
    }
    return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
  }

}
