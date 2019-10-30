### Setup

DeferredRegister is a way of creating a registry which returns suppliers, allowing for static initialisation of items, blocks, etc, without actually creating the necessary item, block, etc, objects, until they need to be registered.

To use, create an instance of ModRegistry statically within your mod class. Pass it your MODID.

Then, in your ModBlocks, ModItems, etc, simply create static `RegistryObject<T>` for your necessary objects, and initialise them by calling `MYMOD.REGISTRY.registerItem` with the first parameter being the string registry name of the item, and the second being a supplier of the item.

Likewise `MYMOD.REGISTRY.registerBlock` (which automatically attempts to create an itemblock; use `MYMOD.REGISTRY.registerBlockWithoutItem` if you wish to skip this step), which additionally accepts a `Supplier<Item.Properties>` for properties to be applied to the registered block item.

While it may seem overkill, all properties are set up to accept suppliers of properties, rather than property instances themselves. These can be in-lined where necessary.

### Suppliers

ModRegistry also provides a number of supplier functions which can be passed into the `registerXXX` call:

#### Block

`ModRegistry::block` -> accepts `T extends Block` constructor that accepts Block.Properties, and a `Supplier<Block.Properties>` supplier containing the block properties. eg:

```java
public static RegistryObject<ThatchBlock> THATCH = MysticalWorld.REGISTRY.registerBlock("thatch", MysticalWorld.REGISTRY.block(ThatchBlock::new, () -> Block.Properties.create(Material.WOOD).sound(SoundType.PLANT)), ModRegistries.SIG);
```

Create a `ThatchBlock` descendent of `Block`, with the Material of `WOOD` and the SoundType of `PLANT`, as well as passing in a reference to `ModRegistries.SIG` which is "Standard Item Group" properties. This is simply a lambda:

```java
public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(MysticalWorld.ITEM_GROUP);
```

Second example:

```java
 public static RegistryObject<AubergineCropBlock> AUBERGINE_CROP = MysticalWorld.REGISTRY.registerBlockWithoutItem("aubergine_crop", MysticalWorld.REGISTRY.block(AubergineCropBlock::new, () -> Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0f).sound(SoundType.CROP)));
```

This creates but does not register an `AubergineCropBlock` with the relevant properties.

#### Item

`ModRegistry::item` -> accepts `T extends Item` and functions in an almost identical fashion to `block`:

```java
public static RegistryObject<Item> PELT = MysticalWorld.REGISTRY.registerItem("pelt", MysticalWorld.REGISTRY.item(Item::new, ModRegistries.SIG));
```

Here the standard item group properties are used, and the pelt itself is simply an item with no other special properties, requiring no additional classes.

#### Additional suppliers

Should be pretty obvious.
