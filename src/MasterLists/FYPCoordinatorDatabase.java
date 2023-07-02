package MasterLists;

import People.FYPCoordinator;
import People.Supervisor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FYPCoordinatorDatabase {
    public static ArrayList<FYPCoordinator> FYPList = new ArrayList<FYPCoordinator>();

    public FYPCoordinatorDatabase (){
        //parsing a CSV file into Scanner class constructor
        try {
            Scanner sc = new Scanner(new File("src//Databases//FYP coordinator.csv"));

            int index = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                String[] splitInput = sc.nextLine().split(",", 3);
                if(splitInput.length==3){
                    String[] removeAdd = splitInput[1].split("@", 2);
                    FYPList.add(new FYPCoordinator(splitInput[0].trim(), removeAdd[0], splitInput[2])); //create array of FYP Coordinator Obj
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }
    }


    public static void updateFYPCoorList(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src//Databases//FYP coordinator.csv");

            // this clears the contents of the file
            writer.write("");

            for(FYPCoordinator p: FYPList){
                writer.printf("%s,%s,%s\n", p.getName(), p.getID()+"@NTU.EDU.SG", p.getPassword());
            }

            writer.close();}

        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }
    }
}


