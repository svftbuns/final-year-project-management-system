package People;

import java.util.Scanner;

public abstract class User{
    private String name;
    private String userID;
    private String password;
    private String email;

    public User(String name, String userID, String password){
        this.name=name;
        this.userID=userID;
        this.password=password;
        this.email=userID+"@e.ntu.edu.sg";

    }
    Scanner sc = new Scanner(System.in);

    //change pw available for all users in User Class//
    public void changePW(String password){
        System.out.println("Enter your new password: ");
        while(true){
            String newPW = sc.nextLine();
            if(newPW.equals(password)){
                System.out.println("Please enter a different password: ");
            }
            else{
                this.password = newPW;
                return;
            }
        }
    }

    public String getID(){
        return userID;
    }

    public String getName(){return name;}

    public String getPassword(){return this.password;}

    public void setEmail(String email){this.email = email;}
}


