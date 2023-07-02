package MasterLists;

import People.Student;

import java.io.*;
import java.util.*;

public class StudentDatabase
{
    public static ArrayList<Student> StudentList = new ArrayList<Student>();
    public StudentDatabase () {
//parsing a CSV file into Scanner class constructor
        try {
            Scanner sc = new Scanner(new File("src//Databases//student list.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern
            while (sc.hasNext())  //returns a boolean value
            {
                String[] splitInput = sc.nextLine().split(",", 4);
                String[] removeAdd = splitInput[1].split("@", 2);
                Student newStudent = new Student(splitInput[0].trim(), removeAdd[0], splitInput[2]);

                // if student has already registered for a project, set currRegistering = true
                if(splitInput[3].equals("true")){
                    newStudent.setCurrRegistering(true);
                }

                StudentList.add(newStudent); //create array of Student Obj
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }
    }

    public static void updateStudentList(){

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src//Databases//student list.csv");

            // this clears the contents of the file
            writer.write("");

            for(Student p: StudentList){
                writer.printf("%s,%s,%s,%s\n", p.getName(), p.getID()+"@E.NTU.EDU.SG", p.getPassword(), p.getCurrRegistering());
            }

            writer.close();}

        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }
    }
}

