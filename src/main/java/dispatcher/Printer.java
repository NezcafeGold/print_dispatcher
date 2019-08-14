package dispatcher;

import document.Document;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class Printer implements Runnable {

    private static BlockingQueue<Document> printQueue = new LinkedBlockingQueue();
    private static ArrayList<Document> printedDocs = new ArrayList<Document>();
    private static AtomicBoolean isDocToPrint = new AtomicBoolean(true);
    private static CountDownLatch cdl = new CountDownLatch(1);
    private static int allPrintTime = 0;

    private int currentPrintTime = 0;
    private boolean isPrindEnd = false;

    public void run() {
        while (isDocToPrint.get()) {
            Document document = printQueue.peek();
            if (document != null) {
                currentPrintTime = document.getPrintTime();
                allPrintTime += currentPrintTime;
                try {
                    Thread.sleep(currentPrintTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s: %s напечатан за %d мс \n", this.getClass().getSimpleName(),
                        document.getName(), document.getPrintTime());
                printedDocs.add(document);
                printQueue.remove(document);
            }
        }
        isPrindEnd = true;
        cdl.countDown();
        System.out.printf("%s: Печать остановлена\n", this.getClass().getSimpleName());
    }

    public BlockingQueue<Document> stopPrintAndGetQueue() {
        this.isDocToPrint.set(false);
        if (!isPrindEnd) {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return printQueue;
    }

    public synchronized void addToQueue(Document document) {
        try {
            printQueue.put(document);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancelPrint(Document document) {
        if (printQueue.contains(document)) {
            printQueue.remove(document);
        }
    }

    public BlockingQueue<Document> getPrintQueue() {
        return printQueue;
    }

    public ArrayList<Document> getPrintedDocs() {
        return printedDocs;
    }

    public int getAllPrintTime() {
        return allPrintTime;
    }
}
