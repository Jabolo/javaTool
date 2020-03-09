package jfk2.instructions.methods;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetMethodBodyInstruction implements InstructionInterface {
    String ClassName;
    public static boolean x = false;
    String methodFullName;
    String body = "";
    public SetMethodBodyInstruction(String s, String x) throws IOException {
        methodFullName = s;
        //ClassName = s.substring(0, s.lastIndexOf("."));
        ClassName = s.substring(0, s.indexOf("("));
        ClassName = ClassName.substring(0, ClassName.lastIndexOf("."));
        body += new String(Files.readAllBytes(Paths.get(x)), StandardCharsets.UTF_8);
    }

    @Override
    public void perform() {
        System.out.println("set_method_body: " + methodFullName);
        try {
            CtClass newClass = pool.get(ClassName);
            CtMethod[] methods = newClass.getDeclaredMethods();
            for (CtMethod thisMethod: methods
            ) {
                if (thisMethod.getLongName().equals(methodFullName)){
                    thisMethod.setBody(body);
                    break;
                }
            }
            jarMainTool.instance.modifiedCtClasses.add(newClass);
            CreateNewJar x = new CreateNewJar(jarMainTool.instance.getOutputTempJarFileFullName(), jarMainTool.instance.getOutputTempJarFileFullName());
            jarMainTool.instance.modifiedCtClasses.remove(newClass);
            newClass.defrost();
        } catch (IOException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
