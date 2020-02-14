package epicsquid.mysticallib.item;

import com.google.common.collect.Lists;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DeferredFood extends Food {
  private final List<Pair<Supplier<EffectInstance>, Float>> effects;
  private List<Pair<EffectInstance, Float>> effectsCalculated = null;

  public DeferredFood(int healing, float saturationIn, boolean isMeat, boolean alwaysEdible, boolean fastEdible, List<Pair<Supplier<EffectInstance>, Float>> effectsIn) {
    super(healing, saturationIn, isMeat, alwaysEdible, fastEdible, Collections.emptyList());
    this.effects = effectsIn;
  }

  public List<Pair<EffectInstance, Float>> getEffects() {
    if (effectsCalculated == null) {
      effectsCalculated = Lists.newArrayList();
      for (Pair<Supplier<EffectInstance>, Float> pair : effects) {
        effectsCalculated.add(Pair.of(pair.getLeft().get(), pair.getRight()));
      }
    }
    return effectsCalculated;
  }

  public static class Builder extends Food.Builder {
    private int value;
    private float saturation;
    private boolean meat;
    private boolean alwaysEdible;
    private boolean fastToEat;
    private final List<Pair<Supplier<EffectInstance>, Float>> effects = Lists.newArrayList();

    public Builder effect(Supplier<EffectInstance> effectIn, float probability) {
      this.effects.add(Pair.of(effectIn, probability));
      return this;
    }

    public Builder hunger(int hungerIn) {
      this.value = hungerIn;
      return this;
    }

    public Builder saturation(float saturationIn) {
      this.saturation = saturationIn;
      return this;
    }

    public Builder meat() {
      this.meat = true;
      return this;
    }

    public Builder setAlwaysEdible() {
      this.alwaysEdible = true;
      return this;
    }

    public Builder fastToEat() {
      this.fastToEat = true;
      return this;
    }

    public Food build() {
      return new DeferredFood(this.value, this.saturation, this.meat, this.alwaysEdible, this.fastToEat, this.effects);
    }
  }
}
