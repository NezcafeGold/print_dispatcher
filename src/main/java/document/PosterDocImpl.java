package document;

import document.type.PageSize;

public class PosterDocImpl implements Document {

    private final int printTime = 4000;
    private final String name = "Плакат";
    private final PageSize pageSize = PageSize.A2;

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
