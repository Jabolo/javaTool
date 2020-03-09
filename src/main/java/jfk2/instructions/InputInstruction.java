package jfk2.instructions;


import javassist.NotFoundException;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputInstruction implements InstructionInterface {
    private List<String> parameters = new ArrayList<>();
    public InputInstruction(String parameter) {
            this.parameters.add(parameter);
        }

    @Override
    public void perform() {
        System.out.println("input: " + parameters.get(0));
        jarMainTool.instance.setInputFileName(parameters.get(0));
        try {
            pool.appendClassPath(jarMainTool.instance.getInputFileName());
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getInputFileName(), jarMainTool.instance.getOutputTempJarFileFullName());
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }

    }
}

