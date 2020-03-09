package jfk2.instructions.fields;

import javassist.*;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;

public class AddFieldInstruction implements InstructionInterface {
    String className = null;
    String fieldBody = "";
    public AddFieldInstruction(String className) {
        this.className = className;
    }
    public void addToFieldBody(String x){
        fieldBody += x + " ";
    }
    @Override
    public void perform() {
        System.out.println("add_field: " + fieldBody);
        try {
            CtClass newClass = pool.get(className);
            CtField newField = CtField.make(fieldBody, newClass);
            newClass.addField(newField);
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
