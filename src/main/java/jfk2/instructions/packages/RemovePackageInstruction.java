package jfk2.instructions.packages;

import javassist.CtClass;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemovePackageInstruction implements InstructionInterface {
    private List<String> parameters = new ArrayList<>();
    public RemovePackageInstruction(String s) {parameters.add(s);}

            @Override
            public void perform() {
                System.out.println("remove_package: " + parameters.get(0));
                CtClass newClass = pool.makeClass(parameters.get(0));
                String newClassName = newClass.getName().replaceAll("\\.", "/");
                try {
                    jarMainTool.instance.removedCtClasses.add(newClassName);
                    CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
                    jarMainTool.instance.removedCtClasses.remove(newClassName);
                    newClass.defrost();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
}
