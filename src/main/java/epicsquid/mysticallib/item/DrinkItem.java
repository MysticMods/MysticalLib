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
public class DrinkItem extends MultiReturnItem {
  public DrinkItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.DRINK;
  }

  @Override
  protected Item getReturnItem(ItemStack stack) {
    return Items.GLASS_BOTTLE;
  }
}
