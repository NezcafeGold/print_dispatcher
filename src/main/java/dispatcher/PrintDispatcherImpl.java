package dispatcher;

import document.Document;
import document.type.SortType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrintDispatcherImpl implements PrintDispatcher {

    private Printer printer;

    public PrintDispatcherImpl(Printer printer) {
        this.printer = printer;
    }

    public List<Document> stopAll() {
        ArrayList<Document> notPrintedDocs = new ArrayList<Document>(printer.stopPrintAndGetQueue());
        return notPrintedDocs;
    }

    public void submitToPrint(Document document) {
        printer.addToQueue(document);
    }

    public void cancelOfPrint(Document document) {
        printer.cancelPrint(document);
    }

    public List<Document> getSortedPrintedDocs(SortType sortType) {
        ArrayList<Document> printedDocs = new ArrayList<Document>(printer.getPrintedDocs());
        switch (sortType) {
            case BY_NAME:
                Collections.sort(printedDocs, new Comparator<Document>() {
                    public int compare(Document u1, Document u2) {
                        return u1.getName().compareTo(u2.getName());
                    }
                });
                break;
            case BY_PAGE_SIZE:
                Collections.sort(printedDocs, new Comparator<Document>() {
                    public int compare(Document u1, Document u2) {
                        return u1.getPageSize().compareTo(u2.getPageSize());
                    }
                });
                break;
            case BY_PRINT_TIME:
                Collections.sort(printedDocs, new Comparator<Document>() {
                    public int compare(Document u1, Document u2) {
                        return ((Integer) u1.getPrintTime()).compareTo(u2.getPrintTime());
                    }
                });
                break;
        }
        return printedDocs;
    }

    public double getAveragePrintTime() {
        return printer.getAllPrintTime() / printer.getPrintedDocs().size();
    }
}
