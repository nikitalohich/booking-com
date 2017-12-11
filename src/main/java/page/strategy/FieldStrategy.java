package page.strategy;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import page.ConfirmReservationPage;

public interface FieldStrategy {
    Logger logger = LogManager.getLogger(FieldStrategy.class);

    ConfirmReservationPage fillAllField(ConfirmReservationPage page, User user);
}
