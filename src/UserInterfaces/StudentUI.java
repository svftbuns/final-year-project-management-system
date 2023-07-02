package UserInterfaces;

import People.Student;

import java.util.Scanner;
import Checkers.*;

public class StudentUI implements UI {

    Scanner sc = new Scanner(System.in);
    private Student student;

    public StudentUI(Student student) {
        this.student = student;
    }

    public void welcomePage() {
        int choice = 0;

        System.out.println("Welcome to NTU's FYPMS! Please select an option: ");
        System.out.println("1. Change Password");
        System.out.println("2. View Available Projects");
        System.out.println("3. Request To Register Project");
        System.out.println("4. View Own Project");
        System.out.println("5. View All Requests Status & History");
        System.out.println("6. Request To Change Project Title");
        System.out.println("7. Request To Deregister FYP");
        System.out.println("8. Log Out");

        while (true) {
            System.out.println("Enter your choice: ");
            choice = Input_cleaner.int_only(sc.nextLine());

            switch (choice) {
                case 1:
                    student.changePW(student.getPassword());
                    return;
                case 2:
                    student.viewAvailableProjects();
                    break;
                case 3:
                    if (student.getIsRegistered()) {
                        System.out.println("You already have a project registered");
                        break;
                    } else {
                        System.out.println("Enter a project you wish to register");
                        int projectChoice = Input_cleaner.projectID_only(sc.nextLine());
                        student.requestProject(projectChoice);
                    }
                    break;
                case 4:
                    student.viewOwnProject();
                    break;
                case 5:
                    student.viewRequestHistory();
                    break;
                case 6:
                    if (student.getIsRegistered()==false) {
                        System.out.println("You have not registered a project!");
                        break;
                    } else {
                        System.out.println("Enter your desired project name: ");
                        String newTitle = Input_cleaner.alphanumeric(sc.nextLine());
                    student.reqTitleChg(newTitle);
                    System.out.println("Your request for title change has been sent, please await for approval");}
                    break;
                case 7:
                    if (student.getIsRegistered()==false) {
                        System.out.println("You have no project to deregister!");
                    } else {
                        student.reqDeregister();
                        System.out.println("You have indicated your request to deregister, please await for approval");
                    }
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }

        }
    }
}
