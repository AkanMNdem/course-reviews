package edu.virginia.cs.hw7;

import java.sql.ResultSet;

public class Course {
//    private int ID;
    private String department;
    private String catalog_number;

    public Course(String department, String catalog_number){
//        this.ID= id;
        this.department=department;
        this.catalog_number=catalog_number;
    }
//    public int getID() {
//        return ID;
//    }
////
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCatalog_number() {
        return catalog_number;
    }

    public void setCatalog_number(String catalog_number) {
        this.catalog_number = catalog_number;
    }
}
