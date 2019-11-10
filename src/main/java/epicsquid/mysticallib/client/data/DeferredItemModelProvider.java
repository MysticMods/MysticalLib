package epicsquid.mysticallib.client.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Supplier;

public abstract class DeferredItemModelProvider extends ItemModelProvider {
    private final String name;

    public DeferredItemModelProvider(String name, DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    protected String name(Supplier<? extends IItemProvider> item) {
        return item.get().asItem().getRegistryName().getPath();
    }

    protected ResourceLocation itemTexture(Supplier<? extends IItemProvider> item) {
        return modLoc("item/" + name(item));
    }

    protected ItemModelBuilder blockItem(Supplier<? extends Block> block) {
        return blockItem(block, "");
    }

    protected ItemModelBuilder blockItem(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(name(block), modLoc("block/" + name(block) + suffix));
    }

    protected ItemModelBuilder blockWithInventoryModel (Supplier<? extends Block> block) {
        return withExistingParent(name(block), modLoc("block/" + name(block) + "_inventory"));
    }

    protected ItemModelBuilder generated(Supplier<? extends IItemProvider> item) {
        return generated(item, itemTexture(item));
    }

    protected ItemModelBuilder generated(Supplier<? extends IItemProvider> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    protected ItemModelBuilder handheld(Supplier<? extends IItemProvider> item) {
        return handheld(item, itemTexture(item));
    }

    protected ItemModelBuilder handheld(Supplier<? extends IItemProvider> item, ResourceLocation texture) {
        return withExistingParent(name(item), "item/handheld").texture("layer0", texture);
    }

    protected ItemModelBuilder spawnEgg(Supplier<? extends IItemProvider> item) {
        return withExistingParent(name(item), "item/template_spawn_egg");
    }
}
