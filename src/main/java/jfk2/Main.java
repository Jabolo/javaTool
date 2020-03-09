package jfk2;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import jfk2.instructions.ListInstruction;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream(args[1]);
        JarInputStream jis = new JarInputStream(is);
        String pathMy = "";
        File sourceFileOrDir = new File(pathMy);
        ClassPool classPool = ClassPool.getDefault();
        if (args.length > 0) {
            System.out.println(args.length);
            // iterating the args array
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--i": {
                        pathMy = args[1 + i];
                        pathMy = pathMy.replaceAll("\\\\", "/");
                        is = new FileInputStream(args[1 + i]);
                        jis = new JarInputStream(is);
                        sourceFileOrDir = new File(pathMy);
                        try {
                            classPool.insertClassPath(pathMy);
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "--list-packages": {
                        ZipEntry ze = jis.getNextEntry();
                        System.out.println("packages:");
                        while (ze != null) {
                            if (ze.getName().endsWith("/")) {
                                System.out.println(ze.getName());
                            }
                            ze = jis.getNextEntry();
                        }
                        break;
                    }
                    case "--list-classes": {
                        ListInstruction.listClasses(jis);
                    }
                    case "--list-methods": {
                        String pa = "file:///" + pathMy;
                        URL[] urls = new URL[]{new URL(pa)};
                        URLClassLoader urlClassLoader = new URLClassLoader(urls);
                        try {
                            Class<?> classMine = urlClassLoader.loadClass(args[1 + i]);
                            Method[] methods = classMine.getDeclaredMethods();
                            System.out.println("methods:");
                            for (int j = 0; j < methods.length; j++)
                                System.out.println(methods[j].toString());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "--list-fields": {
                        String pa = "file:///" + pathMy;
                        URL[] urls = new URL[]{new URL(pa)};
                        URLClassLoader urlClassLoader = new URLClassLoader(urls);
                        try {
                            Class<?> classMine = urlClassLoader.loadClass(args[1 + i]);
                            Field[] fields = classMine.getDeclaredFields();
                            System.out.println("fields:");
                            for (int j = 0; j < fields.length; j++)
                                System.out.println(fields[j].toString());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "--list-ctors": {
                        String pa = "file:///" + pathMy;
                        URL[] urls = new URL[]{new URL(pa)};
                        URLClassLoader urlClassLoader = new URLClassLoader(urls);
                        try {
                            Class<?> classMine = urlClassLoader.loadClass(args[1 + i]);
                            Constructor[] cons = classMine.getDeclaredConstructors();
                            System.out.println("ctors:");
                            for (int j = 0; j < cons.length; j++)
                                System.out.println(cons[j].toString());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "--script": {
                        try {
                            String instr;
                            Scanner scanner = new Scanner(new FileInputStream(args[1 + i]));
                            while (scanner.hasNextLine()) {
                                instr = scanner.nextLine();
                                if (instr.startsWith("add-package")) {

                                }
                                if (instr.startsWith("add-class")) {
                                    String className = instr.substring(instr.lastIndexOf(" "));
                                    CtClass x = classPool.makeClass(className);
                                    x.writeFile();
                                }
                                if (instr.startsWith("remove-class")) {

                                }
                            }
                            scanner.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (CannotCompileException e) {
                            e.printStackTrace();
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }

                        ClassPool.doPruning = false;
                        CtClass cc = classPool.makeClass("testPack.Inside.xClass");
                        ClassPool.doPruning = false;
                    }
                    case "--o": {

                        File destDir = new File(pathMy.substring(0, pathMy.lastIndexOf("/")));
                        if (sourceFileOrDir.isFile()) {
                            copyJarFile(new JarFile(sourceFileOrDir), destDir);
                        } else if (sourceFileOrDir.isDirectory()) {
                            File[] files = sourceFileOrDir.listFiles(new FilenameFilter() {
                                public boolean accept(File dir, String name) {
                                    return name.endsWith(".jar");
                                }
                            });
                            for (File f : files) {
                                copyJarFile(new JarFile(f), destDir);
                            }
                        }
                    }
                }
            }
        } else
            System.out.println("No command line " +
                    "arguments found.");

    }

    public static void copyJarFile(JarFile jarFile, File destDir) throws IOException {
        String fileName = jarFile.getName();
        String fileNameLastPart = fileName.substring(fileName.lastIndexOf(File.separator));
        fileNameLastPart = fileNameLastPart.replaceAll("\\.jar", "_modified.jar");
        File destFile = new File(destDir, fileNameLastPart);

        JarOutputStream jos = new JarOutputStream(new FileOutputStream(destFile));
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            InputStream is = jarFile.getInputStream(entry);
            jos.putNextEntry(new JarEntry(entry.getName()));
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) {
                jos.write(buffer, 0, bytesRead);
            }
            is.close();
            jos.flush();
            jos.closeEntry();
        }
        jos.close();
    }
}
