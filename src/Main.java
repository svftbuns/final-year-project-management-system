import Checkers.Util;
import People.*;
import UserInterfaces.*;
import MasterLists.*;

import java.
        util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDatabase StudentDatabase = new StudentDatabase();
        SupervDatabase SupervDatabase = new SupervDatabase();
        FYPCoordinatorDatabase FYPCoordinatorDatabase = new FYPCoordinatorDatabase();
        ProjectDatabase ProjectDatabase = new ProjectDatabase();
        RequestDatabase RequestDatabase = new RequestDatabase();

        while(true){
            System.out.println("Welcome to NTU's FYPMS! Enter the following information to continue");
            System.out.println("Please key in your username: ");
            String userID = sc.nextLine();
            System.out.println("Please enter your password: ");
            String password = sc.nextLine();


            Student validStudent = Util.isStudent(userID);
            FYPCoordinator validFYP = Util.isFYP(userID);
            Supervisor validSuper = Util.isSupervisor(userID);

            // check if valid userID
            if(validStudent!=null){
                Util.checkPassword(validStudent, password);
                StudentUI StudentUI = new StudentUI(validStudent);
                StudentUI.welcomePage();
                Util.updateProgram();

            }
            else if(validFYP!=null){
                Util.checkPassword(validFYP, password);
                FYPCoordinatorUI FYPCoordUI = new FYPCoordinatorUI(validFYP);
                FYPCoordUI.welcomePage();
                Util.updateProgram();
            }

            else if(validSuper!=null){
                Util.checkPassword(validSuper, password);
                SupervisorUI SupervisorUI = new SupervisorUI(validSuper);
                SupervisorUI.welcomePage();
                Util.updateProgram();
            }

            else{
                System.out.println("Invalid user");}
        }


    }
}

