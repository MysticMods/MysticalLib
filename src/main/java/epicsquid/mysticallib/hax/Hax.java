package epicsquid.mysticallib.hax;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import com.google.gson.GsonBuilder;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Hax {
  public static Field modifiersField, bakedQuadFace, bakedQuadDiffuse, bakedQuadTint;

  public static Field field_ModelBakery_blockModelShapes;

  public static void init()
      throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException,
      InvocationTargetException {
    Field f = ReflectionHelper.findField(ModelBlock.class, "SERIALIZER", "field_178319_a");
    f.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);

    f.set(null,
        (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer()).registerTypeAdapter(BlockPart.class, new BPDeserializer())
            .registerTypeAdapter(BlockPartFace.class, new BPFDeserializer()).registerTypeAdapter(BlockFaceUV.class, new BFUVDeserializer())
            .registerTypeAdapter(ItemTransformVec3f.class, new ITV3FDeserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ICTDeserializer())
            .registerTypeAdapter(ItemOverride.class, new IODeserializer()).create());
    field_ModelBakery_blockModelShapes = ReflectionHelper.findField(ModelBakery.class, "blockModelShapes", "field_177610_k");
    field_ModelBakery_blockModelShapes.setAccessible(true);

    bakedQuadFace = ReflectionHelper.findField(BakedQuad.class, "face", "field_178214_c");
    bakedQuadFace.setAccessible(true);
    bakedQuadTint = ReflectionHelper.findField(BakedQuad.class, "tintIndex", "field_178213_b");
    bakedQuadTint.setAccessible(true);
    bakedQuadDiffuse = ReflectionHelper.findField(BakedQuad.class, "applyDiffuseLighting", "field_178214_f");
    bakedQuadDiffuse.setAccessible(true);
  }

  public static Class findClass(Class[] classes, String name) {
    for (Class c : classes) {
      if (c.getTypeName().contains(name)) {
        return c;
      }
    }
    return null;
  }
}
