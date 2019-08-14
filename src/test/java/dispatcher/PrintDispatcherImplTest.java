package dispatcher;

import document.Document;
import document.DocumentSelector;
import document.type.DocType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class PrintDispatcherImplTest {

    private Printer printer = new Printer();
    private PrintDispatcherImpl printDispatcher = new PrintDispatcherImpl(printer);
    private DocumentSelector documentSelector = new DocumentSelector();

    @Test
    void submitToPrint() {
        int startSize = printer.getPrintQueue().size();
        Document actualDocument = documentSelector.getDocument(DocType.ANNOUNCEMENT);
        printDispatcher.submitToPrint(actualDocument);
        Document expectedDocument = printer.getPrintQueue().peek();
        int endSize = printer.getPrintQueue().size();

        Assert.assertEquals(actualDocument, expectedDocument);
        Assert.assertEquals(startSize + 1, endSize);
    }

    @Test
    void cancelOfPrint() {
        ArrayList<Document> startQueue = new ArrayList<Document>(printer.getPrintQueue());
        Document document = documentSelector.getDocument(DocType.ANNOUNCEMENT);
        printer.addToQueue(document);
        ArrayList<Document> docQueue = new ArrayList<Document>(printer.getPrintQueue());
        printDispatcher.cancelOfPrint(document);
        ArrayList<Document> endQueue = new ArrayList<Document>(printer.getPrintQueue());

        Assert.assertEquals(startQueue.size() + 1, docQueue.size());
        Assert.assertEquals(startQueue.size(), endQueue.size());
    }

}