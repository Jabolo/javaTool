package jfk2.instructions.packages;

import javassist.CtClass;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPackageInstruction implements InstructionInterface {
    private List<String> parameters = new ArrayList<>();
    public AddPackageInstruction(String s) {parameters.add(s);
    }

    @Override
    public void perform() {
        System.out.println("add_package: " + parameters.get(0));
        CtClass newClass = pool.makeClass(parameters.get(0) + "..AddNewPackage");
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
