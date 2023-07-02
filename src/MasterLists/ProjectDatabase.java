package MasterLists;

import Checkers.Util;
import Enums.ProjectState;
import IndividualClasses.Project;
import People.FYPCoordinator;
import People.Student;
import People.Supervisor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
public class ProjectDatabase {
    public static ArrayList<Project> ProjectList = new ArrayList<Project>();
    public static int projectCount = 0;

    public ProjectDatabase() { //parsing a CSV file into Scanner class constructor
        try {
            Scanner sc = new Scanner(new File("src//Databases//rollover project.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern
            int projID = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                String[] splitInput = sc.nextLine().split(",", 3);
                Project newProject = new Project(projID + 1, splitInput[0], splitInput[1]);
                // check if project is allocated previously
                if (!splitInput[2].equals("Unassigned")) {
                    Student student = Util.isStudent(splitInput[2]);
                    newProject.register(student);
                    student.setIsRegistered(true);
                    student.setProjectID(projID+1);

                    // update Supervisor's/FYP Coordinator's attributes
                    Supervisor supervisor = Util.isSupervisor(newProject.getSuperID());
                    FYPCoordinator fyp = Util.isFYP(newProject.getSuperID());
                    if(supervisor!=null){
                        supervisor.incrementProjCount();}
                    else if(fyp!=null){
                        fyp.incrementProjCount();
                    }
                }

                // create array of Proj Obj//
                ProjectList.add(newProject);
                projID++;
                projectCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found hehe");
        }
    }

    public static void updateProjectList() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src//Databases//rollover project.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Project File not found hehe");
        }

        // this clears the contents of the file
        writer.write("");

        for (Project p : ProjectList) {
            if (p.getStatus() == ProjectState.Allocated) {
                writer.printf("%s,%s,%s\n", p.getSuperName(), p.getTitle(), p.getStudentID());
            } else {
                writer.printf("%s,%s,Unassigned\n", p.getSuperName(), p.getTitle());
            }

        }
        writer.close();

    }
}
