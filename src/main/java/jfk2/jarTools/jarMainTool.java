package jfk2.jarTools;

import javassist.CtClass;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class jarMainTool {
    public static jarMainTool instance = new jarMainTool();
    String inputFileName;
    String outputFileName;
    String outputJarFileFullName = null;
    String outputTempJarFileFullName = null;
    public String getOutputJarFileFullName() {
        return outputJarFileFullName;
    }

    public String getOutputTempJarFileFullName() {
        return outputTempJarFileFullName;
    }

    public void setOutputTempJarFileFullName(String outputTempJarFileFullName) {
        this.outputTempJarFileFullName = outputTempJarFileFullName;
    }

    public List<CtClass> modifiedCtClasses = new LinkedList<>();
    public List<String> removedCtClasses = new LinkedList<>();
    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
        //if(inputFileName.lastIndexOf("\\") == inputFileName.length())
            outputTempJarFileFullName = inputFileName.substring(0, inputFileName.lastIndexOf("\\")) + "\\tempFile";
        //else
         //   outputTempJarFileFullName = inputFileName + "\\tempFile";
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
        outputJarFileFullName = inputFileName.substring(0, inputFileName.lastIndexOf("\\"))
                + "\\" + outputFileName;
    }

}
