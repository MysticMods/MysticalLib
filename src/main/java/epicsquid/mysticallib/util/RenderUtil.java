package epicsquid.mysticallib.util;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.LibEvents;
import epicsquid.mysticallib.MysticalLib;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderUtil {

  public static ResourceLocation beam_texture = new ResourceLocation(MysticalLib.MODID + ":textures/effect/beam.png");
  public static ResourceLocation glow_texture = new ResourceLocation(MysticalLib.MODID + ":textures/effect/glow.png");
  public static int maxLightX = 0xF000F0;
  public static int maxLightY = 0xF000F0;

  public static void renderBeam(@Nonnull BufferBuilder buf, double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1, float b1,
      float a1, float r2, float g2, float b2, float a2, double width) {
    float yaw = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double tX1 = width * (double) MathHelper.cos(yaw);
    double tY1 = 0;
    double tZ1 = -width * (double) MathHelper.sin(yaw);

    double tX2 = width * (double) MathHelper.sin(yaw) * -(double) MathHelper.sin(pitch);
    double tY2 = width * (double) MathHelper.cos(pitch);
    double tZ2 = width * (double) MathHelper.cos(yaw) * -(double) MathHelper.sin(pitch);

    buf.pos((double) (x1 - tX1), (double) (y1 - tY1), (double) (z1 - tZ1)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tX1), (double) (y2 - tY1), (double) (z2 - tZ1)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tX1), (double) (y2 + tY1), (double) (z2 + tZ1)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tX1), (double) (y1 + tY1), (double) (z1 + tZ1)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();

    buf.pos((double) (x1 - tX2), (double) (y1 - tY2), (double) (z1 - tZ2)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tX2), (double) (y2 - tY2), (double) (z2 - tZ2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tX2), (double) (y2 + tY2), (double) (z2 + tZ2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tX2), (double) (y1 + tY2), (double) (z1 + tZ2)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
  }

  public static void renderBeam(@Nonnull BufferBuilder buf, double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1, float b1,
      float a1, float r2, float g2, float b2, float a2, double width1, double width2, double angle) {
    float rads = (float) Math.toRadians(angle);
    double ac = MathHelper.cos(rads);
    double as = MathHelper.sin(rads);
    float yaw = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double tX1 = (double) MathHelper.cos(yaw);
    double tY1 = 0;
    double tZ1 = -(double) MathHelper.sin(yaw);

    double tX2 = (double) MathHelper.sin(yaw) * -(double) MathHelper.sin(pitch);
    double tY2 = (double) MathHelper.cos(pitch);
    double tZ2 = (double) MathHelper.cos(yaw) * -(double) MathHelper.sin(pitch);

    double tXc1 = width1 * (tX1 * ac + tX2 * as);
    double tYc1 = width1 * (tY1 * ac + tY2 * as);
    double tZc1 = width1 * (tZ1 * ac + tZ2 * as);
    double tXs1 = width1 * (tX1 * -as + tX2 * ac);
    double tYs1 = width1 * (tY1 * -as + tY2 * ac);
    double tZs1 = width1 * (tZ1 * -as + tZ2 * ac);
    double tXc2 = width2 * (tX1 * ac + tX2 * as);
    double tYc2 = width2 * (tY1 * ac + tY2 * as);
    double tZc2 = width2 * (tZ1 * ac + tZ2 * as);
    double tXs2 = width2 * (tX1 * -as + tX2 * ac);
    double tYs2 = width2 * (tY1 * -as + tY2 * ac);
    double tZs2 = width2 * (tZ1 * -as + tZ2 * ac);

    buf.pos((double) (x1 - tXs1), (double) (y1 - tYs1), (double) (z1 - tZs1)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tXs2), (double) (y2 - tYs2), (double) (z2 - tZs2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tXs2), (double) (y2 + tYs2), (double) (z2 + tZs2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tXs1), (double) (y1 + tYs1), (double) (z1 + tZs1)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();

    buf.pos((double) (x1 - tXc1), (double) (y1 - tYc1), (double) (z1 - tZc1)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tXc2), (double) (y2 - tYc2), (double) (z2 - tZc2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tXc2), (double) (y2 + tYc2), (double) (z2 + tZc2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tXc1), (double) (y1 + tYc1), (double) (z1 + tZc1)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
  }

  public static void renderBeam(@Nonnull BufferBuilder buf, double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1, float b1,
      float a1, float r2, float g2, float b2, float a2, double width, double angle) {
    float rads = (float) Math.toRadians(angle);
    double ac = MathHelper.cos(rads);
    double as = MathHelper.sin(rads);
    float yaw = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double tX1 = width * (double) MathHelper.cos(yaw);
    double tY1 = 0;
    double tZ1 = -width * (double) MathHelper.sin(yaw);

    double tX2 = width * (double) MathHelper.sin(yaw) * -(double) MathHelper.sin(pitch);
    double tY2 = width * (double) MathHelper.cos(pitch);
    double tZ2 = width * (double) MathHelper.cos(yaw) * -(double) MathHelper.sin(pitch);

    double tXc = tX1 * ac + tX2 * as;
    double tYc = tY1 * ac + tY2 * as;
    double tZc = tZ1 * ac + tZ2 * as;
    double tXs = tX1 * -as + tX2 * ac;
    double tYs = tY1 * -as + tY2 * ac;
    double tZs = tZ1 * -as + tZ2 * ac;

    buf.pos((double) (x1 - tXs), (double) (y1 - tYs), (double) (z1 - tZs)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tXs), (double) (y2 - tYs), (double) (z2 - tZs)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tXs), (double) (y2 + tYs), (double) (z2 + tZs)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tXs), (double) (y1 + tYs), (double) (z1 + tZs)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();

    buf.pos((double) (x1 - tXc), (double) (y1 - tYc), (double) (z1 - tZc)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tXc), (double) (y2 - tYc), (double) (z2 - tZc)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tXc), (double) (y2 + tYc), (double) (z2 + tZc)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tXc), (double) (y1 + tYc), (double) (z1 + tZc)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
  }

  public static void renderBeamGui(@Nonnull BufferBuilder buf, double x1, double y1, double x2, double y2, float r1, float g1, float b1, float a1, float r2,
      float g2, float b2, float a2, double width) {
    float yaw = (float) Math.atan2(y2 - y1, x2 - x1);

    double tX1 = -width * (double) MathHelper.sin(yaw);
    double tY1 = width * (double) MathHelper.cos(yaw);

    buf.pos((double) (x1 - tX1), (double) (y1 - tY1), (double) (0)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x2 - tX1), (double) (y2 - tY1), (double) (0)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x2 + tX1), (double) (y2 + tY1), (double) (0)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    buf.pos((double) (x1 + tX1), (double) (y1 + tY1), (double) (0)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
  }

  public static void renderBeamSeriesGui(@Nonnull BufferBuilder buf, double x1, double y1, double x2, double y2, float r1, float g1, float b1, float a1,
      double width, boolean horiz) {
    float yaw2 = (float) Math.atan2(y2 - y1, x2 - x1);

    double tX1 = width * (double) MathHelper.cos(yaw2);
    double tY1 = -width * (double) MathHelper.sin(yaw2);

    buf.pos((double) (x1 - tX1), (double) (y1 - tY1), (double) (0)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    buf.pos((double) (x1 + tX1), (double) (y1 + tY1), (double) (0)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
        .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
  }

  public static void renderBeamSeries(@Nonnull BufferBuilder buf, double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1,
      float b1, float a1, double width, boolean horiz) {
    float yaw2 = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch2 = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double tX1 = width * (double) MathHelper.cos(yaw2);
    double tY1 = 0;
    double tZ1 = -width * (double) MathHelper.sin(yaw2);

    double tX2 = width * (double) MathHelper.sin(yaw2) * -(double) MathHelper.sin(pitch2);
    double tY2 = width * (double) MathHelper.cos(pitch2);
    double tZ2 = width * (double) MathHelper.cos(yaw2) * -(double) MathHelper.sin(pitch2);
    if (horiz) {
      buf.pos((double) (x1 - tX1), (double) (y1 - tY1), (double) (z1 - tZ1)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 + tX1), (double) (y1 + tY1), (double) (z1 + tZ1)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    } else {
      buf.pos((double) (x1 - tX2), (double) (y1 - tY2), (double) (z1 - tZ2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 + tX2), (double) (y1 + tY2), (double) (z1 + tZ2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    }
  }

  public static void renderBeamSeries(@Nonnull BufferBuilder buf, double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1,
      float b1, float a1, double width, boolean horiz, double angle) {
    float rads = (float) Math.toRadians(angle);
    double ac = MathHelper.cos(rads);
    double as = MathHelper.sin(rads);
    float yaw2 = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch2 = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double tX1 = width * (double) MathHelper.cos(yaw2);
    double tY1 = 0;
    double tZ1 = -width * (double) MathHelper.sin(yaw2);

    double tX2 = width * (double) MathHelper.sin(yaw2) * -(double) MathHelper.sin(pitch2);
    double tY2 = width * (double) MathHelper.cos(pitch2);
    double tZ2 = width * (double) MathHelper.cos(yaw2) * -(double) MathHelper.sin(pitch2);

    if (horiz) {
      double tXc = tX1 * ac + tX2 * as;
      double tYc = tY1 * ac + tY2 * as;
      double tZc = tZ1 * ac + tZ2 * as;
      buf.pos((double) (x1 - tXc), (double) (y1 - tYc), (double) (z1 - tZc)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 + tXc), (double) (y1 + tYc), (double) (z1 + tZc)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    } else {
      double tXs = tX1 * -as + tX2 * ac;
      double tYs = tY1 * -as + tY2 * ac;
      double tZs = tZ1 * -as + tZ2 * ac;
      buf.pos((double) (x1 - tXs), (double) (y1 - tYs), (double) (z1 - tZs)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 + tXs), (double) (y1 + tYs), (double) (z1 + tZs)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    }
  }

  public static void renderSlash(@Nonnull BufferBuilder buf, double x0, double y0, double z0, float r, float g, float b, float a, float radius, float width,
      float angleRange) {
    for (float i = -angleRange / 2.0f; i < angleRange / 2.0f; i += angleRange / 16.0f) {
      float coeff1 = 1.0f - Math.abs(i) / (angleRange / 2.0f);
      float coeff2 = 1.0f - Math.abs(i + angleRange / 16.0f) / (angleRange / 2.0f);
      double x1 = x0 + radius * Math.sin(Math.toRadians(i));
      double z1 = z0 + radius * Math.cos(Math.toRadians(i));
      double x2 = x0 + (radius + 0.5f * coeff1 * width) * Math.sin(Math.toRadians(i));
      double z2 = z0 + (radius + 0.5f * coeff1 * width) * Math.cos(Math.toRadians(i));
      double x3 = x0 + (radius + 0.5f * coeff2 * width) * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      double z3 = z0 + (radius + 0.5f * coeff2 * width) * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      double x4 = x0 + radius * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      double z4 = z0 + radius * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      buf.pos(x1, y0, z1 - radius / 2.0f).tex(1, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x2, y0, z2 - radius / 2.0f).tex(1, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x3, y0, z3 - radius / 2.0f).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x4, y0, z4 - radius / 2.0f).tex(0, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      x1 = x0 + radius * Math.sin(Math.toRadians(i));
      z1 = z0 + radius * Math.cos(Math.toRadians(i));
      x2 = x0 + (radius - 0.5f * coeff1 * width) * Math.sin(Math.toRadians(i));
      z2 = z0 + (radius - 0.5f * coeff1 * width) * Math.cos(Math.toRadians(i));
      x3 = x0 + (radius - 0.5f * coeff2 * width) * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      z3 = z0 + (radius - 0.5f * coeff2 * width) * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      x4 = x0 + radius * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      z4 = z0 + radius * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      buf.pos(x1, y0, z1 - radius / 2.0f).tex(1, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x2, y0, z2 - radius / 2.0f).tex(1, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x3, y0, z3 - radius / 2.0f).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x4, y0, z4 - radius / 2.0f).tex(0, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x1, y0, z1 - radius / 2.0f).tex(1, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x2, y0, z2 - radius / 2.0f).tex(1, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x3, y0, z3 - radius / 2.0f).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x4, y0, z4 - radius / 2.0f).tex(0, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      x1 = x0 + radius * Math.sin(Math.toRadians(i));
      z1 = z0 + radius * Math.cos(Math.toRadians(i));
      x2 = x0 + radius * Math.sin(Math.toRadians(i));
      z2 = z0 + radius * Math.cos(Math.toRadians(i));
      x3 = x0 + radius * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      z3 = z0 + radius * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      x4 = x0 + radius * Math.sin(Math.toRadians(i + angleRange / 16.0f));
      z4 = z0 + radius * Math.cos(Math.toRadians(i + angleRange / 16.0f));
      buf.pos(x1, y0, z1 - radius / 2.0f).tex(1, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x2, y0 - width * 0.5f * coeff1, z2 - radius / 2.0f).tex(1, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x3, y0 - width * 0.5f * coeff2, z3 - radius / 2.0f).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x4, y0, z4 - radius / 2.0f).tex(0, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x1, y0, z1 - radius / 2.0f).tex(1, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x2, y0 + width * 0.5f * coeff1, z2 - radius / 2.0f).tex(1, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff1).endVertex();
      buf.pos(x3, y0 + width * 0.5f * coeff2, z3 - radius / 2.0f).tex(0, 0).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
      buf.pos(x4, y0, z4 - radius / 2.0f).tex(0, 1).lightmap(maxLightX, maxLightY).color(r, g, b, a * coeff2).endVertex();
    }
  }

  public static void renderBeamSeries(@Nonnull BufferBuilder buf, double x0, double y0, double z0, double x1, double y1, double z1, double x2, double y2,
      double z2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
    float yaw1 = (float) Math.atan2(x1 - x0, z1 - z0);
    float pitch1 = (float) Math.atan2(y1 - y0, (double) MathHelper.sqrt(Math.pow(x1 - x0, 2) + Math.pow(z1 - z0, 2)));

    float yaw2 = (float) Math.atan2(x2 - x1, z2 - z1);
    float pitch2 = (float) Math.atan2(y2 - y1, (double) MathHelper.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2)));

    double iX1 = 0.2 * (double) MathHelper.cos(yaw1);
    double iY1 = 0;
    double iZ1 = -0.2 * (double) MathHelper.sin(yaw1);

    double iX2 = 0.2 * (double) MathHelper.sin(yaw1) * -(double) MathHelper.sin(pitch1);
    double iY2 = 0.2 * (double) MathHelper.cos(pitch1);
    double iZ2 = 0.2 * (double) MathHelper.cos(yaw1) * -(double) MathHelper.sin(pitch1);

    double tX1 = 0.2 * (double) MathHelper.cos(yaw2);
    double tY1 = 0;
    double tZ1 = -0.2 * (double) MathHelper.sin(yaw2);

    double tX2 = 0.2 * (double) MathHelper.sin(yaw2) * -(double) MathHelper.sin(pitch2);
    double tY2 = 0.2 * (double) MathHelper.cos(pitch2);
    double tZ2 = 0.2 * (double) MathHelper.cos(yaw2) * -(double) MathHelper.sin(pitch2);

    if (Math.signum(tX1) != Math.signum(iX1) || Math.signum(tY1) != Math.signum(iY1) || Math.signum(tZ1) != Math.signum(iZ1)) {
      buf.pos((double) (x2 - tX1), (double) (y2 - tY1), (double) (z2 - tZ1)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x1 + iX1), (double) (y1 + iY1), (double) (z1 + iZ1)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 - iX1), (double) (y1 - iY1), (double) (z1 - iZ1)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x2 + tX1), (double) (y2 + tY1), (double) (z2 + tZ1)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    } else {
      buf.pos((double) (x1 - iX1), (double) (y1 - iY1), (double) (z1 - iZ1)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x2 - tX1), (double) (y2 - tY1), (double) (z2 - tZ1)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x2 + tX1), (double) (y2 + tY1), (double) (z2 + tZ1)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x1 + iX1), (double) (y1 + iY1), (double) (z1 + iZ1)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    }
    if (Math.signum(tX2) != Math.signum(iX2) || Math.signum(tY2) != Math.signum(iY2) || Math.signum(tZ2) != Math.signum(iZ2)) {
      buf.pos((double) (x2 - tX2), (double) (y2 - tY2), (double) (z2 - tZ2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x1 + iX2), (double) (y1 + iY2), (double) (z1 + iZ2)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x1 - iX2), (double) (y1 - iY2), (double) (z1 - iZ2)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x2 + tX2), (double) (y2 + tY2), (double) (z2 + tZ2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
    } else {
      buf.pos((double) (x1 - iX2), (double) (y1 - iY2), (double) (z1 - iZ2)).tex(0.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
      buf.pos((double) (x2 - tX2), (double) (y2 - tY2), (double) (z2 - tZ2)).tex(1.0, 0.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x2 + tX2), (double) (y2 + tY2), (double) (z2 + tZ2)).tex(1.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r2, (float) g2, (float) b2, (float) a2).endVertex();
      buf.pos((double) (x1 + iX2), (double) (y1 + iY2), (double) (z1 + iZ2)).tex(0.0, 1.0).lightmap(maxLightX, maxLightY)
          .color((float) r1, (float) g1, (float) b1, (float) a1).endVertex();
    }
  }

  public static void drawTextRGBA(@Nonnull FontRenderer font, @Nonnull String s, int x, int y, int r, int g, int b, int a) {
    font.drawString(s, x, y, (a << 24) + (r << 16) + (g << 8) + (b));
  }

  public static void renderStarBurst(@Nonnull BufferBuilder buf, double x, double y, double z, float r, float g, float b, float a, double width,
      double radius) {
    for (double i = -Math.PI * 0.5; i <= Math.PI * 0.5; i += Math.PI * 0.125) {
      for (double j = 0; j < Math.PI * 2.0; j += Math.PI * (0.75 * (Math.abs(i / (Math.PI * 0.5))) + 0.25)) {
        double si = ((float) (LibEvents.ticks) / 80.0f) * (0.375f + 0.773 * j / (Math.PI * 2.0)) * (0.25f + 1.225 * i / (Math.PI)) * Math.PI * 2.0;
        double sj = ((float) (LibEvents.ticks) / 57.0f) * (0.25f + 1.63 * i / (Math.PI * 0.5)) * (0.375f + 0.241 * j / (Math.PI)) * Math.PI * 2.0;
        double dx = radius * Math.sin(sj) * Math.cos(si);
        double dy = radius * Math.sin(si);
        double dz = radius * Math.cos(sj) * Math.cos(si);
        RenderUtil
            .renderBeam(buf, x + dx * 0.25, y + dy * 0.25, z + dz * 0.25, x + dx * 0.375, y + dy * 0.375, z + dz * 0.375, r, g, b, 0.0f, r, g, b, a, width);
        RenderUtil.renderBeam(buf, x + dx * 0.375, y + dy * 0.375, z + dz * 0.375, x + dx, y + dy, z + dz, r, g, b, a, r, g, b, 0.0f, width);
      }
    }
  }

  public static void renderBeamCircle(@Nonnull BufferBuilder buf, double x, double y, double z, float r, float g, float b, float a, double width, double radius,
      int steps, boolean horiz) {
    for (double i = 0; i <= Math.PI * 2.0; i += Math.PI * (2.0 / (double) steps)) {
      double x1 = x + Math.sin(i) * radius;
      double z1 = z + Math.cos(i) * radius;
      double x2 = x + Math.sin(i + Math.PI * (2.0 / (double) steps)) * radius;
      double z2 = z + Math.cos(i + Math.PI * (2.0 / (double) steps)) * radius;
      RenderUtil.renderBeamSeries(buf, x1, y, z1, x2, y, z2, r, g, b, a, width, horiz);
    }
  }

  public static void drawCrystal(@Nonnull BufferBuilder buf, float x, float y, float z, float r, float g, float b, float a, float rotation, float hsize,
      float ysize, float minU, float minV, float maxU, float maxV) {
    float offX1 = hsize * 0.5f * (float) Math.sin(Math.toRadians(rotation));
    float offZ1 = hsize * 0.5f * (float) Math.cos(Math.toRadians(rotation));
    float offX2 = hsize * 0.5f * (float) Math.sin(Math.toRadians(rotation + 90.0f));
    float offZ2 = hsize * 0.5f * (float) Math.cos(Math.toRadians(rotation + 90.0f));
    float pos1X = x;
    double pos1Y = y - ysize * 0.5f;
    double pos1Z = z;
    double pos2X = x + offX1;
    double pos2Y = y;
    double pos2Z = z + offZ1;
    double pos3X = x + offX2;
    double pos3Y = y;
    double pos3Z = z + offZ2;
    double pos4X = x - offX1;
    double pos4Y = y;
    double pos4Z = z - offZ1;
    double pos5X = x - offX2;
    double pos5Y = y;
    double pos5Z = z - offZ2;
    double pos6X = x;
    double pos6Y = y + ysize * 0.5f;
    double pos6Z = z;
    Vec3d diff1 = new Vec3d(pos3X - pos1X, pos3Y - pos1Y, pos3Z - pos1Z);
    Vec3d diff2 = new Vec3d(pos2X - pos1X, pos2Y - pos1Y, pos2Z - pos1Z);
    Vec3d normal1 = new Vec3d(diff1.y * diff2.z - diff1.z * diff2.y, diff1.x * diff2.z - diff1.z * diff2.x, diff1.x * diff2.y - diff1.y * diff2.x).normalize()
        .scale(-1.0);
    Vec3d normal2 = new Vec3d(normal1.z, -normal1.y, normal1.x).scale(-1.0);
    Vec3d normal3 = new Vec3d(-normal1.x, -normal1.y, -normal1.z).scale(-1.0);
    Vec3d normal4 = new Vec3d(-normal1.z, -normal1.y, -normal1.x).scale(-1.0);
    Vec3d normal5 = new Vec3d(normal1.x, normal1.y, normal1.z).scale(-1.0);
    Vec3d normal6 = new Vec3d(normal1.z, normal1.y, normal1.x).scale(-1.0);
    Vec3d normal7 = new Vec3d(-normal1.x, normal1.y, -normal1.z).scale(-1.0);
    Vec3d normal8 = new Vec3d(-normal1.z, normal1.y, -normal1.x).scale(-1.0);
    normal1 = new Vec3d(normal1.x, -normal1.y, normal1.z).scale(-1.0);
    buf.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(r, g, b, a).normal((float) normal1.x, (float) normal1.y, (float) normal1.z).endVertex();
    buf.pos(pos2X, pos2Y, pos2Z).tex(minU, minV).color(r, g, b, a).normal((float) normal1.x, (float) normal1.y, (float) normal1.z).endVertex();
    buf.pos(pos3X, pos3Y, pos3Z).tex(maxU, minV).color(r, g, b, a).normal((float) normal1.x, (float) normal1.y, (float) normal1.z).endVertex();

    buf.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(r, g, b, a).normal((float) normal2.x, (float) normal2.y, (float) normal2.z).endVertex();
    buf.pos(pos3X, pos3Y, pos3Z).tex(minU, minV).color(r, g, b, a).normal((float) normal2.x, (float) normal2.y, (float) normal2.z).endVertex();
    buf.pos(pos4X, pos4Y, pos4Z).tex(maxU, minV).color(r, g, b, a).normal((float) normal2.x, (float) normal2.y, (float) normal2.z).endVertex();

    buf.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(r, g, b, a).normal((float) normal3.x, (float) normal3.y, (float) normal3.z).endVertex();
    buf.pos(pos4X, pos4Y, pos4Z).tex(minU, minV).color(r, g, b, a).normal((float) normal3.x, (float) normal3.y, (float) normal3.z).endVertex();
    buf.pos(pos5X, pos5Y, pos5Z).tex(maxU, minV).color(r, g, b, a).normal((float) normal3.x, (float) normal3.y, (float) normal3.z).endVertex();

    buf.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(r, g, b, a).normal((float) normal4.x, (float) normal4.y, (float) normal4.z).endVertex();
    buf.pos(pos5X, pos5Y, pos5Z).tex(minU, minV).color(r, g, b, a).normal((float) normal4.x, (float) normal4.y, (float) normal4.z).endVertex();
    buf.pos(pos2X, pos2Y, pos2Z).tex(maxU, minV).color(r, g, b, a).normal((float) normal4.x, (float) normal4.y, (float) normal4.z).endVertex();

    buf.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(r, g, b, a).normal((float) normal5.x, (float) normal5.y, (float) normal5.z).endVertex();
    buf.pos(pos2X, pos2Y, pos2Z).tex(minU, maxV).color(r, g, b, a).normal((float) normal5.x, (float) normal5.y, (float) normal5.z).endVertex();
    buf.pos(pos3X, pos3Y, pos3Z).tex(maxU, maxV).color(r, g, b, a).normal((float) normal5.x, (float) normal5.y, (float) normal5.z).endVertex();

    buf.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(r, g, b, a).normal((float) normal6.x, (float) normal6.y, (float) normal6.z).endVertex();
    buf.pos(pos3X, pos3Y, pos3Z).tex(minU, maxV).color(r, g, b, a).normal((float) normal6.x, (float) normal6.y, (float) normal6.z).endVertex();
    buf.pos(pos4X, pos4Y, pos4Z).tex(maxU, maxV).color(r, g, b, a).normal((float) normal6.x, (float) normal6.y, (float) normal6.z).endVertex();

    buf.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(r, g, b, a).normal((float) normal7.x, (float) normal7.y, (float) normal7.z).endVertex();
    buf.pos(pos4X, pos4Y, pos4Z).tex(minU, maxV).color(r, g, b, a).normal((float) normal7.x, (float) normal7.y, (float) normal7.z).endVertex();
    buf.pos(pos5X, pos5Y, pos5Z).tex(maxU, maxV).color(r, g, b, a).normal((float) normal7.x, (float) normal7.y, (float) normal7.z).endVertex();

    buf.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(r, g, b, a).normal((float) normal8.x, (float) normal8.y, (float) normal8.z).endVertex();
    buf.pos(pos5X, pos5Y, pos5Z).tex(minU, maxV).color(r, g, b, a).normal((float) normal8.x, (float) normal8.y, (float) normal8.z).endVertex();
    buf.pos(pos2X, pos2Y, pos2Z).tex(maxU, maxV).color(r, g, b, a).normal((float) normal8.x, (float) normal8.y, (float) normal8.z).endVertex();
  }

  public static void drawCrystal(@Nonnull BufferBuilder b, float x, float y, float z, float rotation, float hsize, float ysize, float minU, float minV,
      float maxU, float maxV) {
    float offX1 = hsize * 0.5f * (float) Math.sin(Math.toRadians(rotation));
    float offZ1 = hsize * 0.5f * (float) Math.cos(Math.toRadians(rotation));
    float offX2 = hsize * 0.5f * (float) Math.sin(Math.toRadians(rotation + 90.0f));
    float offZ2 = hsize * 0.5f * (float) Math.cos(Math.toRadians(rotation + 90.0f));
    float pos1X = x;
    double pos1Y = y - ysize * 0.5f;
    double pos1Z = z;
    double pos2X = x + offX1;
    double pos2Y = y;
    double pos2Z = z + offZ1;
    double pos3X = x + offX2;
    double pos3Y = y;
    double pos3Z = z + offZ2;
    double pos4X = x - offX1;
    double pos4Y = y;
    double pos4Z = z - offZ1;
    double pos5X = x - offX2;
    double pos5Y = y;
    double pos5Z = z - offZ2;
    double pos6X = x;
    double pos6Y = y + ysize * 0.5f;
    double pos6Z = z;
    Vec3d diff1 = new Vec3d(pos3X - pos1X, pos3Y - pos1Y, pos3Z - pos1Z);
    Vec3d diff2 = new Vec3d(pos2X - pos1X, pos2Y - pos1Y, pos2Z - pos1Z);
    Vec3d normal1 = new Vec3d(diff1.y * diff2.z - diff1.z * diff2.y, diff1.x * diff2.z - diff1.z * diff2.x, diff1.x * diff2.y - diff1.y * diff2.x).normalize()
        .scale(-1.0);
    Vec3d normal2 = new Vec3d(normal1.z, -normal1.y, normal1.x).scale(-1.0);
    Vec3d normal3 = new Vec3d(-normal1.x, -normal1.y, -normal1.z).scale(-1.0);
    Vec3d normal4 = new Vec3d(-normal1.z, -normal1.y, -normal1.x).scale(-1.0);
    Vec3d normal5 = new Vec3d(normal1.x, normal1.y, normal1.z).scale(-1.0);
    Vec3d normal6 = new Vec3d(normal1.z, normal1.y, normal1.x).scale(-1.0);
    Vec3d normal7 = new Vec3d(-normal1.x, normal1.y, -normal1.z).scale(-1.0);
    Vec3d normal8 = new Vec3d(-normal1.z, normal1.y, -normal1.x).scale(-1.0);
    normal1 = new Vec3d(normal1.x, -normal1.y, normal1.z).scale(-1.0);
    b.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(255, 255, 255, 255).normal((float) normal1.x, (float) normal1.y, (float) normal1.z)
        .endVertex();
    b.pos(pos2X, pos2Y, pos2Z).tex(minU, minV).color(255, 255, 255, 255).normal((float) normal1.x, (float) normal1.y, (float) normal1.z).endVertex();
    b.pos(pos3X, pos3Y, pos3Z).tex(maxU, minV).color(255, 255, 255, 255).normal((float) normal1.x, (float) normal1.y, (float) normal1.z).endVertex();

    b.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(255, 255, 255, 255).normal((float) normal2.x, (float) normal2.y, (float) normal2.z)
        .endVertex();
    b.pos(pos3X, pos3Y, pos3Z).tex(minU, minV).color(255, 255, 255, 255).normal((float) normal2.x, (float) normal2.y, (float) normal2.z).endVertex();
    b.pos(pos4X, pos4Y, pos4Z).tex(maxU, minV).color(255, 255, 255, 255).normal((float) normal2.x, (float) normal2.y, (float) normal2.z).endVertex();

    b.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(255, 255, 255, 255).normal((float) normal3.x, (float) normal3.y, (float) normal3.z)
        .endVertex();
    b.pos(pos4X, pos4Y, pos4Z).tex(minU, minV).color(255, 255, 255, 255).normal((float) normal3.x, (float) normal3.y, (float) normal3.z).endVertex();
    b.pos(pos5X, pos5Y, pos5Z).tex(maxU, minV).color(255, 255, 255, 255).normal((float) normal3.x, (float) normal3.y, (float) normal3.z).endVertex();

    b.pos(pos1X, pos1Y, pos1Z).tex((minU + maxU) / 2.0, maxV).color(255, 255, 255, 255).normal((float) normal4.x, (float) normal4.y, (float) normal4.z)
        .endVertex();
    b.pos(pos5X, pos5Y, pos5Z).tex(minU, minV).color(255, 255, 255, 255).normal((float) normal4.x, (float) normal4.y, (float) normal4.z).endVertex();
    b.pos(pos2X, pos2Y, pos2Z).tex(maxU, minV).color(255, 255, 255, 255).normal((float) normal4.x, (float) normal4.y, (float) normal4.z).endVertex();

    b.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(255, 255, 255, 255).normal((float) normal5.x, (float) normal5.y, (float) normal5.z)
        .endVertex();
    b.pos(pos2X, pos2Y, pos2Z).tex(minU, maxV).color(255, 255, 255, 255).normal((float) normal5.x, (float) normal5.y, (float) normal5.z).endVertex();
    b.pos(pos3X, pos3Y, pos3Z).tex(maxU, maxV).color(255, 255, 255, 255).normal((float) normal5.x, (float) normal5.y, (float) normal5.z).endVertex();

    b.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(255, 255, 255, 255).normal((float) normal6.x, (float) normal6.y, (float) normal6.z)
        .endVertex();
    b.pos(pos3X, pos3Y, pos3Z).tex(minU, maxV).color(255, 255, 255, 255).normal((float) normal6.x, (float) normal6.y, (float) normal6.z).endVertex();
    b.pos(pos4X, pos4Y, pos4Z).tex(maxU, maxV).color(255, 255, 255, 255).normal((float) normal6.x, (float) normal6.y, (float) normal6.z).endVertex();

    b.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(255, 255, 255, 255).normal((float) normal7.x, (float) normal7.y, (float) normal7.z)
        .endVertex();
    b.pos(pos4X, pos4Y, pos4Z).tex(minU, maxV).color(255, 255, 255, 255).normal((float) normal7.x, (float) normal7.y, (float) normal7.z).endVertex();
    b.pos(pos5X, pos5Y, pos5Z).tex(maxU, maxV).color(255, 255, 255, 255).normal((float) normal7.x, (float) normal7.y, (float) normal7.z).endVertex();

    b.pos(pos6X, pos6Y, pos6Z).tex((minU + maxU) / 2.0, minV).color(255, 255, 255, 255).normal((float) normal8.x, (float) normal8.y, (float) normal8.z)
        .endVertex();
    b.pos(pos5X, pos5Y, pos5Z).tex(minU, maxV).color(255, 255, 255, 255).normal((float) normal8.x, (float) normal8.y, (float) normal8.z).endVertex();
    b.pos(pos2X, pos2Y, pos2Z).tex(maxU, maxV).color(255, 255, 255, 255).normal((float) normal8.x, (float) normal8.y, (float) normal8.z).endVertex();
  }

  public static TransformType itemTransformType = TransformType.NONE;

  public static void setTransform(@Nonnull TransformType t) {
    itemTransformType = t;
  }

  public static void setTransformGUI() {
    itemTransformType = TransformType.GUI;
  }
}
