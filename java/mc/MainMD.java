package mc;

import java.io.*;

import faulty.auxiliar.*;
import maskingDistance.*;

/**
 * This class represents the compiler.
 */
public class MainMD {
	   
    
    public static void main(String[] args)
    {
       ProgramParser prog = new ProgramParser();
       boolean printTrace = false;
       boolean toDot = false;
       boolean startSimulation = false;
       boolean deadlockIsError = false;
       
       if (args.length < 2){
            System.out.println("Usage: ./faulty-mask <options> <nominal model path> <faulty model path>");
            System.out.println("Output: lim n->infinity of 1/(1+sumMaskedFaults[0..n]), where n is the number of steps and sumMaskedFaults[inf..sup] is the amount of faults masked from inf to sup");
            System.out.println("Options: \n -d : create dot file \n -t : print error trace \n -s : start simulation \n -l : treat deadlock as error state too");
       }
       else{
           for (int i = 0; i < args.length; i++){
              if (args[i].equals("-t")){
                printTrace = true;
              }
              if (args[i].equals("-s")){
                startSimulation = true;
              }
              if (args[i].equals("-d")){
                toDot = true;
              }
              if (args[i].equals("-l")){
                deadlockIsError = true;
              }
           }
            AuxiliarProgram spec = prog.parseAux(args[args.length - 2]);
            AuxiliarProgram imp = prog.parseAux(args[args.length - 1]);
            MaskingDistance md = new MaskingDistance();
            System.out.println("Masking Distance: "+md.calculateDistance(spec,imp,deadlockIsError));
            if (printTrace)
              md.printTraceToError();
            if (startSimulation)
              md.simulateGame();
            if (toDot)
              md.createDot();
        }
     }
}