package People;

import Checkers.Util;
import Enums.ProjectState;
import Enums.RequestType;
import IndividualClasses.*;
import MasterLists.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Supervisor extends User {
    private String supvID;
    private int no_Of_Proj = 0;
    private int incomingRequest = 0;
    protected int viewedRequest = 0;
    private ArrayList<Request> IncomingRequestList = new ArrayList<Request>();
    private ArrayList<Request> OutgoingRequestList = new ArrayList<Request>();


    public Supervisor(String name, String userID, String password) {
        super(name, userID, password);
        setEmail(userID + "@ntu.edu.sg");
        this.supvID = userID;
    }


    public void createProject(String projectTitle) {
        // create new project and append to project list
        Project newProject = new Project((ProjectDatabase.projectCount + 1), getName(), projectTitle);
        ProjectDatabase.ProjectList.add(newProject);
        ProjectDatabase.projectCount++;


    }

    // supervisor can modify title of his own project
    public void modifyTitle(int ProjectID, String newTitle) {
        ProjectDatabase.ProjectList.get(ProjectID - 1).setTitle(newTitle);
        System.out.println("Project Title changed! You can view it under 'View Projects'");
    }

    // supervisor can view his own projects
    public void viewOwnProjects() {
        int count=0;
        for (Project p: ProjectDatabase.ProjectList) {
            if (this.supvID.equals(p.getSuperID())) {
                p.displayProjInfo();
                count++;
            }}
        if(count==0){
            System.out.println("You have no current projects");
        }
    }


    // Supervisor request transfer of student
    public void reqTrfStudent(int ProjectID, String replacementSVid) {

        // first check if project is allocated
        if(!ProjectDatabase.ProjectList.get(ProjectID-1).getStatus().equals(ProjectState.Allocated)){
            System.out.println("Project has yet to be allocated");
            return;
        }

        Supervisor replacement = Util.isSupervisor(replacementSVid);
        if(replacement != null){
            if (replacement.getProjCount() >= 2) {
                System.out.printf("Supervisor %s can no longer supervise more projects!\n", replacementSVid);
            }
            else {
                Request newRequest = new Request(this.supvID, ProjectID, replacementSVid);
                OutgoingRequestList.add(newRequest);
                RequestDatabase.MasterRequestList.add(newRequest);
                RequestDatabase.newRequestCreated(newRequest);
                System.out.println("Your request for student transfer has been sent, please await for approval");
            }
        }
        else{
            System.out.println("Invalid Replacement Supervisor ID entered");
        }
    }

    public void handleRequest(Request r){
        Scanner sc=new Scanner(System.in);
        System.out.println("Do you accept this request (Y/y) or (N/n): ");
        String choice = String.valueOf(sc.next().charAt(0));

        // Request #1: Title Change
        if(choice.equals("Y") || choice.equals("y")){
            int ProjectID = r.getProjectID();

            // change project's attribute
            modifyTitle(ProjectID,  r.getTitle());
            System.out.println("You have accepted this request");
            r.setStatus("Approved");
            this.viewedRequest++;
        }

        else if(choice.equals("N") || choice.equals("n")){
            r.setStatus("Declined");
            System.out.println("You have declined this request");
            this.viewedRequest++;}
        else{
            System.out.println("Invalid choice");
        }
        this.viewedRequest++;
    }

    //for incoming requests
    public void appendRequest(Request r) {
        this.IncomingRequestList.add(r);
        // only add incoming request number when request status is pending
        if(r.getStatus().equals("Pending")){
            this.incomingRequest++;
        }
    }

    public void viewOutgoingRequest() {
        for (Request i : this.OutgoingRequestList) {
            i.viewRequest();
        }
    }


    // getters and setters + makeProjectAvail/Unavail
    public String getSuperID(){return this.supvID;}

    public int getProjCount(){return this.no_Of_Proj;}

    public int getIncomingRequestNo(){return this.incomingRequest;}

    public int getViewedRequestNo(){return this.viewedRequest;}

    public ArrayList<Request> getIncomingRequestList(){return this.IncomingRequestList;}

    public void incrementProjCount() {
        this.no_Of_Proj++;
        if (this.no_Of_Proj == 2) {
            makeOtherProjUnavail();
        }
    }

    public void decrementProjCount(){
        if(this.no_Of_Proj ==2){
            makeOtherProjAvail();}
        this.no_Of_Proj--;

    }

    //make other projs unavailable when supervisedProj == 2
    void makeOtherProjUnavail(){
        if(this.no_Of_Proj==2){
            for(Project i : ProjectDatabase.ProjectList){
                //only set available projs under that superV name to unavailable//
                if(i.getSuperID() == this.supvID && i.getStatus() == ProjectState.Available){
                    i.setStatus(ProjectState.Unavailable);

                }
            }
        }}

    void makeOtherProjAvail (){
        for(Project i : ProjectDatabase.ProjectList){
            //only set Unavailable projs under that superV name to Available//
            if(this.supvID.equals(i.getSuperID()) && i.getStatus() == ProjectState.Unavailable){
                i.setStatus(ProjectState.Available);
            }
        }
    }
}



