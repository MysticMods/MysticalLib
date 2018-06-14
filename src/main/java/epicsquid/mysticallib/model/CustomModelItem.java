package epicsquid.mysticallib.model;

import java.util.function.Function;

import epicsquid.mysticallib.model.item.BakedModelItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class CustomModelItem extends CustomModelBase {
  public boolean handheld = false;

  public CustomModelItem(boolean handheld, ResourceLocation... textures) {
    this.handheld = handheld;
    for (int i = 0; i < textures.length; i++) {
      addTexture("layer" + i, textures[i]);
    }
  }

  @Override
  public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    return new BakedModelItem(state, format, bakedTextureGetter, this);
  }

  @Override
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }
}
