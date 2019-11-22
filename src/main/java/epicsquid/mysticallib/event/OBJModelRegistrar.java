package epicsquid.mysticallib.event;

import com.google.common.collect.ImmutableMap;
import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.util.ObjUtils;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ForgeBlockStateV1.Transforms;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;

@OnlyIn(Dist.CLIENT)
public class OBJModelRegistrar {
  private static final TRSRTransformation THIRD_PERSON_BLOCK = Transforms.convert(0, 2.5f, 0, 75, 45, 0, 0.375f);
  private static final ImmutableMap<TransformType, TRSRTransformation> BLOCK_TRANSFORMS = ImmutableMap.<TransformType, TRSRTransformation>builder()
      .put(TransformType.GUI, Transforms.convert(0, 0, 0, 30, 225, 0, 0.625f))
      .put(TransformType.GROUND, Transforms.convert(0, 3, 0, 0, 0, 0, 0.25f))
      .put(TransformType.FIXED, Transforms.convert(0, 0, 0, 0, 0, 0, 0.5f))
      .put(TransformType.THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_BLOCK)
      .put(TransformType.THIRD_PERSON_LEFT_HAND, Transforms.leftify(THIRD_PERSON_BLOCK))
      .put(TransformType.FIRST_PERSON_RIGHT_HAND, Transforms.convert(0, 0, 0, 0, 45, 0, 0.4f))
      .put(TransformType.FIRST_PERSON_LEFT_HAND, Transforms.convert(0, 0, 0, 0, 225, 0, 0.4f))
      .build();

  public static void modelBake(ModelBakeEvent event) {
    for (ResourceLocation modelRL : ObjUtils.OBJ_MODELS) {
      IUnbakedModel model = ModelLoaderRegistry.getModelOrLogError(modelRL, "Unable to load OBJ: " + modelRL.toString());

      if (!(model instanceof OBJModel)) {
        MysticalLib.LOG.error("Tried to load " + modelRL.toString() + " as an OBJ model, but it isn't.");
      } else {
        IBakedModel bakedBlockModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), true), DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        IBakedModel bakedInvModelBase = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), true), DefaultVertexFormats.ITEM);
        if (bakedInvModelBase == null) {
          MysticalLib.LOG.error("Unable to bake inventory variant of OBJ model " + modelRL.toString());
          continue;
        }
        IBakedModel bakedInvModel = new PerspectiveMapWrapper(bakedInvModelBase, BLOCK_TRANSFORMS);

        event.getModelRegistry().put(new ModelResourceLocation(modelRL, ""), bakedBlockModel);
        event.getModelRegistry().put(new ModelResourceLocation(modelRL, "inventory"), bakedInvModel);
      }
    }
  }

  public static void textureStitchPre(TextureStitchEvent.Pre event) {
    if (event.getMap().getBasePath().equals("textures")) {
      for (ResourceLocation texRL : ObjUtils.OBJ_TEXTURES) {
        event.addSprite(texRL);
      }
    }
  }
}
