package epicsquid.mysticallib.model.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class BakedModelBlockUnlitWrapper implements IBakedModel {

	private IBakedModel internal;
	private TreeMap<Integer, List<BakedQuad>> quads = new TreeMap<>();

	public BakedModelBlockUnlitWrapper(@Nonnull IBakedModel model) {
		this.internal = model;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		if (side == null) {
			int stateid = Block.getStateId(state);
			if (!quads.containsKey(stateid)) {
				List<BakedQuad> list = new ArrayList<>();
				for (Direction f : Direction.values()) {
					list.addAll(internal.getQuads(state, f, rand));
				}
				list.addAll(internal.getQuads(state, null, rand));
				for (int i = 0; i < list.size(); i++) {
					// FIXME HAX
//					try {
//						Hax.bakedQuadFace.set(list.get(i), null);
//					} catch (IllegalArgumentException | IllegalAccessException e) {
//						e.printStackTrace();
//					}
				}
				quads.put(stateid, list);
			}
			return quads.get(stateid);
		}
		return new ArrayList<>();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return internal.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return internal.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return internal.isBuiltInRenderer();
	}

	@Override
	@Nonnull
	public TextureAtlasSprite getParticleTexture() {
		return internal.getParticleTexture();
	}

	@Override
	@Nonnull
	public ItemOverrideList getOverrides() {
		return internal.getOverrides();
	}

	@Override
	@Nonnull
	public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType type) {
		return internal.handlePerspective(type);
	}

}
