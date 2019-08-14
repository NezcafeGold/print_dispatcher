package document;

import document.type.PageSize;

public class ReportDocImpl implements Document {

    private final int printTime = 1000;
    private final String name = "Приказ";
    private final PageSize pageSize = PageSize.A4;

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
