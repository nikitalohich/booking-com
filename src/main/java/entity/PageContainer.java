package entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import page.AbstractPage;
import page.MainPage;

public class PageContainer {
    static final Logger logger = LogManager.getLogger(PageContainer.class);
    private AbstractPage currentPage = new MainPage();
    private static PageContainer instance = new PageContainer();

    private PageContainer(){

    }

    public void updatePage(AbstractPage newPage) {
        currentPage = newPage;
        logger.log(Level.INFO, currentPage.getClass() + " updated");
    }

    public AbstractPage getPage() {
        logger.log(Level.INFO, currentPage.getClass() + " get");
        return currentPage;
    }

    public static PageContainer getInstance(){
        return instance;
    }
}
