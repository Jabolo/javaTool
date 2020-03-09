package jfk2.instructions;

import javassist.ClassPool;

import java.util.LinkedList;
import java.util.List;

public interface InstructionInterface {
    ClassPool pool = ClassPool.getDefault();
    void perform();
}
