package jfk2.instructions.Ctors;

import javassist.*;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.Arrays;

public class AddCtorInstruction implements InstructionInterface {
    String dest = null;
    String ctorDeclaration = "";
    public AddCtorInstruction(String s) {
        dest =s;
    }

    public void addToDeclaration(String s){
        ctorDeclaration += s + " ";
    }
    @Override
    public void perform() {
        addToDeclaration("{}");
        System.out.println("add_ctor: into:" + dest + " ->" + ctorDeclaration);
        try {
            CtClass newClass = pool.get(dest);
            CtConstructor ctConstructor = CtNewConstructor.make(ctorDeclaration, newClass);
            newClass.addConstructor(ctConstructor);
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
