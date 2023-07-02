package MasterLists;
import IndividualClasses.Project;
import People.Supervisor;
import java.io.*;
import java.util.*;

public class SupervDatabase
{
    public static ArrayList<Supervisor> SupervisorList = new ArrayList<Supervisor>();
    public SupervDatabase () {//parsing a CSV file into Scanner class constructor
        try {

            Scanner sc = new Scanner(new File("src//Databases//faculty_list.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern

            while (sc.hasNext()) {
                String[] splitInput = sc.nextLine().split(",", 3);


                if (splitInput.length == 3) {
                    String[] removeAdd = splitInput[1].split("@", 2);
                    SupervisorList.add(new Supervisor(splitInput[0].trim(), removeAdd[0], splitInput[2]));
                }
            }



        }
        catch(FileNotFoundException e)
            {
            System.out.println("File not found hehe");
            }
        }

        public static void updateSupervisorList(){
        PrintWriter writer = null;
        try {
        writer = new PrintWriter("src//Databases//faculty_list.csv");

        // this clears the contents of the file
        writer.write("");

        for(Supervisor p: SupervisorList){
        writer.printf("%s,%s,%s\n", p.getName(), p.getID()+"@NTU.EDU.SG", p.getPassword());
        }

        writer.close();}

        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }
}
}
