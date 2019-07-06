package epicsquid.mysticallib.block;

import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

public class CustomStateMapper implements IStateMapper {
  protected Map<BlockState, ModelResourceLocation> mapStateModelLocations = Maps.<BlockState, ModelResourceLocation>newLinkedHashMap();

  @Override
  @Nonnull
  public Map<BlockState, ModelResourceLocation> putStateModelLocations(@Nonnull Block block) {
    UnmodifiableIterator unmodifiableiterator = block.getBlockState().getValidStates().iterator();

    while (unmodifiableiterator.hasNext()) {
      BlockState BlockState = (BlockState) unmodifiableiterator.next();
      this.mapStateModelLocations.put(BlockState, new ModelResourceLocation(BlockState.getBlock().getRegistryName(), "custom"));
    }

    return this.mapStateModelLocations;
  }

}
