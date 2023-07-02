package MasterLists;

import Enums.RequestType;
import IndividualClasses.Request;
import People.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class RequestDatabase {
    public static ArrayList <Request> MasterRequestList = new ArrayList <Request>();
    static File requestlist = new File("src//Databases//request_list.csv");
    public RequestDatabase(){
        try {
            Scanner sc = new Scanner(requestlist);
            sc.useDelimiter(",");   //sets the delimiter pattern
            sc.nextLine();
            while (sc.hasNext())  //returns a boolean value
            {
                String[] splitInput = sc.nextLine().split(",", 5);
                RequestType type = RequestType.valueOf(splitInput[0].trim());
                Request newRequest;;
                if(type==RequestType.ProjectRegistration){
                    newRequest = new Request(splitInput[1], Integer.parseInt(splitInput[2]), type);
                    newRequest.setStatus(splitInput[3]);
                }

                else if(type==RequestType.ProjectDeregistration){
                    newRequest = new Request(splitInput[1], Integer.parseInt(splitInput[2]));
                    newRequest.setStatus(splitInput[3]);
                }

                else if(type== RequestType.TitleChange){
                    newRequest = new Request(Integer.parseInt(splitInput[2]), splitInput[3], splitInput[1]);
                    newRequest.setStatus(splitInput[4]);
                }
                else{
                    newRequest = new Request(splitInput[1], Integer.parseInt(splitInput[2]), splitInput[3]);
                    newRequest.setStatus(splitInput[4]);
                }

                MasterRequestList.add(newRequest);
                newRequestCreated(newRequest);
            }
        }

        catch(FileNotFoundException e){
            System.out.println("File not found hehe");
        }


    }

    public static void newRequestCreated(Request r) {
        // append to the relevant personnel's Incoming Request List
        if (r.getRequestType()== RequestType.TitleChange) {
            String supervisorID = ProjectDatabase.ProjectList.get((r.getProjectID()) - 1).getSuperID();
            for (Supervisor i : SupervDatabase.SupervisorList) {
                if (i.getSuperID().equals(supervisorID))
                    i.appendRequest(r);
                }
            }
        // everything else just append to FYPCoor's Request History
        else {
            FYPCoordinatorDatabase.FYPList.get(0).appendRequest(r);
        }
    }

    // update request csv at the end
    public static void updateRequestList(){

        try {
            PrintWriter writer = new PrintWriter(requestlist);

            // first clear all contents
            writer.write("");
            writer.write("Master Request List\n");

            for(Request r : MasterRequestList){
                writer.println(r.toArray(r.getRequestType()));
            }

            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found hehe");
        }


    }
}

