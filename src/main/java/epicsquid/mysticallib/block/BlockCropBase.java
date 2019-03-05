package epicsquid.mysticallib.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import tehnut.harvest.IReplantHandler;

public class BlockCropBase extends BlockCrops implements IBlock, IModeledObject, IReplantHandler {

  private final @Nonnull EnumPlantType plantType;

  /**
   * Used for arbitrary crops
   * @param name Name of the crop
   */
  public BlockCropBase(@Nonnull String name, @Nonnull EnumPlantType plantType) {
    super();
    setUnlocalizedName(name);
    setRegistryName(LibRegistry.getActiveModid(), name);

    this.plantType = plantType;
  }

  /**
   * Controls the type of plant
   */
  @Override
  @Nonnull
  public EnumPlantType getPlantType(@Nullable IBlockAccess world, @Nullable BlockPos pos) {
    return plantType;
  }

  /**
   * Scales the bounding box of the crop with it's age
   */
  @Override
  @Nonnull
  public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos){
    return new AxisAlignedBB(0,0,0,1,0.125f*(state.getValue(this.AGE)+1),1);
  }

  @Override
  public void initModel(){
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString(),"inventory"));
  }

  /**
   * Return null since we don't want an item for this block
   */
  @Override
  public Item getItemBlock() {
    return null;
  }

  @Override
  public void handlePlant(World world, BlockPos pos, IBlockState state, EntityPlayer player, @Nullable TileEntity tileEntity) {
    IBlockState newState = getDefaultState();
    NonNullList<ItemStack> drops = NonNullList.create();
    getDrops(drops, world, pos, state, 0);
    ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 1.0f, false, player);
    for (ItemStack stack : drops) {
      if (stack.getItem() == getSeed()) {
        stack.shrink(1);
        break;
      }
    }

    if (!world.isRemote) {
      world.setBlockState(pos, newState);
      for (ItemStack drop : drops) {
        EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
        entityItem.setPickupDelay(10);
        world.spawnEntity(entityItem);
      }
    }
  }
}
