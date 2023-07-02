package IndividualClasses;
import Enums.RequestType;


public class Request{

    private static int requestcount=1;
    private int requestID;
    private RequestType requestType;
    private String Status = "Pending"; // Pending, Approved, Declined
    private String StudentID;
    private int ProjectID;
    private String newTitle;
    private String superID;
    private String replacementSuperID;

// 4 types of constructor for 4 types of requests

    // Request Register Project
    public Request(String StudentID, int ProjectID, RequestType type){
        this.StudentID = StudentID;
        this.ProjectID = ProjectID;
        this.requestType=type;
        this.requestID=Request.requestcount;
        requestcount++;
    }

    // Request Deregister Project
    public Request(String StudentID, int ProjectID){
        this.StudentID = StudentID;
        this.ProjectID= ProjectID;
        this.requestType=RequestType.ProjectDeregistration;
        this.requestID=requestcount;
        requestcount++;
    }

    // Request Title Change
    public Request(int ProjectID, String newTitle, String studentID){
        this.ProjectID=ProjectID;
        this.newTitle=newTitle;
        this.requestType=RequestType.TitleChange;
        this.StudentID = studentID;
        this.requestID=requestcount;
        requestcount++;
    }

    // Request Student Transfer
    public Request(String superID, int ProjectID, String replaceSuperID){
        this.superID=superID;
        this.ProjectID=ProjectID;
        this.replacementSuperID=replaceSuperID;
        this.requestType=RequestType.TransferStudent;
        this.requestID=requestcount;
        requestcount++;
    }

    // view requests
    public void viewRequest(){
        System.out.printf("Request ID  \tRequest Type \t\t   Status\t\t Description\n");
        System.out.printf("%d\t\t\t  %s\t\t%s\t\t", this.requestID, this.requestType, this.Status);

        if(this.requestType==RequestType.ProjectRegistration){
            System.out.printf("Student %s requests registration for Project %d\n", this.StudentID, this.ProjectID);
        }

        else if(this.requestType==RequestType.ProjectDeregistration){
            System.out.printf("Student %s requests deregistration for Project %d\n", this.StudentID, this.ProjectID);
        }

        else if(this.requestType==RequestType.TitleChange){
            System.out.printf("Student %s request title change for Project %d to %s\n", this.StudentID, this.ProjectID, this.newTitle);
        }

        else if(this.requestType == RequestType.TransferStudent){
            System.out.printf("Supervisor %s requests transfer of Project %d to Replacement Supervisor %s", this.superID, this.ProjectID, this.replacementSuperID);
        }

        System.out.println("\n");
    }

    // to convert into string and store into request.csv
    public String toArray(RequestType type){
        if(type==RequestType.ProjectRegistration || type==RequestType.ProjectDeregistration){
            return type +","+this.StudentID+","+ this.ProjectID+","+this.Status;
        }
        else if(type==RequestType.TitleChange){
            return type + ","+ this.StudentID+","+this.ProjectID+","+this.newTitle+","+this.Status;
        }
        else{
            return type+","+this.superID+","+this.ProjectID+","+this.replacementSuperID+","+this.Status;
        }


    }

    // setters and getters
    public void setStatus(String status){this.Status=status;}

    public int getProjectID(){return this.ProjectID;}

    public int getRequestID(){return this.requestID;}

    public RequestType getRequestType(){return this.requestType;}

    public String getStudentID(){return this.StudentID;}

    public String getTitle(){return this.newTitle;}

    public String getStatus(){return this.Status;}

    public String getSuperID(){return this.superID;}

    public String getReplacementSuperID(){return this.replacementSuperID;}
}

