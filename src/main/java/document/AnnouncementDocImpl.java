package document;

import document.type.PageSize;

public class AnnouncementDocImpl implements Document {

    private final int printTime = 2000;
    private final String name = "Объявление";
    private final PageSize pageSize = PageSize.A3;

    public int getPrintTime() {
        return printTime;
    }

    public String getName() {
        return name;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

}
