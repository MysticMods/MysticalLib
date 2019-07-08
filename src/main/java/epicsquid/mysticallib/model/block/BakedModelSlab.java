package epicsquid.mysticallib.model.block;

import epicsquid.mysticallib.model.CustomModelBase;
import epicsquid.mysticallib.model.ModelUtil;
import epicsquid.mysticallib.model.parts.Cube;
import epicsquid.mysticallib.struct.Vec4f;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class BakedModelSlab extends BakedModelBlock {
	private Cube cube_down, cube_up;

	public static Vec4f FULL_FACE_UV = new Vec4f(0, 0, 16, 16);
	public static Vec4f BOTTOM_SIDE_UV = new Vec4f(0, 8, 16, 8);
	public static Vec4f TOP_SIDE_UV = new Vec4f(0, 0, 16, 8);
	public static Vec4f[] bottomUV = new Vec4f[]{BOTTOM_SIDE_UV, BOTTOM_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, BOTTOM_SIDE_UV, BOTTOM_SIDE_UV};
	public static Vec4f[] topUV = new Vec4f[]{TOP_SIDE_UV, TOP_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, TOP_SIDE_UV, TOP_SIDE_UV};

	public BakedModelSlab(@Nonnull IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
												@Nonnull CustomModelBase model) {
		super(format, bakedTextureGetter, model);
		TextureAtlasSprite[] texes = new TextureAtlasSprite[]{texwest, texeast, texdown, texup, texnorth, texsouth};
		cube_down = ModelUtil.makeCube(format, 0, 0, 0, 1, 0.5, 1, null, texes, 0).setNoCull(Direction.UP);
		cube_up = ModelUtil.makeCube(format, 0, 0.5, 0, 1, 0.5, 1, null, texes, 0).setNoCull(Direction.DOWN);
	}

	@Override
	@Nonnull
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		List<BakedQuad> quads = new ArrayList<>();
		getFaceQuads(quads, side, state);
		return quads;
	}

	private void getFaceQuads(@Nonnull List<BakedQuad> quads, @Nullable Direction side, @Nullable BlockState state) {
		if (state == null) {
			cube_down.addToList(quads, side);
		} else {
			SlabType half = state.get(SlabBlock.TYPE);
			if (half == SlabType.BOTTOM) {
				cube_down.addToList(quads, side);
			} else if (half == SlabType.TOP) {
				cube_up.addToList(quads, side);
			}
		}
	}

}
