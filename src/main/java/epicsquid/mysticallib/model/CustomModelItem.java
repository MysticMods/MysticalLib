package epicsquid.mysticallib.model;

import epicsquid.mysticallib.model.item.BakedModelItem;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class CustomModelItem extends CustomModelBase {
  public boolean handheld;

  public CustomModelItem(boolean handheld, @Nonnull ResourceLocation... textures) {
    this.handheld = handheld;
    for (int i = 0; i < textures.length; i++) {
      addTexture("layer" + i, textures[i]);
    }
  }

  @Nullable
  @Override
  public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
    return new BakedModelItem(format, spriteGetter, this);
  }

  @Override
  @Nonnull
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }
}
