package jfk2.instructions.classes;

import javassist.CtClass;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RemoveClassInstruction implements InstructionInterface {
    private List<String> parameters = new ArrayList<>();
    public RemoveClassInstruction(String s) { parameters.add(s);
    }

    @Override
    public void perform() {
        System.out.println("remove_class: " + parameters.get(0));
        CtClass newClass = pool.makeClass(parameters.get(0));
        try {
            String newClassName = newClass.getName().replaceAll("\\.", "/") + ".class";
            jarMainTool.instance.removedCtClasses.add(newClassName);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.removedCtClasses.remove(newClassName);
            newClass.defrost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
