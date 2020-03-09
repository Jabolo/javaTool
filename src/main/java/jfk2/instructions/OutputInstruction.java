package jfk2.instructions;

import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputInstruction implements InstructionInterface{
    private List<String> parameters = new ArrayList<>();

    public OutputInstruction(String parameter) {
        parameters.add(parameter);
    }

    @Override    public void perform() {
        System.out.println("output: " + parameters.get(0));
        jarMainTool.instance.setOutputFileName(parameters.get(0));
        try {
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputJarFileFullName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
