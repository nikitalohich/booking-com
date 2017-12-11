package page.strategy;

import entity.User;
import page.ConfirmReservationPage;

public class ReservationWithoutCardImpl implements FieldStrategy {

    @Override
    public ConfirmReservationPage fillAllField(ConfirmReservationPage page, User user) {
        logger.info("Reservation without card");
        return page.setDataInAllFields(user);
    }
}
