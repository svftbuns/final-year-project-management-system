package People;

import Checkers.Util;
import Enums.ProjectState;
import Enums.RequestType;
import IndividualClasses.Project;
import IndividualClasses.Request;
import MasterLists.*;


import java.util.ArrayList;

public class Student extends User {
    private String StudentID;
    private boolean isRegistered = false;

    private boolean currRegistering = false;
    private int projectID;
    private ArrayList<Request> RequestHistory= new ArrayList<Request>();

    public Student(String name, String userID, String password){
        super(name, userID, password);
        this.StudentID=userID;
    }

    public void viewAvailableProjects(){
        //if isRegistered false, allow student to view available projects//
        if(isRegistered == false){
            for(Project p: ProjectDatabase.ProjectList){
                // double check if supervisor already reached cap of 2
                Util.checkSupervisorCap(p);
                if(p.getStatus() == ProjectState.Available){
                    p.displayProjInfo();
                }
            }
        }
        else{
            System.out.println("You are currently allocated to FYP and do not have access to available project list.");
        }
    }


    // Student request register project
    public void requestProject(int projectID){
        // check if student has deregistered that project before
        for(Request i : RequestDatabase.MasterRequestList){
            if(this.StudentID==i.getStudentID()&&projectID == i.getProjectID() && i.getRequestType() == RequestType.ProjectDeregistration){
                System.out.println("You are not allowed to make selection again as you deregistered your FYP");
                return;
            }
        }
        // check if student has already registered for another project
        if(this.currRegistering){
                System.out.println("You can only request for one project at a time");
                return;
            }
        // check for valid Project ID
        if (projectID > ProjectDatabase.projectCount || projectID <= 0) {
            System.out.println("No project found with that ID.");
            return;
        }

        // check if Project is available
        if(ProjectDatabase.ProjectList.get(projectID-1).getStatus()!=ProjectState.Available){
            System.out.println("This project is not available for registration");
            return;

        }
        // create a Request object
        Request newRequest= new Request(this.StudentID, projectID, RequestType.ProjectRegistration);
        // append to Student's Request List
        RequestHistory.add(newRequest);
        RequestDatabase.MasterRequestList.add(newRequest);
        RequestDatabase.newRequestCreated(newRequest);

        // set project status to reserved
        ProjectDatabase.ProjectList.get(projectID-1).setStatus(ProjectState.Reserved);
        System.out.printf("Thank you for indicating your interest to register project %d, please await for allocation results\n", projectID);
        this.currRegistering=true;
    }

    // Student request deregister project
    public void reqDeregister(){
        // create a Request object
        Request newRequest= new Request(this.StudentID, this.projectID);

        // append to Student's Request List
        RequestHistory.add(newRequest);
        RequestDatabase.MasterRequestList.add(newRequest);
        RequestDatabase.newRequestCreated(newRequest);
        this.currRegistering=false;
    }

    // Student request title change
    public void reqTitleChg(String newTitle){
        // create a Request object
        Request newRequest= new Request(this.projectID, newTitle, this.StudentID);

        // append to Student's Request List
        RequestHistory.add(newRequest);
        RequestDatabase.MasterRequestList.add(newRequest);
        RequestDatabase.newRequestCreated(newRequest);
    }


    public void viewOwnProject(){
        // student can view his project after registration
        if(this.isRegistered == false){
            System.out.println("You do not have a project registered");
        }
        else{
            ProjectDatabase.ProjectList.get(this.projectID - 1).displayProjInfo();
        }
    }

    public void viewRequestHistory() {
        for (Request i : RequestDatabase.MasterRequestList) {
            if (i.getStudentID().equals(this.StudentID)) {
                i.viewRequest();
            }
        }
    }


    // setters and getters
    public void setProjectID(int projectID){this.projectID=projectID;}

    public void setIsRegistered(boolean registered){this.isRegistered=registered;}

    public void setCurrRegistering(boolean curr){this.currRegistering=curr;}

    public String getStudentID(){return this.StudentID;}

    public boolean getIsRegistered(){return this.isRegistered;}

    public boolean getCurrRegistering(){return this.currRegistering;}
}

