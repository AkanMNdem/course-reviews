package edu.virginia.cs.hw7;

public class Student {
//    private int ID;
    private String username;
    private String password;

    public Student (String username, String password){
//        this.ID=id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
//    public int getID() {
//        return ID;
//    }
//    public void setID(int ID) {
//        this.ID = ID;
//    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
