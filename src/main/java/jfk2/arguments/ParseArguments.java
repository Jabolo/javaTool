package jfk2.arguments;

import jfk2.instructions.Ctors.AddCtorInstruction;
import jfk2.instructions.Ctors.RemoveCtorInstruction;
import jfk2.instructions.Ctors.SetCtorBodyInstruction;
import jfk2.instructions.InputInstruction;
import jfk2.instructions.Instruction;
import jfk2.instructions.ListInstruction;
import jfk2.instructions.OutputInstruction;
import jfk2.instructions.classes.AddClassInstruction;
import jfk2.instructions.classes.RemoveClassInstruction;
import jfk2.instructions.fields.AddFieldInstruction;
import jfk2.instructions.fields.RemoveFieldInstruction;
import jfk2.instructions.interfaces.AddInterfaceInstruction;
import jfk2.instructions.interfaces.RemoveInterfaceInstruction;
import jfk2.instructions.methods.*;
import jfk2.instructions.packages.AddPackageInstruction;
import jfk2.instructions.packages.RemovePackageInstruction;

import java.util.List;

public class ParseArguments {
    public ParseArguments(List<String> arguments) {
        for (int i = 0; i < arguments.size(); i++) {
            Instruction tempInstr = new Instruction();

            try {
                if (arguments.get(i).equals("--i"))
                    Instruction.instructionsList.add(new InputInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--list-packages") || arguments.get(i).equals("--list-classes"))
                    Instruction.instructionsList.add(new ListInstruction(arguments.get(i)));
                else if (arguments.get(i).equals("--list-methods") || arguments.get(i).equals("--list-ctors") || arguments.get(i).equals("--list-fields"))
                    Instruction.instructionsList.add(new ListInstruction(arguments.get(i), arguments.get(++i)));
                else if (arguments.get(i).equals("--add-package"))
                    Instruction.instructionsList.add(new AddPackageInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--remove-package"))
                    Instruction.instructionsList.add(new RemovePackageInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--add-class"))
                    Instruction.instructionsList.add(new AddClassInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--add-interface"))
                    Instruction.instructionsList.add(new AddInterfaceInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--remove-class"))
                    Instruction.instructionsList.add(new RemoveClassInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--remove-interface"))
                    Instruction.instructionsList.add(new RemoveInterfaceInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--add-method")) {
                    AddMethodInstruction NewAddMethodInstruction = new AddMethodInstruction();
                    NewAddMethodInstruction.setDest(arguments.get(++i));
                    NewAddMethodInstruction.setReturnType(arguments.get(++i));
                    for (int j = ++i; j < arguments.size(); j++) {
                        NewAddMethodInstruction.addToDeclaration(arguments.get(j));
                        if (arguments.get(j).substring(arguments.get(j).length() - 1).equals(")")) { //End od declaration
                            i--;
                            break;
                        }
                    }
                    Instruction.instructionsList.add(NewAddMethodInstruction);
                } else if (arguments.get(i).equals("--remove-method"))
                    Instruction.instructionsList.add(new RemoveMethodInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--set-method-body"))
                    Instruction.instructionsList.add(new SetMethodBodyInstruction(arguments.get(++i), arguments.get(++i)));
                else if (arguments.get(i).equals("--add-before-method"))
                    Instruction.instructionsList.add(new AddBeforeMethodInstruction(arguments.get(++i), arguments.get(++i)));
                else if (arguments.get(i).equals("--add-after-method"))
                    Instruction.instructionsList.add(new AddAfterMethodInstruction(arguments.get(++i), arguments.get(++i)));
                else if (arguments.get(i).equals("--add-field")) {
                    AddFieldInstruction addFieldInstruction = new AddFieldInstruction(arguments.get(++i));
                    for (int j = ++i; j < arguments.size(); j++) {
                        addFieldInstruction.addToFieldBody(arguments.get(j));
                        if (arguments.get(j).substring(arguments.get(j).length() - 1).equals(";")) {
                            i--;
                            break;
                        }
                    }
                    Instruction.instructionsList.add(addFieldInstruction);
                } else if (arguments.get(i).equals("--remove-field"))
                    Instruction.instructionsList.add(new RemoveFieldInstruction(arguments.get(++i), arguments.get(++i)));
                else if (arguments.get(i).equals("--add-ctor")) {
                    AddCtorInstruction addCtorInstruction = new AddCtorInstruction(arguments.get(++i));
                    for (int j = ++i; j < arguments.size(); j++) {
                        addCtorInstruction.addToDeclaration(arguments.get(j));
                        if (arguments.get(j).substring(arguments.get(j).length() - 1).equals(")")) { //End od declaration
                            i--;
                            break;
                        }
                    }
                    Instruction.instructionsList.add(addCtorInstruction);
                } else if (arguments.get(i).equals("--remove-ctor"))
                    Instruction.instructionsList.add(new RemoveCtorInstruction(arguments.get(++i)));
                else if (arguments.get(i).equals("--set-ctor-body"))
                    Instruction.instructionsList.add(new SetCtorBodyInstruction(arguments.get(++i), arguments.get(++i)));
                else if (arguments.get(i).equals("--o"))
                    Instruction.instructionsList.add(new OutputInstruction(arguments.get(++i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



