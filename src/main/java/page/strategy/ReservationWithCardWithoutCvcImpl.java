package page.strategy;

import entity.User;
import page.ConfirmReservationPage;
import page.ConfirmReservationWithCardWithoutCvcPage;

public class ReservationWithCardWithoutCvcImpl implements FieldStrategy {

    @Override
    public ConfirmReservationPage fillAllField(ConfirmReservationPage page, User user) {
        logger.info("Reservation with card without cvc");
        return new ConfirmReservationWithCardWithoutCvcPage().setDataInAllFields(user);
    }
}
