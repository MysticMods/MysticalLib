package epicsquid.mysticallib;

import epicsquid.mysticallib.block.BaseOreBlock;
import epicsquid.mysticallib.item.KnifeItem;
import epicsquid.mysticallib.material.IMaterial;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MysticalLib.MODID)
public class LibRegistry {

	private static final String SWORD = "SWORD";
	private static final String KNIFE = "KNIFE";
	private static final String PICKAXE = "PICKAXE";
	private static final String AXE = "AXE";
	private static final String SHOVEL = "SHOVEL";
	private static final String HOE = "HOE";
	private static final String SPEAR = "SPEAR";

	private static ArrayList<SoundEvent> sounds = new ArrayList<>();

	@SubscribeEvent
	public void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {
		for (SoundEvent s : sounds) {
			event.getRegistry().register(s);
		}
	}

	public static void registerMetalSetItems(IMaterial mat, IForgeRegistry<Item> registry, String modid) {
		registry.register(new Item(mat.getItemProps()).setRegistryName(modid, mat.getName() + "_ingot"));
		registry.register(new Item(mat.getItemProps()).setRegistryName(modid, mat.getName() + "_nugget"));
		registry.register(new SwordItem(mat.getTier(), (int) mat.getAttackDamage(SWORD), mat.getAttackSpeed(SWORD), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_sword"));
		registry.register(new PickaxeItem(mat.getTier(), (int) mat.getAttackDamage(PICKAXE), mat.getAttackSpeed(PICKAXE), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_pickaxe"));
		registry.register(new AxeItem(mat.getTier(), mat.getAttackDamage(AXE), mat.getAttackSpeed(AXE), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_axe"));
		registry.register(new ShovelItem(mat.getTier(), mat.getAttackDamage(SHOVEL), mat.getAttackSpeed(SHOVEL), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_shovel"));
		registry.register(new KnifeItem(mat.getTier(), mat.getAttackDamage(KNIFE), mat.getAttackSpeed(KNIFE), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_knife"));
		registry.register(new HoeItem(mat.getTier(), mat.getAttackSpeed(HOE), mat.getItemProps()).setRegistryName(modid, mat.getName() + "_hoe"));
		registry.register(new ArmorItem(mat.getArmor(), EquipmentSlotType.HEAD, mat.getItemProps()).setRegistryName(modid, mat.getName() + "_helmet"));
		registry.register(new ArmorItem(mat.getArmor(), EquipmentSlotType.CHEST, mat.getItemProps()).setRegistryName(modid, mat.getName() + "_chestplate"));
		registry.register(new ArmorItem(mat.getArmor(), EquipmentSlotType.LEGS, mat.getItemProps()).setRegistryName(modid, mat.getName() + "_leggings"));
		registry.register(new ArmorItem(mat.getArmor(), EquipmentSlotType.FEET, mat.getItemProps()).setRegistryName(modid, mat.getName() + "_boots"));
	}

	public static List<Block> registerMetalSetBlocks(IMaterial mat, IForgeRegistry<Block> registry, String modid) {
		List<Block> result = new ArrayList<>();
		result.add(new Block(mat.getBlockProps()).setRegistryName(modid, mat.getName() + "_block"));
		if (mat.hasOre()) {
			result.add(new BaseOreBlock(mat.getBlockOreProps()).setRegistryName(modid, mat.getName() + "_ore"));
		}
		return result;
	}
}
