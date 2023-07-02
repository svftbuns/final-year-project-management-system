package People;

import Checkers.Util;
import Enums.*;
import IndividualClasses.*;
import MasterLists.*;


import java.util.Scanner;
;

public class FYPCoordinator extends Supervisor {

    public FYPCoordinator(String name, String userID, String password) {

        super(name, userID, password);
    }

    //view all projs regardless of status//
    public void viewAllProjects() {
        for (int i = 0; i < ProjectDatabase.projectCount; i++) {
            ProjectDatabase.ProjectList.get(i).displayProjInfo();
        }
    }

    public void viewFilteredProjsByType(ProjectState type) {
        int count=0;
        for(Project p : ProjectDatabase.ProjectList){
                if (p.getStatus() == type) {
                   p.displayProjInfo();
                   count++;
                }
            }
        if(count==0){
            System.out.println("There are no "+ type+ " projects");
        }
    }


    public void viewFilteredProjsBySuperv(String supervID) {
        int count = 0;
        for (int i = 0; i < ProjectDatabase.projectCount; i++) {
            if (supervID.equals(ProjectDatabase.ProjectList.get(i).getSuperID())) {
                System.out.println("Project ID: " + ProjectDatabase.ProjectList.get(i).getProjectID());
                System.out.println("Supervisor Name: " + ProjectDatabase.ProjectList.get(i).getSuperName());
                System.out.println("Supervisor Email address: " + ProjectDatabase.ProjectList.get(i).getSuperID() + "@ntu.edu.sg");
                System.out.println("Student Name: " + ProjectDatabase.ProjectList.get(i).getStudentName());
                System.out.println("Student Email address: " + ProjectDatabase.ProjectList.get(i).getStudentID() + "@e.ntu.edu.sg");
                System.out.println("Project Title: " + ProjectDatabase.ProjectList.get(i).getTitle());
                System.out.println("Project Status" + ProjectDatabase.ProjectList.get(i).getStatus() + "\n");
                count++;
            }
        }
        if (count == 0) {
            System.out.printf("Supervisor %s does not have any projects\n", supervID);
        }
    }

    public void viewFilteredProjsByStudent(String studentID) {
        for (int i = 0; i < ProjectDatabase.projectCount; i++) {
            if (studentID.equals(ProjectDatabase.ProjectList.get(i).getStudentID())) {
                System.out.println("Project ID: " + ProjectDatabase.ProjectList.get(i).getProjectID());
                System.out.println("Supervisor Name: " + ProjectDatabase.ProjectList.get(i).getSuperName());
                System.out.println("Supervisor Email address: " + ProjectDatabase.ProjectList.get(i).getSuperID() + "@ntu.edu.sg");
                System.out.println("Student Name: " + ProjectDatabase.ProjectList.get(i).getStudentName());
                System.out.println("Student Email address: " + ProjectDatabase.ProjectList.get(i).getStudentID() + "@e.ntu.edu.sg");
                System.out.println("Project Title: " + ProjectDatabase.ProjectList.get(i).getTitle());
                System.out.println("Project Status" + ProjectDatabase.ProjectList.get(i).getStatus()+"\n");
                return;
            }
        }
        System.out.println("Student has not been assigned a project.");
    }


    // override supervisor's handleRequest method
    public void handleRequest(Request r) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you accept this request (Y/y) or (N/n): ");
        String choice = String.valueOf(sc.next().charAt(0));

        // Request #1 : Project Registration
        if (r.getRequestType() == RequestType.ProjectRegistration) {
            // check if supervisor already has 2 projects, if yes, immediately decline request
            String StudentID = r.getStudentID();
            Student student = Util.isStudent(StudentID);
            int ProjectID = r.getProjectID();
            String superID = ProjectDatabase.ProjectList.get(ProjectID - 1).getSuperID();
            Supervisor supervisor = Util.isSupervisor(superID);
            FYPCoordinator fyp = Util.isFYP(superID);
            if((supervisor!=null && supervisor.getProjCount()==2) || (fyp!=null && fyp.getProjCount()==2)){
                System.out.printf("Supervisor %s already has two projects, hence your request cannot be approved and is automatically declined\n", ProjectDatabase.ProjectList.get(ProjectID - 1).getSuperName());
                r.setStatus("Declined");
                student.setCurrRegistering(false);
                return;
            }

            if (choice.equals("Y") || choice.equals("y")) {

                // change student's attributes
                student.setIsRegistered(true);
                student.setProjectID(ProjectID);

                // change project's attributes
                ProjectDatabase.ProjectList.get(ProjectID - 1).register(student);

                // change supervisor's/FYPCoordinator's attributes
                if(supervisor!=null){
                supervisor.incrementProjCount();}
                else{
                    incrementProjCount();
                }
                this.viewedRequest++;


                r.setStatus("Approved");
                System.out.println("You have accepted this request");
            }else if(choice.equals("N") || choice.equals("n")) {
                r.setStatus("Declined");
                // if declined, set project status to available
                ProjectDatabase.ProjectList.get(ProjectID - 1).setStatus(ProjectState.Available);
                student.setCurrRegistering(false);
                System.out.println("You have declined this request");
                this.viewedRequest++;
            }
            else{
                System.out.println("Invalid choice");
            }
        }


        // Request #2: Project Deregistration
        if (r.getRequestType() == RequestType.ProjectDeregistration) {
            String StudentID = r.getStudentID();
            int ProjectID = r.getProjectID();
            Student student = Util.isStudent(StudentID);
            if (choice.equals("Y") || choice.equals("y")) {

                // change student's attributes
                student.setIsRegistered(false);
                student.setCurrRegistering(false);
                student.setProjectID(0);

                // change project's attributes
                ProjectDatabase.ProjectList.get(ProjectID - 1).deregister();

                // change supervisor's attributes
                Supervisor supervisor = Util.isSupervisor(ProjectDatabase.ProjectList.get(ProjectID - 1).getSuperID());

                if (supervisor != null) {
                    supervisor.decrementProjCount();
                }
                else{
                    // it's FYPCoordinator project!
                    decrementProjCount();
                }
                r.setStatus("Approved");
                this.viewedRequest++;
                System.out.println("You have accepted this request");
            } else if(choice.equals("N") || choice.equals("n")) {
                r.setStatus("Declined");
                System.out.println("You have declined this request");
                this.viewedRequest++;
            }
            else{
                System.out.println("Invalid choice");
            }
        }


        // Request #3: Transfer of Student
        if (r.getRequestType() == RequestType.TransferStudent) {
            if (choice.equals("Y") || choice.equals("y")) {
                String superID = r.getSuperID();
                String replaceID = r.getReplacementSuperID();
                int ProjectID = r.getProjectID();

                Supervisor current = Util.isSupervisor(superID);
                Supervisor replace = Util.isSupervisor(replaceID);

                // update current supervisor's attribute
                current.decrementProjCount();



                // update new supervisor's attribute
                replace.incrementProjCount();

                // update project's attribute
                ProjectDatabase.ProjectList.get(ProjectID - 1).setSupervisorID(replaceID);
                ProjectDatabase.ProjectList.get(ProjectID - 1).setSuperName(replace.getName());
                r.setStatus("Approved");
                this.viewedRequest++;

                System.out.println("You have accepted this request");
            } else if (choice.equals("N") || choice.equals("n")){
                r.setStatus("Declined");
               System.out.println("You have declined this request");
               this.viewedRequest++;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
    }


    // override Request Transfer Student because FYP can do it herself
    public void reqTrfStudent(int ProjectID, String replacementSVid) {
        Supervisor replacement = Util.isSupervisor(replacementSVid);
        if (replacement != null) {
            if (replacement.getProjCount() >= 2) {
                System.out.printf("Supervisor %s can no longer supervise more projects!\n", replacementSVid);
                return;
            }

            // update FYP's attribute
            // update new supervisor's attribute
            replacement.incrementProjCount();
            decrementProjCount();

            // update project's attribute
            ProjectDatabase.ProjectList.get(ProjectID - 1).setSupervisorID(replacement.getSuperID());
            ProjectDatabase.ProjectList.get(ProjectID - 1).setSuperName(replacement.getName());

            System.out.println("Project Transfer Successful!");
        }
        else{
            System.out.println("Invalid Supervisor ID entered");
        }
    }
}
