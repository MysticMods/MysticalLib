package epicsquid.mysticallib.item;

import epicsquid.mysticallib.util.ItemUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
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
    ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
    ItemStack result = super.onItemUseFinish(stack, world, entity);
    if (result.isEmpty()) {
      return bottle;
    } else if (entity instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) entity;
      if (!player.addItemStackToInventory(bottle)) {
        ItemUtil.spawnItem(world, player.getPosition(), bottle);
      }
    }
    return result;
  }
}
