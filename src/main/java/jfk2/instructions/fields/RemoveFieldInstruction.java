package jfk2.instructions.fields;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;

public class RemoveFieldInstruction implements InstructionInterface {
    String className = null;
    String fieldName = null;
    public RemoveFieldInstruction(String s, String s1) {
        className = s;
        fieldName = s1;
    }
    @Override
    public void perform() {
        System.out.println("remove_field: " + fieldName + " from: " + className);
        try {
            CtClass newClass = pool.get(className);
            CtField[] fields = newClass.getDeclaredFields();
            for (CtField field: fields
            ) {
                if(field.getName().equals(fieldName)) {
                    newClass.removeField(field);
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
