import dispatcher.PrintDispatcherImpl;
import dispatcher.Printer;
import document.Document;
import document.DocumentSelector;
import document.type.DocType;
import document.type.SortType;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunDispatcher {
    public static void main(String[] args) {

        new RunDispatcher().start();

    }

    private void start() {
        Printer printer = new Printer();
        final PrintDispatcherImpl printDispatcher = new PrintDispatcherImpl(printer);
        final DocumentSelector documentSelector = new DocumentSelector();

        //Запуск Принтера
        ExecutorService printerExecutor = Executors.newSingleThreadExecutor();
        printerExecutor.submit(new Printer());

        //Запуск диспетчера. Далее его задачи:
        final ExecutorService dispatcher = Executors.newCachedThreadPool();

        //Принять документ на печать. Метод не должен блокировать выполнение программы.
        dispatcher.submit(new Runnable() {
            public void run() {
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.ANNOUNCEMENT));
            }
        });
        dispatcher.submit(new Runnable() {
            public void run() {
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.REPORT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.REPORT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.REPORT));
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.REPORT));
            }
        });

        //Отменить печать принятого документа, если он еще не был напечатан.
        dispatcher.submit(new Runnable() {
            public void run() {
                printDispatcher.submitToPrint(documentSelector.getDocument(DocType.POSTER));
                Document document = documentSelector.getDocument(DocType.POSTER);
                printDispatcher.submitToPrint(document);
                printDispatcher.cancelOfPrint(document);
            }
        });

        //Вызывает метод остановки печати
        dispatcher.submit(new Runnable() {
            int n = 10000;

            public void run() {
                try {
                    Thread.sleep(n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Document> notPrintedDocs = printDispatcher.stopAll();
                System.out.println("Остановка печати. Список ненапечатанных документов:");
                for (Document doc : notPrintedDocs) {
                    System.out.printf(" %s: %d мс, %s \n",
                            doc.getName(), doc.getPrintTime(),
                            doc.getPageSize());
                }
            }
        });

        // Получить отсортированный список напечатанных документов. Список может быть отсортирован на выбор:
        // по порядку печати, по типу документов, по продолжительности печати, по размеру бумаги.
        dispatcher.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Document> printedDocs = printDispatcher.getSortedPrintedDocs(SortType.BY_PAGE_SIZE);
                System.out.println("Отсортированный список напечатанных документов по формату бумаги:");
                for (Document doc : printedDocs) {
                    System.out.printf(" %s: %d мс, %s \n",
                            doc.getName(), doc.getPrintTime(),
                            doc.getPageSize());
                }

                printedDocs = printDispatcher.getSortedPrintedDocs(SortType.BY_NAME);
                System.out.println("Отсортированный список напечатанных документов по имени:");
                for (Document doc : printedDocs) {
                    System.out.printf(" %s: %d мс, %s \n",
                            doc.getName(), doc.getPrintTime(),
                            doc.getPageSize());
                }

                printedDocs = printDispatcher.getSortedPrintedDocs(SortType.BY_PRINT_TIME);
                System.out.println("Отсортированный список напечатанных документов по времени печати:");
                for (Document doc : printedDocs) {
                    System.out.printf(" %s: %d мс, %s \n",
                            doc.getName(), doc.getPrintTime(),
                            doc.getPageSize());
                }

                //Рассчитать среднюю продолжительность печати напечатанных документов
                System.out.printf("Среднее время, затраченное на печать: %d мс \n", (long) printDispatcher.getAveragePrintTime());
            }
        });
    }
}

