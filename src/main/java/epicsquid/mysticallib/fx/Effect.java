package epicsquid.mysticallib.fx;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.CompoundNBT;
import org.lwjgl.opengl.GL11;

public class Effect {
	public int lifetime = 0;
	public int maxLife = 0;
	public float r = 0;
	public float g = 0;
	public float b = 0;
	public float a = 0;
	public boolean inited = false;
	public double x = 0;
	public double y = 0;
	public double z = 0;
	public double px = 0;
	public double py = 0;
	public double pz = 0;
	public double vx = 0;
	public double vy = 0;
	public double vz = 0;
	public boolean additive = false;
	public boolean dead = false;
	public int dimId = 0;

	public Effect() {
		//
	}

	public Effect(int id) {
		this.dimId = id;
	}

	public Effect setLife(int l) {
		this.maxLife = l;
		this.lifetime = l;
		return this;
	}

	public Effect setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	public Effect setPosition(double x, double y, double z) {
		this.px = x;
		this.py = y;
		this.pz = z;
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Effect setMotion(double vx, double vy, double vz) {
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		return this;
	}

	public Effect setAdditive(boolean additive) {
		this.additive = additive;
		return this;
	}

	public void update() {
		if (!inited) {
			inited = true;
		}
		px = x;
		py = y;
		pz = z;
		x += vx;
		y += vy;
		z += vz;

		lifetime--;
		if (lifetime < 0) {
			kill();
		}
	}

	public void kill() {
		dead = true;
	}

	public float getLifeCoeff(float pTicks) {
		return Math.max(0, ((float) lifetime - pTicks) / (float) maxLife);
	}

	public float getInterpX(float pticks) {
		return (float) x * (pticks) + (float) px * (1f - pticks);
	}

	public float getInterpY(float pticks) {
		return (float) y * (pticks) + (float) py * (1f - pticks);
	}

	public float getInterpZ(float pticks) {
		return (float) z * (pticks) + (float) pz * (1f - pticks);
	}

	public void renderTotal(float pticks) {
		if (inited) {
			GlStateManager.enableBlend();
			GlStateManager.enableAlphaTest();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, additive ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.depthMask(false);
			int dfunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
			GlStateManager.depthFunc(GL11.GL_LEQUAL);
			int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
			GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
			GlStateManager.disableCull();
			GlStateManager.shadeModel(GL11.GL_SMOOTH);

			GlStateManager.pushMatrix();
			GlStateManager
							.translated(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);

			render(pticks);

			GlStateManager.popMatrix();

			GlStateManager.alphaFunc(func, ref);
			GlStateManager.depthFunc(dfunc);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.depthMask(true);
			GlStateManager.disableBlend();
		}
	}

	public void read(CompoundNBT tag) {
		px = x;
		x = tag.getDouble("x");
		py = y;
		y = tag.getDouble("y");
		pz = z;
		z = tag.getDouble("z");
		vx = tag.getDouble("vx");
		vy = tag.getDouble("vy");
		vz = tag.getDouble("vz");
		r = tag.getFloat("r");
		g = tag.getFloat("g");
		b = tag.getFloat("b");
		a = tag.getFloat("a");
		maxLife = tag.getInt("maxlife");
		lifetime = tag.getInt("life");
		dimId = tag.getInt("dim");
		additive = tag.getBoolean("additive");
	}

	public CompoundNBT write() {
		CompoundNBT tag = new CompoundNBT();
		tag.putDouble("x", x);
		tag.putDouble("y", y);
		tag.putDouble("z", z);
		tag.putDouble("vx", vx);
		tag.putDouble("vy", vy);
		tag.putDouble("vz", vz);
		tag.putFloat("r", r);
		tag.putFloat("g", g);
		tag.putFloat("b", b);
		tag.putFloat("a", a);
		tag.putInt("maxlife", maxLife);
		tag.putInt("life", lifetime);
		tag.putInt("dim", dimId);
		tag.putBoolean("additive", additive);
		return tag;
	}

	public void render(float pticks) {

	}

}
