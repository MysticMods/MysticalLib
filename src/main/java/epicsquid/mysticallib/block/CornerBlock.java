package epicsquid.mysticallib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CornerBlock extends Block {
  public static Map<Integer, List<VoxelShape>> boxes = new HashMap<>();

  public static final BooleanProperty UP = BooleanProperty.create("up");
  public static final IntegerProperty DIR = IntegerProperty.create("dir", 0, 3); //NXNZ -> PXNZ -> PXPZ -> NXPZ corner
  public static final BooleanProperty INNER = BooleanProperty.create("inner");

  public boolean inner = false;
//  protected @Nullable BlockState parent = null;


  public CornerBlock(Properties props) {
    super(props);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    super.fillStateContainer(builder);
    builder.add(UP).add(DIR).add(INNER);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    double hitX = context.getHitVec().x;
    double hitY = context.getHitVec().y;
    double hitZ = context.getHitVec().z;

    boolean up = (hitY > 0.5);
    if (hitY == 1) {
      up = false;
    }
    if (hitY == 0) {
      up = true;
    }
    int dir = 0;
    if (hitX > 0 && hitX < 1 && hitZ > 0 && hitZ < 1) {
      if (hitX < 0.5 && hitZ < 0.5) {
        dir = 0;
      }
      if (hitX >= 0.5 && hitZ < 0.5) {
        dir = 1;
      }
      if (hitX >= 0.5 && hitZ >= 0.5) {
        dir = 2;
      }
      if (hitX < 0.5 && hitZ >= 0.5) {
        dir = 3;
      }
    }
    if (hitX == 0) {
      if (hitZ < 0.5) {
        dir = 1;
      }
      if (hitZ >= 0.5) {
        dir = 2;
      }
    }
    if (hitZ == 0) {
      if (hitX < 0.5) {
        dir = 3;
      }
      if (hitX >= 0.5) {
        dir = 2;
      }
    }
    if (hitX == 1) {
      if (hitZ < 0.5) {
        dir = 0;
      }
      if (hitZ >= 0.5) {
        dir = 3;
      }
    }
    if (hitZ == 1) {
      if (hitX < 0.5) {
        dir = 0;
      }
      if (hitX > 0.5) {
        dir = 1;
      }
    }
    return getDefaultState().with(UP, up).with(DIR, dir);
  }

//
//  @Override
//  public void addCollisionBoxToList(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox,
//      @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean advanced) {
//    float box_precision = SlantBlock.box_precision;
//    List<AxisAlignedBB> temp = new ArrayList<>();
//    boolean up = state.getValue(UP);
//    int dir = state.getValue(DIR);
//    if (inner) {
//      if (!up) {
//        switch (dir) {
//        case 0:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, i, 1.0, i + box_precision, 1.0));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(i, i, 0, 1.0, i + box_precision, 1.0));
//          }
//          break;
//        case 1:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, i, 1.0, i + box_precision, 1.0));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 0, 1.0 - i, i + box_precision, 1.0));
//          }
//          break;
//        case 2:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 0, 1.0, i + box_precision, 1.0 - i));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 0, 1.0 - i, i + box_precision, 1.0));
//          }
//          break;
//        case 3:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 0, 1.0, i + box_precision, 1.0 - i));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(i, i, 0, 1.0, i + box_precision, 1.0));
//          }
//          break;
//        }
//      } else {
//        switch (dir) {
//        case 0:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, i, 1.0, (1.0 - box_precision) - i, 1.0));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(i, 1.0 - i, 0, 1.0, (1.0 - box_precision) - i, 1.0));
//          }
//          break;
//        case 1:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, i, 1.0, (1.0 - box_precision) - i, 1.0));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 0, 1.0 - i, (1.0 - box_precision) - i, 1.0));
//          }
//          break;
//        case 2:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 0, 1.0, (1.0 - box_precision) - i, 1.0 - i));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 0, 1.0 - i, (1.0 - box_precision) - i, 1.0));
//          }
//          break;
//        case 3:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 0, 1.0, (1.0 - box_precision) - i, 1.0 - i));
//          }
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(i, 1.0 - i, 0, 1.0, (1.0 - box_precision) - i, 1.0));
//          }
//          break;
//        }
//      }
//    } else {
//      if (!up) {
//        switch (dir) {
//        case 0:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 0, 1.0 - i, i + box_precision, 1.0 - i));
//          }
//          break;
//        case 1:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(1.0, i, 0, i, i + box_precision, 1.0 - i));
//          }
//          break;
//        case 2:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(1.0, i, 1.0, i, i + box_precision, i));
//          }
//          break;
//        case 3:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, i, 1.0, 1.0 - i, i + box_precision, i));
//          }
//          break;
//        }
//      } else {
//        switch (dir) {
//        case 0:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 0, 1.0 - i, (1.0 - i) - box_precision, 1.0 - i));
//          }
//          break;
//        case 1:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(1.0, 1.0 - i, 0, i, (1.0 - i) - box_precision, 1.0 - i));
//          }
//          break;
//        case 2:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(1.0, 1.0 - i, 1.0, i, (1.0 - i) - box_precision, i));
//          }
//          break;
//        case 3:
//          for (float i = 0; i < 1; i += box_precision) {
//            temp.add(new AxisAlignedBB(0, 1.0 - i, 1.0, 1.0 - i, (1.0 - i) - box_precision, i));
//          }
//          break;
//        }
//      }
//    }
//    for (AxisAlignedBB b : temp) {
//      addCollisionBoxToList(pos, entityBox, collidingBoxes, b);
//    }
//  }
//
//

  @Override
  public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
    return false;
  }
//
//  // TODO: Look for a better way of doing this
//  @Override
//  @OnlyIn(Dist.CLIENT)
//  public void initCustomModel() {
//    if (hasCustomModel()) {
//      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getNamespace() + ":blocks/" + getRegistryName().getPath());
//      if (parent != null) {
//        defaultTex = new ResourceLocation(parent.getBlock().getRegistryName().getNamespace() + ":blocks/" + parent.getBlock().getRegistryName().getPath());
//      }
//      if (inner) {
//        CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getNamespace() + ":models/block/" + name),
//            new CustomModelBlock(getModelClass(0), defaultTex, defaultTex));
//        CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getNamespace() + ":" + name + "#handlers"),
//            new CustomModelBlock(getModelClass(0), defaultTex, defaultTex));
//      } else {
//        CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getNamespace() + ":models/block/" + name),
//            new CustomModelBlock(getModelClass(1), defaultTex, defaultTex));
//        CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getNamespace() + ":" + name + "#handlers"),
//            new CustomModelBlock(getModelClass(1), defaultTex, defaultTex));
//      }
//    }
//  }
//
//  @Nonnull
//  @Override
//  protected Class<? extends BakedModelBlock> getModelClass(int type) {
//    if (type == 1) {
//      return BakedModelOuterCorner.class;
//    } else {
//      return BakedModelInnerCorner.class;
//    }
//  }
}
