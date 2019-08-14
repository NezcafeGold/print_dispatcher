package document;

import document.type.DocType;

public class DocumentSelector {

    public Document getDocument(DocType docType){
        Document document = null;
        switch (docType){
            case POSTER:
                document = new PosterDocImpl();
                break;
            case REPORT:
                document = new ReportDocImpl();
                break;
            case ANNOUNCEMENT:
                document = new AnnouncementDocImpl();
                break;
        }
        return document;
    }
}
