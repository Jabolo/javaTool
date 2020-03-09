package jfk2.instructions.methods;

import javassist.*;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.Arrays;

public class AddMethodInstruction implements InstructionInterface {

    String dest = null;
    String returnType = null;
    String methodDeclaration = "";
    public AddMethodInstruction() {
    }

    public void addToDeclaration(String s){
        methodDeclaration += " " + s;
    }

    @Override
    public void perform() {
        if(methodDeclaration.contains("void")) addToDeclaration("{}");
        else if(Arrays.asList("byte","char","short","int","long","float","double").contains(returnType)) addToDeclaration("{return 0;}");
        else if(returnType.equals("boolean")) addToDeclaration("{return true;}");
        else addToDeclaration("{return null;}");
        System.out.println("add_method: into:" + dest + " ->" + methodDeclaration);
        try {
            CtClass newClass = pool.get(dest);
            CtMethod method = CtNewMethod.make(methodDeclaration, newClass);
            newClass.addMethod(method);
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
    public void setReturnType(String returnType) { this.returnType = returnType;
    }
}
