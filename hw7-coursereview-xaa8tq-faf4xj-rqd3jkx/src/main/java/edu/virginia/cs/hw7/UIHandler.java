package edu.virginia.cs.hw7;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class UIHandler{
    Student student;
    Course course;
    Review review;
    public ReviewsDatabaseManager Manager = new ReviewsDatabaseManager();
    public boolean loggedIn;
    Scanner scanner = new Scanner(System.in);
    public void display(){
        while(true){
            System.out.println("Please select an option by providing the options number:");
            System.out.println("1. Login to existing account.");
            System.out.println("2. Create new account.");
            int option = scanner.nextInt();

            if (option ==1){
                if(login()){
                    main();
                }
            } else if (option==2){
                addNewAccount();
            } else{
                System.out.println("Invalid response. Try again.");
            }
        }
    }
    private void main(){
        while (true){
            System.out.println("Please select an option below by typing in the number that corresponds to that option");
            System.out.println("1. Submit a review for a specified course.");
            System.out.println("2. Look at the reviews for a course.");
            System.out.println("3. Log out.");
            int choice = scanner.nextInt();

            if (choice == 1){
                createReview();
            } else if (choice ==2){
                retrieveReviews();
            } else if (choice == 3){
                return;
            } else{
                System.out.println("Invalid response. Try again.");
            }
        }
    }

    private void retrieveReviews() {
        scanner.nextLine();
        System.out.println("Enter a course name in the format: BIOL 2200");
        String courseDeptPlusName = scanner.nextLine();
        if (courseDeptPlusName.contains(" ")) {
            String[] splitInput = courseDeptPlusName.split(" ");
            if (splitInput[1].length() > 0 && splitInput[1].length() < 5) {
                if(Manager.courseExists(splitInput[0], splitInput[1])){
                    int ratingsSum = 0;
                    double ratingsCount = 0.0;
                    List<Review> reviewList = Manager.getReviewsForCourse(splitInput[1]);
                    for(int i = 0; i<reviewList.size();i++){
                        System.out.println(reviewList.get(i).getText());
                        ratingsSum += reviewList.get(i).getRating();
                        ratingsCount++;
                    }
                    double averageRating = ratingsSum/ratingsCount;
                    System.out.println(averageRating);
                }
            }
            else {
                System.out.println("Invalid department given. Try again.");
            }
        }
        else {
            System.out.println("No department / invalid department given. Try again");
        }
    }

    public void createReview(){
        scanner.nextLine();
        System.out.println("Enter a course name in the format: BIOL 2200");
        String courseDeptPlusName = scanner.nextLine();
        if (courseDeptPlusName.contains(" ")) {
            String[] splitInput = courseDeptPlusName.split(" ");
            if (splitInput[1].length() > 0 && splitInput[1].length() < 5) {
                Course course = new Course(splitInput[0], splitInput[1]);
                if(!Manager.courseExists(splitInput[0], splitInput[1])){
                    Manager.addCourse(course);
                }
                System.out.println("Enter a review: ");
                String reviewText = scanner.nextLine();
                boolean validInt = false;
                int rating=1;
                while(!validInt) {
                    System.out.println("Enter a rating 1-5: ");
                    rating = scanner.nextInt();
                    if(rating <6 && rating> 0){
                        validInt = true;
                    }
                }
                Review review = new Review(reviewText, rating);
                Manager.addReview(review, student, course);
            } else {
                System.out.println("Invalid department given. Try again.");
            }
        } else {
            System.out.println("No department / invalid department given. Try again");
        }


    }
    private void addNewAccount(){
        scanner.nextLine();
        System.out.println("Enter username: ");
        String username= scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        Student student = new Student(username, password);
        Manager.addStudent(student);
    }
    private boolean login(){
        scanner.nextLine();
        System.out.println("Enter username: ");
        String username= scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        if (!Manager.studentExists(username,password)){
            System.out.println("Invalid username or password. Try again.");
            return false;
        }
        student = Manager.getStudentbyUsername(username);
        return true;
    }


}
