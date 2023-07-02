package UserInterfaces;

import Checkers.*;
import Enums.ProjectState;
import IndividualClasses.Request;
import MasterLists.RequestDatabase;
import People.FYPCoordinator;

import java.util.Scanner;

public class FYPCoordinatorUI implements UI {
    Scanner sc = new Scanner(System.in);
    private FYPCoordinator fyp;

    public FYPCoordinatorUI(FYPCoordinator fyp) {
        this.fyp = fyp;
        Util.checkNewRequest(fyp.getIncomingRequestNo(), fyp.getViewedRequestNo());
    }

    public void welcomePage() {
        // welcomePage() has different function bodies in different UI classes
        int choice = 0;

        System.out.println("Welcome to NTU's FYPMS! Please select an option: ");
        System.out.println("1. Change Password");
        System.out.println("2. Create Projects");
        System.out.println("3. Update Projects");
        System.out.println("4. View Projects");
        System.out.println("5. View Pending Requests");
        System.out.println("6: View All Requests");
        System.out.println("7. Transfer Student");
        System.out.println("8. Log Out");


        while (true) {
            System.out.println("Enter your choice: ");
            choice = Input_cleaner.int_only(sc.nextLine());


            switch (choice) {
                case 1:
                    fyp.changePW(fyp.getPassword());
                    return;

                case 2:
                    // create new project
                    System.out.println("Enter the name of the project: ");
                    String title = Input_cleaner.alphanumeric(sc.nextLine());
                    fyp.createProject(title);
                    System.out.println("Project Created!");
                    break;

                case 3:
                    // modify title of project
                    System.out.println("Enter Project ID: ");
                    int projectID = Input_cleaner.projectID_only(sc.nextLine());
                    if(Util.checkOwnProject(projectID, fyp.getID())){
                    System.out.println("Enter new title of project: ");
                    String newTitle = Input_cleaner.alphanumeric(sc.nextLine());
                    fyp.modifyTitle(projectID, newTitle);}
                    else{
                        System.out.println("You do not have a project with that Project ID");
                    }
                    break;

                case 4:
                    // view Projects according to filters
                    System.out.println("Select filter \n1. My Own Projects\n2. By Status\n3. By Supervisor ID\n4. By Student ID\n5. View All Projects");
                    int filter = Input_cleaner.int_only(sc.nextLine());
                    switch (filter) {
                        case 1:
                            // view own projects
                            fyp.viewOwnProjects();
                            break;
                        case 2:
                            // view projects by status
                            System.out.println("1. Available\n2. Reserved\n3. Allocated\n4. Unavailable");
                            int status = Input_cleaner.int_only(sc.nextLine());
                            if (status == 1) {
                                fyp.viewFilteredProjsByType(ProjectState.Available);
                            } else if (status == 2) {
                                fyp.viewFilteredProjsByType(ProjectState.Reserved);
                            } else if (status == 3) {
                                fyp.viewFilteredProjsByType(ProjectState.Allocated);
                            } else if (status == 4) {
                                fyp.viewFilteredProjsByType(ProjectState.Unavailable);
                            } else {
                                System.out.println("Invalid input");
                            }
                            break;
                        case 3:
                            // view projects by Superviser ID
                            System.out.println("Enter Supervisor ID: ");
                            String superID = Input_cleaner.str_only(sc.nextLine());
                            if (Util.checkValidSuperID(superID)) {
                                fyp.viewFilteredProjsBySuperv(superID);
                            } else {
                                System.out.println("Invalid Supervisor ID entered");
                            }
                            break;
                        case 4:
                            // view projects by Student ID
                            System.out.println("Enter Student ID: ");
                            String studentID = Input_cleaner.alphanumeric(sc.nextLine());
                            if (Util.checkValidStudentID(studentID)) {
                                fyp.viewFilteredProjsByStudent(studentID);
                            } else {
                                System.out.println("Invalid Student ID entered");
                            }
                            break;
                        case 5:
                            // view all projects
                            fyp.viewAllProjects();
                            break;
                    }
                    break;

                case 5:
                    // View pending requests

                    int count = 0; // count to keep track of number of pending requests
                        for (Request r : fyp.getIncomingRequestList()) {
                            if (r.getStatus().equals("Pending")) {
                                r.viewRequest();
                                count++;
                            }
                        }
                        // if no pending request
                        if (count == 0) {
                            System.out.println("You have no pending request");
                        } else {
                            System.out.println("Enter Request ID to handle request (else enter 0)");
                            int response = Input_cleaner.int_only(sc.nextLine());
                            int i;
                            if (response != 0) {
                                for (i = 0; i < RequestDatabase.MasterRequestList.size(); i++) {
                                    if (RequestDatabase.MasterRequestList.get(i).getRequestID() == response) {
                                        fyp.handleRequest(RequestDatabase.MasterRequestList.get(i));
                                        break;
                                    }
                                }
                                if (i == RequestDatabase.MasterRequestList.size()) {
                                    System.out.println("Invalid Request ID entered");
                                }
                            }
                        }

                        break;
                        case 6:
                            for (Request r : RequestDatabase.MasterRequestList) {
                                r.viewRequest();
                            }
                            break;
                        case 7:
                            // request to transfer student
                            System.out.println("Enter Project ID: ");
                            int ProjectID = Input_cleaner.projectID_only(sc.nextLine());
                            if(Util.checkOwnProject(ProjectID, fyp.getID())){
                                System.out.println("Enter Replacement Supervisor ID");
                                String replacementID = Input_cleaner.str_only(sc.nextLine());
                                fyp.reqTrfStudent(ProjectID, replacementID);
                            }
                            else{
                                System.out.println("You do not have a Project with that Project ID");
                            }
                            break;

                        case 8:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
            }
        }

}