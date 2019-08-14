package dispatcher;

import document.Document;
import document.type.SortType;

import java.util.List;

public interface PrintDispatcher {

    List<Document> stopAll();

    void submitToPrint(Document document);

    void cancelOfPrint(Document document);

    List<Document> getSortedPrintedDocs(SortType sortType);

    double getAveragePrintTime();

}
