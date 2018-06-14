package epicsquid.mysticallib.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions({ "epicsquid.mysticalcore.asm" })
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(65536)
public class FMLPlugin implements IFMLLoadingPlugin {
  public static boolean runtimeDeobfEnabled = false;

  @Override
  public String[] getASMTransformerClass() {
    return new String[] { "epicsquid.mysticallib.asm.ASMTransformer" };
  }

  @Override
  public String getModContainerClass() {
    return "epicsquid.mysticallib.asm.MysticalCore";
  }

  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {
    runtimeDeobfEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");

  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }

}
