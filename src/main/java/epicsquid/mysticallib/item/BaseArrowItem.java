package epicsquid.mysticallib.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BaseArrowItem extends ArrowItem {

	private final double damage;

	public BaseArrowItem(Properties props, double damage) {
		super(props);
		this.damage = damage;
	}

	@Override
	@Nonnull
	public ArrowEntity createArrow(@Nonnull World worldIn, @Nonnull ItemStack stack, LivingEntity shooter) {
		ArrowEntity entity = new ArrowEntity(worldIn, shooter);
		entity.setPotionEffect(stack);
		entity.setDamage(damage);
		return entity;
	}
}
