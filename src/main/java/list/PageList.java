package list;

import page.AbstractPage;

import java.util.ArrayList;

public class PageList {
    private static volatile PageList instance;
    private ArrayList<AbstractPage> pages;

    private PageList() {
        this.pages = new ArrayList<>();
    }

    public static PageList getInstance() {
        PageList localInstance = instance;
        if (localInstance == null) {
            synchronized (PageList.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PageList();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<AbstractPage> getPages() {
        return pages;
    }
}
