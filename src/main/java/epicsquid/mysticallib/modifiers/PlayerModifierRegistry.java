package epicsquid.mysticallib.modifiers;

import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerModifierRegistry {
  private static PlayerModifierRegistry INSTANCE = new PlayerModifierRegistry();

  public static PlayerModifierRegistry getInstance() {
    return INSTANCE;
  }

  private List<IAttribute> attributes;

  public PlayerModifierRegistry() {
    this.attributes = new ArrayList<>();
  }

  public IAttribute registerAttribute(IAttribute attribute) {
    this.attributes.add(attribute);
    return attribute;
  }

  public void onEntityConstructed(EntityEvent.EntityConstructing event) {
    if (event.getEntity() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getEntity();

      AbstractAttributeMap map = player.getAttributes();

      for (IAttribute attrib : attributes) {
        if (map.getAttributeInstanceByName(attrib.getName()) == null) {
          map.registerAttribute(attrib);
        }
      }
    }
  }
}
