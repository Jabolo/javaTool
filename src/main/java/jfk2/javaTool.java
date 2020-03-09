package jfk2;

import jfk2.arguments.DivideArguments;
import jfk2.instructions.Instruction;
import jfk2.jarTools.CreateNewJar;
import jfk2.jarTools.jarMainTool;

import java.io.IOException;

public class javaTool {
    public static void main(String[] args) throws IOException {
        DivideArguments theOneWhoMakeInstructions = new DivideArguments(args); //robi liste instrukcji z parametrami
        Instruction.PerformInstructions(Instruction.instructionsList);
    }
}
