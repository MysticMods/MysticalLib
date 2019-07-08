package epicsquid.mysticallib.model;

import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;
import java.util.HashMap;
import java.util.Map;

public class DefaultTransformations {

  @Nonnull
  public static TRSRTransformation create(float translateX, float translateY, float translateZ, float rotateX, float rotateY, float rotateZ, float scale) {
    return new TRSRTransformation(new Vector3f(translateX / 16f, translateY / 16f, translateZ / 16f),
        TRSRTransformation.quatFromXYZDegrees(new Vector3f(rotateX, rotateY, rotateZ)), new Vector3f(scale, scale, scale), null);
  }

  public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> itemTransforms = new HashMap<>();
  public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> blockTransforms = new HashMap<>();
  public static Map<ItemCameraTransforms.TransformType, TRSRTransformation> handheldTransforms = new HashMap<>();

  public static TRSRTransformation ITEM_GROUND = create(0, 2, 0, 0, 0, 0, 0.5f);
  public static TRSRTransformation ITEM_HEAD = create(0, 13, 7, 0, 180, 0, 1);
  public static TRSRTransformation ITEM_FIXED = create(0, 0, 0, 0, 180, 0, 1f);
  public static TRSRTransformation ITEM_THIRD_PERSON_RIGHT = create(0, 3, 1, 0, 0, 0, 0.55f);
  public static TRSRTransformation ITEM_THIRD_PERSON_LEFT = create(0, 3, 1, 0, 0, 0, 0.55f);
  public static TRSRTransformation ITEM_FIRST_PERSON_RIGHT = create(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f);
  public static TRSRTransformation ITEM_FIRST_PERSON_LEFT = create(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f);

  public static TRSRTransformation HANDHELD_THIRD_PERSON_RIGHT = create(0, 4, 0.5f, 0, -90, 55, 0.85f);
  public static TRSRTransformation HANDHELD_THIRD_PERSON_LEFT = create(0, 4, 0.5f, 0, 90, -55, 0.85f);

  public static TRSRTransformation BLOCK_GUI = create(0, 0, 0, 30, 225, 0, 0.625f);
  public static TRSRTransformation BLOCK_GROUND = create(0, 3, 0, 0, 0, 0, 0.25f);
  public static TRSRTransformation BLOCK_FIXED = create(0, 0, 0, 0, 0, 0, 0.5f);
  public static TRSRTransformation BLOCK_HEAD = create(0, 13, 7, 0, 180, 0, 1);
  public static TRSRTransformation BLOCK_THIRD_PERSON_RIGHT = create(0, 2.5f, 0, 75, 45, 0, 0.375f);
  public static TRSRTransformation BLOCK_THIRD_PERSON_LEFT = create(0, 2.5f, 0, 75, 45, 0, 0.375f);
  public static TRSRTransformation BLOCK_FIRST_PERSON_RIGHT = create(0, 0, 0, 0, 45, 0, 0.4f);
  public static TRSRTransformation BLOCK_FIRST_PERSON_LEFT = create(0, 0, 0, 0, 225, 0, 0.4f);

  static {
    itemTransforms.put(ItemCameraTransforms.TransformType.FIXED, ITEM_FIXED);
    itemTransforms.put(ItemCameraTransforms.TransformType.GROUND, ITEM_GROUND);
    itemTransforms.put(ItemCameraTransforms.TransformType.HEAD, ITEM_HEAD);
    itemTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, ITEM_THIRD_PERSON_RIGHT);
    itemTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, ITEM_THIRD_PERSON_LEFT);
    itemTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, ITEM_FIRST_PERSON_RIGHT);
    itemTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, ITEM_FIRST_PERSON_LEFT);

    handheldTransforms.put(ItemCameraTransforms.TransformType.GROUND, ITEM_GROUND);
    handheldTransforms.put(ItemCameraTransforms.TransformType.HEAD, ITEM_HEAD);
    handheldTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HANDHELD_THIRD_PERSON_RIGHT);
    handheldTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HANDHELD_THIRD_PERSON_LEFT);
    handheldTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, ITEM_FIRST_PERSON_RIGHT);
    handheldTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, ITEM_FIRST_PERSON_LEFT);

    blockTransforms.put(ItemCameraTransforms.TransformType.GUI, BLOCK_GUI);
    blockTransforms.put(ItemCameraTransforms.TransformType.GROUND, BLOCK_GROUND);
    blockTransforms.put(ItemCameraTransforms.TransformType.FIXED, BLOCK_FIXED);
    blockTransforms.put(ItemCameraTransforms.TransformType.HEAD, BLOCK_HEAD);
    blockTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, BLOCK_THIRD_PERSON_RIGHT);
    blockTransforms.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, BLOCK_THIRD_PERSON_LEFT);
    blockTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, BLOCK_FIRST_PERSON_RIGHT);
    blockTransforms.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, BLOCK_FIRST_PERSON_LEFT);
  }

}
