package epicsquid.mysticallib.fluid;

import java.util.function.Predicate;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class ExtendedFluidTank extends FluidTank {
  Predicate<Fluid> predicate;
  boolean output = false;

  public ExtendedFluidTank(int capacity, Predicate<Fluid> predicate, boolean output) {
    super(capacity);
    this.predicate = predicate;
    this.output = output;
  }

  public boolean canOutput() {
    return output;
  }

  public boolean appliesTo(FluidStack fluid) {
    return (predicate == null || predicate != null && fluid != null && predicate.test(fluid.getFluid()));
  }

  @Override
  public boolean canFillFluidType(FluidStack fluid) {
    return canFill() && appliesTo(fluid);
  }
}
