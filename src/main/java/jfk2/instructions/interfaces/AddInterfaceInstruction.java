package jfk2.instructions.interfaces;

import javassist.CtClass;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddInterfaceInstruction implements InstructionInterface {
    private List<String> parameters = new ArrayList<>();
    public AddInterfaceInstruction(String s) { parameters.add(s);
    }

    @Override
    public void perform() {
        System.out.println("add_interface: " + parameters.get(0));
        CtClass newClass = pool.makeInterface(parameters.get(0));
        try {
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
