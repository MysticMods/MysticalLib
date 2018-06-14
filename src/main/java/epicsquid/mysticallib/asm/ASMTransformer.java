package epicsquid.mysticallib.asm;

import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nullable;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class ASMTransformer implements IClassTransformer {
  @Override
  public byte[] transform(String name, String transformedName, byte[] basicClass) {
    if (transformedName.compareTo("net.minecraft.client.renderer.RenderItem") == 0) {
      return patchRenderItemASM(name, basicClass, name.compareTo(transformedName) != 0);
    }
    if (transformedName.compareTo("net.minecraftforge.client.ForgeHooksClient") == 0) {
      return patchForgeHooksASM(name, basicClass, name.compareTo(transformedName) != 0);
    }
    return basicClass;
  }

  public byte[] patchForgeHooksASM(String name, byte[] bytes, boolean obfuscated) {
    String targetMethod = "";
    String transformTypeName = "";
    if (obfuscated) {
      targetMethod = "handleCameraTransforms";
      transformTypeName = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;";
    } else {
      targetMethod = "handleCameraTransforms";
      transformTypeName = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;";
    }

    ClassNode classNode = new ClassNode();
    ClassReader classReader = new ClassReader(bytes);
    classReader.accept(classNode, 0);

    List<MethodNode> methods = classNode.methods;

    for (MethodNode m : methods) {
      if (m.name.compareTo(targetMethod) == 0) {
        InsnList code = m.instructions;
        List<LocalVariableNode> vars = m.localVariables;
        int paramloc = -1;
        if (!obfuscated) {
          paramloc = 1;
        }
        for (int i = 0; i < vars.size() && paramloc == -1; i++) {
          LocalVariableNode p = vars.get(i);
          if (p.desc.compareTo(transformTypeName) == 0) {
            paramloc = i;
          }
        }

        if (paramloc > -1) {
          MethodInsnNode method = new MethodInsnNode(Opcodes.INVOKESTATIC, "epicsquid/mysticallib/util/RenderUtil", "setTransform", "(" + transformTypeName + ")V",
              false);
          code.insertBefore(code.get(2), method);
          code.insertBefore(code.get(2), new VarInsnNode(Opcodes.ALOAD, paramloc));
          System.out.println("Successfully patched ForgeHooksClient!");
        } else {
        }
      }
    }

    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    classNode.accept(writer);
    return writer.toByteArray();
  }

  public byte[] patchRenderItemASM(String name, byte[] bytes, boolean obfuscated) {
    String targetMethod = "";
    String targetDesc = "";
    if (obfuscated) {
      targetMethod = "func_180454_a";
      targetDesc = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V";
    } else {
      targetMethod = "renderItem";
      targetDesc = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V";
    }

    ClassNode classNode = new ClassNode();
    ClassReader classReader = new ClassReader(bytes);
    classReader.accept(classNode, 0);

    List<MethodNode> methods = classNode.methods;

    for (MethodNode m : methods) {
      if (m.name.compareTo(targetMethod) == 0 && m.desc.compareTo(targetDesc) == 0) {
        InsnList code = m.instructions;
        List<LocalVariableNode> vars = m.localVariables;
        int paramloc = 1;
        @Nullable AbstractInsnNode returnNode = null;
        for (ListIterator<AbstractInsnNode> iterator = code.iterator(); iterator.hasNext(); ) {
          AbstractInsnNode insn = iterator.next();

          if (insn.getOpcode() == Opcodes.RETURN) {
            returnNode = insn;
            break;
          }
        }

        if (returnNode != null && paramloc > -1) {
          MethodInsnNode method = new MethodInsnNode(Opcodes.INVOKESTATIC, "epicsquid/mysticallib/util/RenderUtil", "renderItem", "(Lnet/minecraft/item/ItemStack;)V",
              false);
          code.insertBefore(returnNode, new VarInsnNode(Opcodes.ALOAD, paramloc));
          code.insertBefore(returnNode, method);
          System.out.println("Successfully loaded RenderItem ASM!");
        } else {
        }
      }
    }

    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    classNode.accept(writer);
    return writer.toByteArray();
  }
}
