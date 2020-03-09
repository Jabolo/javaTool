package jfk2.instructions.Ctors;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;

public class RemoveCtorInstruction implements InstructionInterface {
    String ClassName = null;
    String ctorFullName = null;
    public RemoveCtorInstruction(String s) {
        ctorFullName = s;
        //ClassName = s.substring(0, s.lastIndexOf("."));
        ClassName = s.substring(0, s.indexOf("("));
        ClassName = ClassName.substring(0, ClassName.lastIndexOf("."));
    }
    @Override
    public void perform() {
        System.out.println("remove_ctor: " + ctorFullName);
        try {
            CtClass newClass = pool.get(ClassName);
            CtConstructor[] ctors = newClass.getDeclaredConstructors();
            for (CtConstructor ctor: ctors
            ) {
                if (ctor.getLongName().equals(ctorFullName)){
                    newClass.removeConstructor(ctor);
                    break;
                }
            }
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}
