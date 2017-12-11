package entity;

import java.util.ArrayList;

public class Trip {
    private String destination;
    private String checkin;
    private String checkout;
    private int budget;
    private ArrayList<String> hotelStarRating = new ArrayList<>();

    //constructor for the ValidDatesTest
    public Trip(String destination, String checkin, String checkout) {
        this.destination = destination;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public Trip() {
    }

    public Trip(String destination, int budget) {
        this.destination = destination;
        this.budget = budget;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<String> getHotelStarRating() {
        return hotelStarRating;
    }

    public void setHotelStarRating(ArrayList<String> hotelStarRating) {
        this.hotelStarRating = hotelStarRating;
    }
}
