package jfk2.instructions.Ctors;

import javassist.*;
import jfk2.instructions.InstructionInterface;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SetCtorBodyInstruction implements InstructionInterface {
    String ClassName;
    String ctorFullName;
    String body = "";
    public SetCtorBodyInstruction(String s, String s1) throws IOException {
        ctorFullName = s;
        //ClassName = s.substring(0, s.lastIndexOf("."));
        ClassName = s.substring(0, s.indexOf("("));
        ClassName = ClassName.substring(0, ClassName.lastIndexOf("."));
        body += new String(Files.readAllBytes(Paths.get(s1)), StandardCharsets.UTF_8);
        System.out.println();
    }
    @Override
    public void perform() {
        System.out.println("set_ctor_body: " + ctorFullName);
        try {
            CtClass newClass = pool.get(ClassName);
            CtConstructor[] ctors = newClass.getDeclaredConstructors();
            for (CtConstructor ctor: ctors
            ) {
                System.out.println(ctor.getLongName());
                if (ctor.getLongName().equals(ctorFullName)){
                    ctor.setBody(body);
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
