package jfk2.instructions;

import jfk2.jarTools.jarMainTool;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class ListInstruction implements InstructionInterface {
    String whatToList = null;
    String ClassName = null;
    public ListInstruction(String x) {
        whatToList = x;
    }

    public ListInstruction(String s, String s1) {
        whatToList = s;
        ClassName = s1;
    }

    @Override
    public void perform() {

        FileInputStream is = null;
        JarInputStream jis = null;
        try {
            String pa = "file:///" + jarMainTool.instance.getInputFileName();
            URL[] urls = new URL[]{new URL(pa)};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            is = new FileInputStream(jarMainTool.instance.getInputFileName());
            jis = new JarInputStream(is);
            switch (whatToList){
                case "--list-packages":{
                    listPacakges(jis);
                    break;
                }
                case "--list-classes":{
                    listClasses(jis);
                    break;
                }
                case "--list-methods":{
                    listMethods(urlClassLoader);
                    break;
                }
                case "--list-fields":{
                    listFields(urlClassLoader);
                    break;
                }
                case "--list-ctors":{
                    listCtors(urlClassLoader);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
                jis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void listCtors(URLClassLoader urlClassLoader) {
        try {
            Class<?> classMine = urlClassLoader.loadClass(ClassName);
            Constructor[] cons = classMine.getDeclaredConstructors();
            System.out.println("ctors:");
            for (int j = 0; j < cons.length; j++)
                System.out.println(cons[j].toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void listFields(URLClassLoader urlClassLoader) {
        try {
            Class<?> classMine = urlClassLoader.loadClass(ClassName);
            Field[] fields = classMine.getDeclaredFields();
            System.out.println("fields:");
            for (int j = 0; j < fields.length; j++)
                System.out.println(fields[j].toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void listMethods(ClassLoader urlClassLoader) throws MalformedURLException {
        try {
            Class<?> classMine = urlClassLoader.loadClass(ClassName);
            Method[] methods = classMine.getDeclaredMethods();
            System.out.println("methods:");
            for (int j = 0; j < methods.length; j++)
                System.out.println(methods[j].toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void listClasses(JarInputStream jis) throws IOException {
        ZipEntry ze = jis.getNextEntry();
        System.out.println("classes:");
        while (ze != null) {
            if (ze.getName().endsWith(".class")) {
                System.out.println(ze.getName().replaceAll(".*/", ""));
            }
            ze = jis.getNextEntry();
        }
        return;
    }

    private void listPacakges(JarInputStream jis) throws IOException {
        ZipEntry ze = jis.getNextEntry();
        System.out.println("packages:");
        while (ze != null) {
            if (ze.getName().endsWith("/"))
                System.out.println(ze.getName());
            ze = jis.getNextEntry();
        }
    }
}
