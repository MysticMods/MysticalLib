package epicsquid.mysticallib.model.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import epicsquid.mysticallib.model.CustomModelBase;
import epicsquid.mysticallib.model.DefaultTransformations;
import epicsquid.mysticallib.model.ModelUtil;
import epicsquid.mysticallib.model.parts.Cube;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class BakedModelWall extends BakedModelBlock {
  Cube post, north, south, west, east;

  public BakedModelWall(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    post = ModelUtil.makeCube(format, 0.25, 0, 0.25, 0.5, 1, 0.5, null, texes, -1);
    north = ModelUtil.makeCube(format, 0.3125, 0, 0, 0.375, 0.875, 0.5, null, texes, -1);
    south = ModelUtil.makeCube(format, 0.3125, 0, 0.5, 0.375, 0.875, 0.5, null, texes, -1);
    west = ModelUtil.makeCube(format, 0, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, -1);
    east = ModelUtil.makeCube(format, 0.5, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, -1);
  }

  @Override
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
    List<BakedQuad> quads = new ArrayList<BakedQuad>();
    if (state == null) {
      post.addToList(quads, side);
      west.addToList(quads, side);
      east.addToList(quads, side);
    } else {
      boolean up = state.getValue(BlockWall.UP);
      boolean cnorth = state.getValue(BlockWall.NORTH);
      boolean csouth = state.getValue(BlockWall.SOUTH);
      boolean cwest = state.getValue(BlockWall.WEST);
      boolean ceast = state.getValue(BlockWall.EAST);
      if (up) {
        post.addToList(quads, side);
      }
      if (cnorth) {
        north.addToList(quads, side);
      }
      if (csouth) {
        south.addToList(quads, side);
      }
      if (cwest) {
        west.addToList(quads, side);
      }
      if (ceast) {
        east.addToList(quads, side);
      }
    }
    return quads;
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
    return particle;
  }

  @Override
  public ItemOverrideList getOverrides() {
    return new ItemOverrideList(Arrays.asList());
  }

  @Override
  public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
    Matrix4f matrix = null;
    if (DefaultTransformations.blockTransforms.containsKey(type)) {
      matrix = DefaultTransformations.blockTransforms.get(type).getMatrix();
      return Pair.of(this, matrix);
    }
    return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
  }

}
