package epicsquid.mysticallib.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GenerationNode {
	private String structure = "";
	private Rotation rotation = Rotation.NONE;
	private Mirror mirror = Mirror.NONE;
	public BlockPos pos = new BlockPos(-1, -1, -1);
	private boolean replaceWithAir = false;
	boolean isAlive = true;
	boolean force = false;

	public GenerationNode(@Nonnull CompoundNBT tag) {
		read(tag);
	}

	public GenerationNode(@Nonnull BlockPos pos, @Nonnull String structure, @Nonnull Rotation rotation, @Nonnull Mirror mirror, boolean replaceWithAir,
												boolean force) {
		this.pos = pos;
		this.structure = structure;
		this.rotation = rotation;
		this.mirror = mirror;
		this.replaceWithAir = replaceWithAir;
		this.force = force;
	}

	public void update(@Nonnull World world) {
		if (structure.length() > 0) {
			IGeneratable data = StructureRegistry.structures.get(structure);
			if (data != null) {
				data.calcDimensions();
				if (world.isAreaLoaded(new BlockPos(pos.getX() - 8, pos.getY(), pos.getZ() - 8),
								new BlockPos(pos.getX() + data.getWidth() + 8, pos.getY(), pos.getZ() + data.getLength() + 8))) {
					data.generateIn(world, pos.getX(), pos.getY(), pos.getZ(), rotation, mirror, replaceWithAir, force);
					isAlive = false;
				}
			} else {
				isAlive = false;
			}
		}
	}

	protected void read(@Nonnull CompoundNBT compound) {
		structure = compound.getString("structure");
		rotation = Rotation.values()[compound.getInt("rotation")];
		mirror = Mirror.values()[compound.getInt("mirror")];
		replaceWithAir = compound.getBoolean("replaceWithAir");
		pos = NBTUtil.readBlockPos(compound.getCompound("pos"));
	}

	@Nonnull
	public CompoundNBT write() {
		CompoundNBT compound = new CompoundNBT();
		compound.put("pos", NBTUtil.writeBlockPos(pos));
		compound.putString("structure", structure);
		compound.putInt("rotation", rotation.ordinal());
		compound.putInt("mirror", mirror.ordinal());
		compound.putBoolean("replaceWithAir", replaceWithAir);
		return compound;
	}
}
