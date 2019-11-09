package epicsquid.mysticallib.data;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.LootTable;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class DeferredBlockLootTableProvider extends BlockLootTables {
  public void registerFlowerPot(Supplier<? extends Block> flowerPot) {
    super.registerFlowerPot(flowerPot.get());
  }

  public void registerSilkTouch(Supplier<? extends Block> blockIn, Supplier<? extends Block> silkTouchDrop) {
    super.registerSilkTouch(blockIn.get(), silkTouchDrop.get());
  }

  public void registerDropping(Supplier<? extends Block> blockIn, Supplier<? extends IItemProvider> drop) {
    super.registerDropping(blockIn.get(), drop.get());
  }

  public void registerSilkTouch(Supplier<? extends Block> blockIn) {
    super.registerSilkTouch(blockIn.get());
  }

  public void registerDropSelfLootTable(Supplier<? extends Block> p_218492_1_) {
    super.registerDropSelfLootTable(p_218492_1_.get());
  }

  public void self (Supplier<? extends Block> block) {
    registerDropSelfLootTable(block);
  }

  public void orePieces (Supplier<? extends Block> block, Supplier<? extends IItemProvider> item) {
    registerLootTable(block, o -> droppingItemWithFortune(o, item.get().asItem()));
  }

  public void slab (Supplier<? extends SlabBlock> block) {
    registerLootTable(block, BlockLootTables::droppingSlab);
  }

  protected void registerLootTable(Supplier<? extends Block> blockIn, Function<Block, LootTable.Builder> factory) {
    super.registerLootTable(blockIn.get(), factory);
  }

  protected void registerLootTable(Supplier<? extends Block> blockIn, LootTable.Builder table) {
    super.registerLootTable(blockIn.get(), table);
  }
}
