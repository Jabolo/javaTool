package jfk2.instructions.methods;

import javassist.*;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.Arrays;

public class RemoveMethodInstruction implements InstructionInterface {
    String ClassName;
    String methodFullName;
    public RemoveMethodInstruction(String x) {
        methodFullName = x;
        //ClassName = s.substring(0, s.lastIndexOf("."));
        ClassName = x.substring(0, x.indexOf("("));
        ClassName = ClassName.substring(0, ClassName.lastIndexOf("."));
    }

    @Override
    public void perform() {
        System.out.println("remove_method: " + methodFullName);
        try {
            CtClass newClass = pool.get(ClassName);
            CtMethod[] methods = newClass.getDeclaredMethods();
            for (CtMethod thisMethod: methods
                 ) {
               if (thisMethod.getLongName().equals(methodFullName)){
                   newClass.removeMethod(thisMethod);
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
