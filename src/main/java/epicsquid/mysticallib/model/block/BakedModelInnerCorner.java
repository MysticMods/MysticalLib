package epicsquid.mysticallib.model.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import epicsquid.mysticallib.block.BlockCornerBase;
import epicsquid.mysticallib.model.CustomModelBase;
import epicsquid.mysticallib.model.DefaultTransformations;
import epicsquid.mysticallib.model.ModelUtil;
import epicsquid.mysticallib.model.parts.Segment;
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

public class BakedModelInnerCorner extends BakedModelBlock {
  Segment segm_down_nxnz_1, segm_down_pxnz_1, segm_down_pxpz_1, segm_down_nxpz_1;
  Segment segm_down_nxnz_2, segm_down_pxnz_2, segm_down_pxpz_2, segm_down_nxpz_2;
  Segment segm_up_nxnz_1, segm_up_pxnz_1, segm_up_pxpz_1, segm_up_nxpz_1;
  Segment segm_up_nxnz_2, segm_up_pxnz_2, segm_up_pxpz_2, segm_up_nxpz_2;

  public BakedModelInnerCorner(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
      CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    segm_down_nxnz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, true, false }, texes,
            -1);
    segm_down_nxnz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, new boolean[] { false, true, false, true, true, true }, texes,
            -1);
    segm_down_pxnz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, new boolean[] { true, true, true, true, true, false }, texes,
            -1);
    segm_down_pxnz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, new boolean[] { true, false, false, true, true, true }, texes,
            -1);
    segm_down_pxpz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, new boolean[] { true, true, true, true, false, true }, texes,
            -1);
    segm_down_pxpz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, new boolean[] { true, false, false, true, true, true }, texes,
            -1);
    segm_down_nxpz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, false, true }, texes,
            -1);
    segm_down_nxpz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, new boolean[] { false, true, false, true, true, true }, texes,
            -1);
    segm_up_nxnz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, true, false }, texes,
            -1);
    segm_up_nxnz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { false, true, true, false, true, true }, texes,
            -1);
    segm_up_pxnz_1 = ModelUtil
        .makeSegm(format, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, true, false }, texes,
            -1);
    segm_up_pxnz_2 = ModelUtil
        .makeSegm(format, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, false, true, false, true, true }, texes,
            -1);
    segm_up_pxpz_1 = ModelUtil
        .makeSegm(format, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, false, true }, texes,
            -1);
    segm_up_pxpz_2 = ModelUtil
        .makeSegm(format, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, false, true, false, true, true }, texes,
            -1);
    segm_up_nxpz_1 = ModelUtil
        .makeSegm(format, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { true, true, true, true, false, true }, texes,
            -1);
    segm_up_nxpz_2 = ModelUtil
        .makeSegm(format, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, new boolean[] { false, true, true, false, true, true }, texes,
            -1);
  }

  @Override
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
    List<BakedQuad> quads = new ArrayList<BakedQuad>();
    if (state == null) {
      segm_down_pxpz_1.addToList(quads, side);
      segm_down_pxpz_2.addToList(quads, side);
    } else {
      boolean up = state.getValue(BlockCornerBase.UP);
      int dir = state.getValue(BlockCornerBase.DIR);
      if (!up) {
        switch (dir) {
        case 2:
          segm_down_nxnz_1.addToList(quads, side);
          segm_down_nxnz_2.addToList(quads, side);
          break;
        case 3:
          segm_down_pxnz_1.addToList(quads, side);
          segm_down_pxnz_2.addToList(quads, side);
          break;
        case 0:
          segm_down_pxpz_1.addToList(quads, side);
          segm_down_pxpz_2.addToList(quads, side);
          break;
        case 1:
          segm_down_nxpz_1.addToList(quads, side);
          segm_down_nxpz_2.addToList(quads, side);
          break;
        }
      } else {
        switch (dir) {
        case 2:
          segm_up_nxnz_1.addToList(quads, side);
          segm_up_nxnz_2.addToList(quads, side);
          break;
        case 3:
          segm_up_pxnz_1.addToList(quads, side);
          segm_up_pxnz_2.addToList(quads, side);
          break;
        case 0:
          segm_up_pxpz_1.addToList(quads, side);
          segm_up_pxpz_2.addToList(quads, side);
          break;
        case 1:
          segm_up_nxpz_1.addToList(quads, side);
          segm_up_nxpz_2.addToList(quads, side);
          break;
        }
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
