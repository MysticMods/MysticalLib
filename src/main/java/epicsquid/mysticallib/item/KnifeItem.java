package epicsquid.mysticallib.item;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ToolItem;

import java.util.Set;

public class KnifeItem extends ToolItem {
	public static Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet(Blocks.ACACIA_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.JUNGLE_LOG, Blocks.OAK_LOG, Blocks.SPRUCE_LOG);

	// TODO rework knives to strip logs of bark with right click, or drop bark by mining it
	public KnifeItem(IItemTier tier, float attackDamage, float attackSpeed, Properties props) {
		super(attackDamage, attackSpeed, tier, EFFECTIVE_BLOCKS, props);
	}
}
