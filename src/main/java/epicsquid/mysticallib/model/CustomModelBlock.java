package epicsquid.mysticallib.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class CustomModelBlock extends CustomModelBase {
  Constructor<? extends BakedModelBlock> ctor;

  public CustomModelBlock(Class<? extends BakedModelBlock> block, ResourceLocation particle, ResourceLocation all) {
    try {
      ctor = block.getConstructor(IModelState.class, VertexFormat.class, Function.class, CustomModelBase.class);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    addTexture("particle", particle);
    addTexture("north", all);
    addTexture("south", all);
    addTexture("east", all);
    addTexture("west", all);
    addTexture("up", all);
    addTexture("down", all);
  }

  public CustomModelBlock(Class<? extends BakedModelBlock> block, ResourceLocation particle, ResourceLocation west, ResourceLocation east,
      ResourceLocation down, ResourceLocation up, ResourceLocation north, ResourceLocation south) {
    try {
      ctor = block.getConstructor(IModelState.class, VertexFormat.class, Function.class, CustomModelBase.class);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    addTexture("particle", particle);
    addTexture("north", north);
    addTexture("south", south);
    addTexture("east", east);
    addTexture("west", west);
    addTexture("up", up);
    addTexture("down", down);
  }

  public CustomModelBlock(Class<? extends BakedModelBlock> block, ResourceLocation particle, Pair<String, ResourceLocation>... textures) {
    try {
      ctor = block.getConstructor(IModelState.class, VertexFormat.class, Function.class, CustomModelBase.class);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }
    addTexture("particle", particle);
    for (Pair<String, ResourceLocation> p : textures) {
      addTexture(p.getLeft(), p.getRight());
    }
  }

  @Override
  public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    try {
      IBakedModel m = ctor.newInstance(state, format, bakedTextureGetter, this);
      return m;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return new BakedModelBlock(state, DefaultVertexFormats.BLOCK, bakedTextureGetter, this);
  }

  @Override
  public IModelState getDefaultState() {
    return TRSRTransformation.identity();
  }
}
