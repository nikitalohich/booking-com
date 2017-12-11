package entity;

public class Card {
    private String cardType;
    private String cardNumber;
    private String expirationDateMonth;
    private String expirationDateYear;
    private String cvc;

    public Card() {

    }

    public Card(String cardType, String cardNumber, String expirationDateMonth, String expirationDateYear, String cvc) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expirationDateMonth = expirationDateMonth;
        this.expirationDateYear = expirationDateYear;
        this.cvc = cvc;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDateMonth() {
        return expirationDateMonth;
    }

    public void setExpirationDateMonth(String expirationDateMonth) {
        this.expirationDateMonth = expirationDateMonth;
    }

    public String getExpirationDateYear() {
        return expirationDateYear;
    }

    public void setExpirationDateYear(String expirationDateYear) {
        this.expirationDateYear = expirationDateYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Override
    public String toString() {
        return cardType + " " + cardNumber + " " + " exp: " + expirationDateMonth + "/" + expirationDateYear;
    }
}
