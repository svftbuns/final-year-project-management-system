package Checkers;

import Enums.ProjectState;
import IndividualClasses.Project;
import MasterLists.*;
import People.FYPCoordinator;
import People.Student;
import People.Supervisor;
import People.User;

import java.util.Scanner;

public class Util{
    //utility methods to call in main

    static Scanner sc = new Scanner(System.in);

    public static Student isStudent(String user){
        //iterate through array of student objs to check for username, if user name matches, assign bool to true//
        for(Student i: StudentDatabase.StudentList){
            if(i.getID().equals(user)){
                return i;
            }
        }
        return null;
    }

    public static Supervisor isSupervisor(String user){
        for(Supervisor i: SupervDatabase.SupervisorList){
            if(i.getID().equals(user)){
                return i;
            }     }
        return null;
    }

    public static FYPCoordinator isFYP(String user){
        for(FYPCoordinator i: FYPCoordinatorDatabase.FYPList){
            if(i.getID().equals(user)){
                return i;
            }
        }
        return null;
    }


    public static void checkPassword(User user, String enteredPassword){
        while(!user.getPassword().equals(enteredPassword)){
            System.out.println("Incorrect password, please enter again: ");
            enteredPassword = sc.nextLine();
        }

    }

    // check for valid Student ID for FYPCoordinator UI
    public static boolean checkValidStudentID(String StudentID){
        for(Student i: StudentDatabase.StudentList){
            if(StudentID.equals(i.getID())){
               return true;
            }
        }
        return false;
    }

    // check for valid Supervisor ID for FYPCoordinator UI + Supervisor UI (Transfer Student)
    public static boolean checkValidSuperID(String superID){
        for(Supervisor i: SupervDatabase.SupervisorList){
            if(superID.equals(i.getID())){
                return true;
            }     }
        return false;
    }

    // check if indicated project belongs to the supervisor
    public static boolean checkOwnProject(int ProjectID, String superID) {
        // first check if ProjectID is valid
        if(ProjectID<=0||ProjectID>ProjectDatabase.ProjectList.size()){
            return false;
        }
        return ProjectDatabase.ProjectList.get(ProjectID-1).getSuperID().equals(superID);
    }

    // check if supervisors/FYP Coordinators have any new request
    public static void checkNewRequest(int incoming, int viewed) {
        if (incoming != viewed) {
            for (int i = 0; i < incoming - viewed; i++) {
                System.out.println("NEW REQUEST!");
            }
        }
    }

    // double check project state, which could have been inaccurate during initialisation
    public static void checkSupervisorCap(Project project){
        Supervisor supervisor = isSupervisor(project.getSuperID());
        FYPCoordinator fyp = isFYP(project.getSuperID());
        if((supervisor!=null && supervisor.getProjCount()==2) || (fyp!=null && fyp.getProjCount()==2)){
            project.setStatus(ProjectState.Unavailable);
        }
    }

    // update the relevant csv files
    public static void updateProgram() {
        ProjectDatabase.updateProjectList();
        RequestDatabase.updateRequestList();
        SupervDatabase.updateSupervisorList();
        FYPCoordinatorDatabase.updateFYPCoorList();
        StudentDatabase.updateStudentList();
    }
}
