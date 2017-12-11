package page.strategy;

import entity.User;
import page.ConfirmReservationPage;
import page.ConfirmReservationWithCardAndCvcPage;

public class ReservationWithCardCvcImpl implements FieldStrategy {

    @Override
    public ConfirmReservationPage fillAllField(ConfirmReservationPage page, User user) {
        logger.info("Reservation with card and cvc");
        return new ConfirmReservationWithCardAndCvcPage().setDataInAllFields(user);
    }

}
