package edu.virginia.cs.hw7;
import java.sql.SQLException;
import java.util.Scanner;

//start, createUser, login, submitReview
public class Main {


    public static void main(String[] args){
        UIHandler uiHandler = new UIHandler();
        try {
            uiHandler.Manager.connect();
        }
        catch(SQLException e){
            throw new RuntimeException();
        }
        uiHandler.display();
    }



}
