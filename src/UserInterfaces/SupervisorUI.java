package UserInterfaces;

import Enums.RequestType;
import IndividualClasses.Project;
import IndividualClasses.Request;
import MasterLists.ProjectDatabase;
import MasterLists.RequestDatabase;
import People.Supervisor;
import Checkers.*;
import java.util.Scanner;

public class SupervisorUI implements UI {
    Scanner sc = new Scanner(System.in);
    private Supervisor supervisor;

    public SupervisorUI(Supervisor supervisor) {
        this.supervisor = supervisor;
        Util.checkNewRequest(supervisor.getIncomingRequestNo(), supervisor.getViewedRequestNo());

    }

    public void welcomePage() {
        int choice = 0;

        System.out.println("Welcome to NTU's FYPMS! Please select an option: ");
        System.out.println("1. Change Password");
        System.out.println("2. Create Projects");
        System.out.println("3. Update Projects");
        System.out.println("4. View Projects");
        System.out.println("5. View Student Pending Requests");
        System.out.println("6: View Incoming Request History");
        System.out.println("7. View Outgoing Request History");
        System.out.println("8. Request To Transfer Student");
        System.out.println("9. Log Out");


        while (true) {
            System.out.println("Enter your choice: ");
            choice = Input_cleaner.int_only(sc.nextLine());

            switch (choice) {
                case 1:
                    supervisor.changePW(supervisor.getPassword());
                    return;
                case 2:
                    // create new project
                    System.out.println("Enter the name of the project: ");
                    String title = Input_cleaner.alphanumeric(sc.nextLine());
                    supervisor.createProject(title);
                    System.out.println("New project created! You may now view it under 'View Projects'");
                    break;
                case 3:
                    // modify title of project
                    supervisor.viewOwnProjects();
                    System.out.println("Enter Project ID of project whose title you wish to change: ");
                    int projectID = Input_cleaner.projectID_only(sc.nextLine());

                    // check if project is his
                    if(Util.checkOwnProject(projectID, supervisor.getSuperID())) {
                        System.out.println("Enter new title of project: ");
                        String newTitle = Input_cleaner.alphanumeric(sc.nextLine());
                        supervisor.modifyTitle(projectID, newTitle);
                    }

                    else{
                        System.out.println("You do not have a Project with that Project ID");
                    }

                    break;
                case 4:
                    // view own projects
                    supervisor.viewOwnProjects();
                    break;
                case 5:
                    // view Student's Pending Request and handle Student's Request
                    int count = 0; // count to keep track of number of pending requests
                    for (Request r : supervisor.getIncomingRequestList()) {
                        if (r.getStatus().equals("Pending")) {
                            r.viewRequest();
                            count++;
                        }
                    }
                    // if no pending request
                    if (count == 0) {
                        System.out.println("You have no pending request");}
                    else {
                        System.out.println("Enter Student ID to handle request (else enter Q)");
                        String response = Input_cleaner.alphanumeric(sc.nextLine());
                        int i;
                        if (!response.equals("Q")) {
                            for (i = 0; i < RequestDatabase.MasterRequestList.size(); i++) {
                                if (RequestDatabase.MasterRequestList.get(i).getStudentID().equals(response) && RequestDatabase.MasterRequestList.get(i).getRequestType() == RequestType.TitleChange) {
                                    supervisor.handleRequest(RequestDatabase.MasterRequestList.get(i));
                                    break;
                                }
                            }
                            if (i == RequestDatabase.MasterRequestList.size()) {
                                System.out.println("Invalid Student ID entered");
                            }
                        }
                    }
                    break;
                case 6:
                    // view own incoming requests
                    for(Request r : supervisor.getIncomingRequestList()){
                        r.viewRequest();
                    }
                    break;
                case 7:
                    // view own outgoing requests
                    supervisor.viewOutgoingRequest();
                    break;
                case 8:
                    // request to transfer student
                    System.out.println("Enter Project ID: ");
                    int ProjectID = Input_cleaner.projectID_only(sc.nextLine());
                    if(Util.checkOwnProject(ProjectID, supervisor.getSuperID())) {
                        System.out.println("Enter Replacement Supervisor ID");
                        String replacementID = Input_cleaner.str_only(sc.nextLine());
                        supervisor.reqTrfStudent(ProjectID, replacementID);
                    }
                    else{
                        System.out.println("You do not have a project with that Project ID");
                    }
                    break;

                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice entered");

            }

        }
    }

}



