package page.strategy;

import entity.User;
import page.ConfirmReservationPage;
import page.ConfirmReservationWithCardPage;

public class ReservationWithCardImpl implements FieldStrategy {

    @Override
    public ConfirmReservationPage fillAllField(ConfirmReservationPage page, User user) {
        logger.info("Reservation with card without cvc and address");
        return new ConfirmReservationWithCardPage().setDataInAllFields(user);
    }
}
