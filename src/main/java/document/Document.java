package document;

import document.type.PageSize;

public interface Document {
    int getPrintTime();

    String getName();

    PageSize getPageSize();

}
