package edu.virginia.cs.hw7;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewsDatabaseManager {
    //public static final String REVIEWS_DATABASE_PATH = "Reviews.sqlite3";

    Connection connection;
    String sql;
//    Student student;
//    Course course;
//    Review review;

    public void connect() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public void createTables() {
        checkConnection();

        throwIfExists("Students");
        sql = "CREATE TABLE Students (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Username VARCHAR NOT NULL, Password VARCHAR NOT NULL)";
        executeUpdateSQL(sql);

        throwIfExists("Courses");
        sql = "CREATE TABLE Courses (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Department VARCHAR NOT NULL, Catalog_Number VARCHAR NOT NULL)";
        executeUpdateSQL(sql);

        throwIfExists("Reviews");
        sql = "CREATE TABLE Reviews (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, StudentID Int NOT NULL, CourseID Int NOT NULL, Review VARCHAR NOT NULL, Rating Int NOT NULL," +
                "FOREIGN KEY (StudentID) REFERENCES Students(ID), " + "FOREIGN KEY (CourseID) REFERENCES Courses(ID))";
        executeUpdateSQL(sql);

    }


    private void checkConnection() {
        if (connection == null){
            throw new IllegalStateException("Manager has not connected");
        }
    }
    public void clear() {
        checkConnection();

        throwIfDoesNotExists("Students");
        sql = "DELETE FROM Students";
        executeUpdateSQL(sql);

        throwIfDoesNotExists("Courses");
        sql = "DELETE FROM Courses";
        executeUpdateSQL(sql);

        throwIfDoesNotExists("Reviews");
        sql = "DELETE FROM Reviews";
        executeUpdateSQL(sql);

    }
    public void deleteTables() {
        checkConnection();
        throwIfDoesNotExists("Students");
        sql = "DROP TABLE Students";
        executeUpdateSQL(sql);
        throwIfDoesNotExists("Courses");
        sql = "DROP TABLE Courses";
        executeUpdateSQL(sql);
//        throwIfDoesNotExists("Reviews");
//        sql = "DROP TABLE Reviews";
//        executeUpdateSQL(sql);
    }
    private void executeUpdateSQL(String sql){
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void throwIfExists(String name){
        try (ResultSet tables = connection.getMetaData().getTables(null, null, name, null)) {
            if (tables.next()) {
                throw new IllegalStateException(name+" table already exists in the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwIfDoesNotExists(String name) {
        try (ResultSet tables = connection.getMetaData().getTables(null, null, name, null)) {
            if (!tables.next()) {
                throw new IllegalStateException(name + " table does not exist in the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudent(Student student){
        checkConnection();
        throwIfDoesNotExists("Students");
        //int id = student.getID();
        String username = student.getUsername();
        String password = student.getPassword();

        if (studentExists(username, password)){
            throw new IllegalStateException("Student has already logged into the System");
        }
        sql = String.format("""
            INSERT INTO STUDENTS ( Username, Password ) values ( "%s", "%s");
            """, username, password);
        executeUpdateSQL(sql);
    }
    public boolean studentExists(String username, String password){
        try(PreparedStatement statement= connection.prepareStatement("SELECT COUNT(*) FROM Students WHERE Username=? AND Password=?")){
            //statement.setInt(1,id);
            statement.setString(1,username);
            statement.setString(2,password);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.getInt(1) >0;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Student getStudentbyUsername(String username){
        checkConnection();
        throwIfDoesNotExists("Students");
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE Username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("Password");
                return new Student(username, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("A Student with username: "+username+ " does not exist");
    }

    public void addCourse(Course course){
        checkConnection();
        throwIfDoesNotExists("Courses");
        //int id = course.getID();
        String department = course.getDepartment();
        String catalog_number = course.getCatalog_number();
        if (courseExists(department, catalog_number)){
            throw new IllegalStateException("Course has already logged into the System");
        }
        sql = String.format("""
            INSERT INTO COURSES ( Department, Catalog_number ) values ( "%s", "%s");
            """, department, catalog_number);
        executeUpdateSQL(sql);
    }

    public boolean courseExists(String department, String catalog_number){
        try(PreparedStatement statement= connection.prepareStatement("SELECT COUNT(*) FROM Courses WHERE Department=? AND Catalog_number=?")){
            //statement.setInt(1,id);
            statement.setString(1,department);
            statement.setString(2,catalog_number);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.getInt(1) >0;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Course getCourseByDepartment(String department){
        checkConnection();
        throwIfDoesNotExists("Courses");
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Courses WHERE Department = " + department)) {
            if (resultSet.next()) {
                //String department = resultSet.getString("Department");
                String  catalog_number = resultSet.getString("Catalog_number");
                return new Course(department, catalog_number);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("A Course of Department: "+department+ " does not exist");
    }

    public void addReview( Review review, Student student, Course course){
        checkConnection();
        throwIfDoesNotExists("Reviews");
        //int id = review.getId();
        String text_message = review.getText();
        int rating = review.getRating();
//        int studentID = student.getID();
//        int courseID = course.getID();
        if (reviewExists(text_message, rating)){
            throw new IllegalStateException("Student has written review for this course");
        }
        sql = String.format("""
            INSERT INTO Reviews (StudentID, CourseID, Review, Rating ) values ( "%d", "%d", "%s", "%s");
            """, getStudentIDfromUser(student.getUsername()), getCourseIDFromCatalogNum(course.getCatalog_number()), text_message, rating);
        executeUpdateSQL(sql);
    }

    public boolean reviewExists(String text_message, int rating){
        try(PreparedStatement statement= connection.prepareStatement("SELECT COUNT(*) FROM Reviews WHERE Review=? AND Rating=?")){
            //statement.setInt(1,id);
            statement.setString(1,text_message);
            statement.setInt(2,rating);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.getInt(1) >0;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int getStudentIDfromUser(String user){
        checkConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE Username = ?")) {
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int studentID = resultSet.getInt("ID");
                return studentID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public Review getReviewByStudentID(String userame){
        checkConnection();
        throwIfDoesNotExists("Reviews");
        int studentID = getStudentIDfromUser(userame);
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Reviews WHERE id = " + studentID)) {
            if (resultSet.next()) {
                String review = resultSet.getString("Review");
                int rating = resultSet.getInt("Rating");
                return new Review(review, rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("A Review with studentID: "+studentID+ " does not exist");
    }
    public int getCourseIDFromCatalogNum(String catalog_number) {
        checkConnection();
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Courses WHERE Catalog_Number = " + catalog_number)) {
            if (resultSet.next()) {
                int courseID = resultSet.getInt("ID");
                return courseID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;

    }

    public List<Review> getReviewsForCourse(String catalog_number){
        checkConnection();
        throwIfDoesNotExists("Students");
        throwIfDoesNotExists("Courses");
        throwIfDoesNotExists("Reviews");
        List<Review> reviewsList = new ArrayList<>();
        int courseID = getCourseIDFromCatalogNum(catalog_number);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Reviews WHERE CourseID= "+courseID)) {
            while (resultSet.next()) {
                //int id = resultSet.getInt("ID");
                String reviewString = resultSet.getString("Review");
                int rating = resultSet.getInt("Rating");
                Review review = new Review(reviewString, rating);
                reviewsList.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviewsList;

    }
    public Review getReviewByStudentId(int id){
        checkConnection();
        throwIfDoesNotExists("Reviews");
        throwIfDoesNotExists("Students");
        throwIfDoesNotExists("Courses");
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Reviews WHERE StudentID = " + id)) {
            if (resultSet.next()) {
                String review = resultSet.getString("Review");
                int rating = resultSet.getInt("Rating");
                return new Review(review, rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("A student's review with ID: "+id+ " does not exist");
    }






    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException(" A connection has not been made yet.");
        }
    }


/*
    public static void main(String[] args) throws SQLException {
        ReviewsDatabaseManager test = new ReviewsDatabaseManager();
        Student student = new Student("xaa8tq", "akan1021");
        Course course = new Course("CS","3140");
        Review review = new Review("great class", 5);
        Student student2 = new Student("hello", "bye");
        Review reviewByStudent2 = new Review("love this class", 5);
        Course course2 = new Course("MATH", "2310");
        Student student3 = new Student("Bobert", "Joe");
        Review reviewByStudent3 = new Review("This class was hard.", 3);
        Student student4 = new Student("Joanna", "OOO");
        Course course3 = new Course("SPAN", "1060");
        Review reviewByStudent4 = new Review("The professor gives a lot of work.", 2);
        test.connect();
        //test.deleteTables();
        //test.createTables();
        //test.clear();
        //test.disconnect();

        test.addStudent(student);
        test.addStudent(student3);

        test.addCourse(course);
        test.addCourse(course2);
        test.addCourse(course3);
        test.addReview(review, student, course);
        test.addStudent(student2);
        test.addStudent(student4);
        test.addReview(reviewByStudent2, student2, course);
        test.addReview(reviewByStudent3, student3, course2);
        test.addReview(reviewByStudent4, student4, course3);

        //List<Review> reviewslist = test.getReviewsForCourse(course);
//        for ( Review reviewtest: reviewslist){
//            System.out.println(reviewtest.getRating());
//        }


//        System.out.println(test.);




    }

    public void addStudent() {
    }*/
}
