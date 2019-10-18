package epicsquid.mysticallib.material;

public interface IMaterialFactory<T> {

  T create(IMaterial material, String modid);

  default String getSuffix() {
    return "";
  }

  String getName();
}
