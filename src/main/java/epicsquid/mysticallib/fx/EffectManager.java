package epicsquid.mysticallib.fx;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EffectManager {
  public static ArrayList<Effect> effects = new ArrayList<>();
  public static ArrayList<Effect> toAdd = new ArrayList<>();

  public static void addEffect(Effect e) {
    toAdd.add(e);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onClientTick(ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
        for (int i = 0; i < effects.size(); i++) {
          if (!effects.get(i).dead) {
            effects.get(i).update();
          }
        }
      }
    }
    if (event.phase == TickEvent.Phase.END) {
      for (int i = 0; i < toAdd.size(); i++) {
        effects.add(toAdd.get(i));
      }
      toAdd.clear();
      for (int i = 0; i < effects.size(); i++) {
        if (effects.get(i).dead) {
          effects.remove(i);
          i = Math.max(0, i - 1);
        }
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRenderLast(RenderWorldLastEvent event) {
    for (int i = 0; i < effects.size(); i++) {
      effects.get(i).renderTotal(event.getPartialTicks());
    }
  }

  public static void assignEffect(EntityLivingBase entity, String effect, int duration, NBTTagCompound data){
    if (!(entity instanceof EntityPlayer)){
      if (!entity.getEntityData().hasKey(Constants.EFFECT_TAG)){
        entity.getEntityData().setTag(Constants.EFFECT_TAG, new NBTTagCompound());
      }
    }
    else if (entity.hasCapability(PlayerDataProvider.playerDataCapability, null)){
      if (!entity.getCapability(PlayerDataProvider.playerDataCapability, null).getData().hasKey(Constants.EFFECT_TAG)){
        entity.getCapability(PlayerDataProvider.playerDataCapability, null).getData().setTag(Constants.EFFECT_TAG, new NBTTagCompound());
        entity.getCapability(PlayerDataProvider.playerDataCapability, null).markDirty();
      }
    }
    NBTTagCompound tag = null;
    if (!(entity instanceof EntityPlayer)){
      tag = entity.getEntityData().getCompoundTag(Constants.EFFECT_TAG);
    }
    else if (entity.hasCapability(PlayerDataProvider.playerDataCapability, null)){
      tag = entity.getCapability(PlayerDataProvider.playerDataCapability, null).getData().getCompoundTag(Constants.EFFECT_TAG);
    }
    if (tag != null){
      if (!tag.hasKey(effect)){
        effects.get(effect).onApplied(entity, data);
      }
      tag.setInteger(effect, duration);
      tag.setTag(effect+"_data", data);
      if (entity.hasCapability(PlayerDataProvider.playerDataCapability, null)){
        entity.getCapability(PlayerDataProvider.playerDataCapability, null).markDirty();
      }
    }
  }
}
