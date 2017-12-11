package entity;

import java.util.ArrayList;

public class UserSingleton {
    private User user = new User();

    private static UserSingleton instance = new UserSingleton();

    private UserSingleton() {
        user.setCard(new Card());
        user.setName("Uladzimir");
        user.setLastName("Zviartouski");
        user.setCountry("Belarus");
        user.setCity("Minsk");
        user.setTitle("Mr");
        user.getCard().setCardNumber("4716888269922824");
        user.getCard().setCardType("Visa");
        user.getCard().setCvc("908");
        user.getCard().setExpirationDateYear("2018");
        user.getCard().setExpirationDateMonth("02");
        user.setAddress("Angerstraße");
        user.setPhone("123456789");
        user.setPassword("adminepam16");
        user.setEmail("sutbookingcom@gmail.com");
        user.setTrip(new Trip());
        user.getTrip().setBudget(300);
        user.getTrip().setCheckin("20");
        user.getTrip().setCheckout("21");
        user.getTrip().setDestination("Warsaw");
        ArrayList<String> list=new ArrayList<>();
        list.add("2");
        list.add("3");
        user.getTrip().setHotelStarRating(list);
    }

    public static UserSingleton getInstance() {
        return instance;
    }
    public User getUser(){
        return user;
    }

}
