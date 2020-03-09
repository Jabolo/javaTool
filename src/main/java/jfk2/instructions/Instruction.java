package jfk2.instructions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Instruction implements InstructionInterface {
    public static LinkedList<InstructionInterface> instructionsList = new LinkedList<>(); //styczna lista instrukcji

    public static void PerformInstructions(List<InstructionInterface> instructionsList) {
        for (InstructionInterface instr: instructionsList
        ) {
            instr.perform();
        }
    }

    @Override
    public void perform() {

    }
}
