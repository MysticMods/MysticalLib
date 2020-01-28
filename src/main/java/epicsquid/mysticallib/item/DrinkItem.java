package epicsquid.mysticallib.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class DrinkItem extends Item {
  public DrinkItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.DRINK;
  }

  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
    super.onItemUseFinish(stack, world, entity);
    return new ItemStack(Items.GLASS_BOTTLE);
  }
}
