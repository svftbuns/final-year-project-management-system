package IndividualClasses;

import Enums.ProjectState;
import MasterLists.SupervDatabase;
import People.Student;

public class Project{

    private int projectID;
    private ProjectState status = ProjectState.Available;
    private String superID;
    private String superName;
    private String studentID = "";
    private String studentName = "Unassigned";
    private String title;

    // constructor
    public Project(int ID, String superName, String title){
        this.projectID=ID;
        this.superName=superName;
        this.title=title;
        int i;
        for(i=0; i< SupervDatabase.SupervisorList.size(); i++){
            if(SupervDatabase.SupervisorList.get(i).getName().equals(this.superName)){
                this.superID=SupervDatabase.SupervisorList.get(i).getSuperID();
                break;
            }
        }
        // If not found, FYP Coordinator is the creator
        if(i==SupervDatabase.SupervisorList.size()){
        this.superID="ASFLI";}

    }

    public void register(Student student){
        this.status=ProjectState.Allocated;
        setStudentID(student.getStudentID());
        setStudentName(student.getName());
    }

    public void deregister(){
        this.status = ProjectState.Available;
        this.studentID = "";
        this.studentName = "Unassigned";
    }

    public void displayProjInfo(){
        // display different info depending on Available or Allocated
        if (this.status==ProjectState.Available || this.status==ProjectState.Reserved || this.status==ProjectState.Unavailable ){
            System.out.println("Project ID: "+ this.projectID);
            System.out.println("Supervisor Name: " + this.superName);
            System.out.println("Supervisor Email address: " + this.superID + "@ntu.edu.sg");
            System.out.println("Project Title: "+ this.title);
            System.out.println("Project Status: " + this.status);
            System.out.println();
        }
        else if (this.status == ProjectState.Allocated){
            System.out.println("Project ID: "+ this.projectID);
            System.out.println("Supervisor Name: " + this.superName);
            System.out.println("Supervisor Email address: " + this.superID + "@ntu.edu.sg");
            System.out.println("Student Name: "+ this.studentName);
            System.out.println("Student Email address: " + this.studentID + "@e.ntu.edu.sg");
            System.out.println("Project Title: "+ this.title);
            System.out.println("Project Status: " + ProjectState.Allocated);
            System.out.println();
        }
    }



// getters and setters

    void setStudentID(String StudentID){this.studentID=StudentID;}
    void setStudentName(String name){this.studentName=name;}
    public void setTitle(String Title){this.title=Title;}
    public void setSupervisorID(String superID){this.superID=superID;}
    public void setSuperName(String superName){this.superName=superName;}
    public void setStatus(ProjectState projectState) {
        this.status=projectState;
    }

    public String getStudentID(){return this.studentID;}
    public String getSuperID(){return this.superID;}
    public String getSuperName(){return this.superName;}
    public String getTitle(){return this.title;}
    public ProjectState getStatus(){return this.status;}
    public String getStudentName(){return this.studentName;}
    public int getProjectID(){return this.projectID;}


}

