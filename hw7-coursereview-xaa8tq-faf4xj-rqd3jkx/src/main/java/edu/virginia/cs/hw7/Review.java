package edu.virginia.cs.hw7;

public class Review {
    private int id;
    private String text;
    private int rating;

    public Review(String text, int rating){
        this.id=id;
        this.text=text;
        this.rating=rating;
    }

    public int getId() {
        return id;
    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 5 && rating > 1){
         this.rating = rating;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
