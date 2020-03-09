package jfk2.arguments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DivideArguments {
    List<String> arguments = new ArrayList<String>();

    public DivideArguments(String[] args) {

        if (checkIfScript(args)) {
//script
            try {
                File myInputScript = new File(args[1]);
                Scanner myReader = new Scanner(myInputScript);
                while (myReader.hasNextLine()) {
                    List<String> argList = Arrays.asList(myReader.nextLine().split(" "));
                    for (String x: argList
                         ) {
                        arguments.add(x);
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            ParseArguments parser = new ParseArguments(arguments);
        } else {
//pure arguments
            for (int i = 0; i < args.length; i++) {
                arguments.add(args[i])  ;
            }
            ParseArguments parser = new ParseArguments(arguments);
        }
    }

    public static  boolean checkIfScript(String[] arguments) {
        if (arguments.length > 2)
            return false;
        else
            return true;
    }

}
