package jfk2.jarTools;

import javassist.CannotCompileException;
import javassist.CtClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class CreateNewJar {
    public CreateNewJar(String fromWhere, String toWhere) throws IOException {
        boolean needRename = false;
        if(fromWhere.equals(toWhere)){
            toWhere = toWhere + "2";
            needRename = true;
        }
        File fileNew = new File(toWhere);
        File fileOld = new File(fromWhere);
        JarFile jarFileOld = new JarFile(fileOld);
        boolean jarCreated = false;
        byte[] buffer = new byte[1024];
        int bytesRead;
        try {
            JarOutputStream jos = new JarOutputStream(new FileOutputStream(fileNew));
            try {
                //OpenFile
                try {
                    //CreateJarEntry
                    for (CtClass modifiedClass : jarMainTool.instance.modifiedCtClasses
                    ) {
                        String EntryName = null;
                        // Create a jar entry and add it to the temp jar.
                        if(modifiedClass.getName().contains("..newPackage")) {
                            EntryName = modifiedClass.getName().replaceAll("\\.\\.AddNewPackage", "").replaceAll("\\.", "/");
                            JarEntry entry = new JarEntry(EntryName);
                            jos.putNextEntry(entry);
                        }
                            else {
                            EntryName = modifiedClass.getName().replaceAll("\\.", "/") + ".class";
                            JarEntry entry = new JarEntry(EntryName);
                            jos.putNextEntry(entry);
                            jos.write(modifiedClass.toBytecode());
                        }
                    }
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                    jos.putNextEntry(new JarEntry("wrong"));
                }
                InputStream is = null;
                if(jarMainTool.instance.modifiedCtClasses.size()==0)  jarMainTool.instance.modifiedCtClasses.add(new CtClass("tempCtClass") {});
                if(jarMainTool.instance.removedCtClasses.size()==0) jarMainTool.instance.removedCtClasses.add("tempCtClass");
                for (Enumeration entries = jarFileOld.entries(); entries.hasMoreElements(); ) {
                    JarEntry entry = (JarEntry) entries.nextElement();
                    if(!entry.getName().replaceAll("\\.class","").replaceAll("/", "\\.").equals(jarMainTool.instance.modifiedCtClasses.get(0).getName())
                            //&& !jarMainTool.instance.removedCtClasses.contains(entry.getName())
                    && !entry.getName().contains(jarMainTool.instance.removedCtClasses.get(0))){
                        // Get an input stream for the entry.
                        is = jarFileOld.getInputStream(entry);
                        jos.putNextEntry(entry);
                        while ((bytesRead = is.read(buffer)) != -1) {
                            jos.write(buffer, 0, bytesRead);
                        }
                    }
                }
                if(jarMainTool.instance.modifiedCtClasses.get(0).getName().equals("tempCtClass"))jarMainTool.instance.modifiedCtClasses.remove(0);
                if(jarMainTool.instance.removedCtClasses.get(0).equals("tempCtClass"))jarMainTool.instance.removedCtClasses.remove(0);
                if (is != null) is.close();
                jarCreated = true;

            } catch (Exception e) {
                e.printStackTrace();
                jos.putNextEntry(new JarEntry("wrong"));
            } finally {
                jos.close();
            }
        } finally {
            jarFileOld.close();
        }
        if (needRename){
            fileOld.delete();
            fileNew.renameTo(fileOld);
        }

        if(toWhere.equals(jarMainTool.instance.getOutputJarFileFullName())){
            fileNew.delete();
            System.out.println("Success");
            fileOld.renameTo(fileNew);
        }
    }
}
