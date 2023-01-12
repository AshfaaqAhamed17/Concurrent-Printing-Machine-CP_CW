//MONITOR CLASS
public class LaserPrinter implements ServicePrinter{

    private String nameOfPrinter;
    private int idOfPrinter;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numOfDocPrinted;
    private boolean paperRefilled = false; // To keep track of call to refill Paper() is  successful or not.
    private boolean tonerReplaced = false; // To keep track of call to toner Replaced() is  successful or not.

    public LaserPrinter(String printerName, int printerId, int currentPaperLevel, int currentTonerLevel) {
        this.nameOfPrinter = printerName;
        this.idOfPrinter = printerId;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
        this.numOfDocPrinted = 0;
    }

//    Method to print the document of the Student. Printing is possible if there is sufficient amount paper and toner only.
//    Reduce 1 sheet and 1 unit of toner for each paper of the document.
    @Override
    public synchronized void printDocument(Document document) {
        boolean lowPaperLevel = document.getNumberOfPages() > currentPaperLevel;
        boolean lowTonerLevel = document.getNumberOfPages() > currentTonerLevel;
        boolean checkMinTonerLevel = MINIMUM_TONER_LEVEL > currentTonerLevel;
        while (lowPaperLevel || lowTonerLevel || checkMinTonerLevel){
        if (lowPaperLevel && lowTonerLevel){
                System.out.println("INSUFFICIENT TONER LEVEL & PAPER LEVEL -> " + "Printing document pause. -- NAME OF STUDENT: " + document.getUserID() + " | DOCUMENT TITLE: " + document.getDocumentName() +
                        " | NUMBER OF PAGES: " + document.getNumberOfPages());
            }else if (lowTonerLevel){
                System.out.println("INSUFFICIENT TONER LEVEL -> " + "Printing document pause. -- NAME OF STUDENT: " + document.getUserID() + " | DOCUMENT TITLE: " + document.getDocumentName() +
                        " | NUMBER OF PAGES: " + document.getNumberOfPages());
            }else if (lowPaperLevel){
                System.out.println("INSUFFICIENT PAPER LEVEL -> " + "Printing document pause. -- NAME OF STUDENT: " + document.getUserID() + " | DOCUMENT TITLE: " + document.getDocumentName() +
                        " | NUMBER OF PAGES: " + document.getNumberOfPages());
            }else {
                System.out.println("MINIMUM TONER LEVEL HAS BEEN REACHED -> " + "Printing document pause. -- NAME OF STUDENT: " + document.getUserID() + " | DOCUMENT TITLE: " + document.getDocumentName() +
                        " | NUMBER OF PAGES: " + document.getNumberOfPages());
            }
            try {
                wait();
                lowPaperLevel = document.getNumberOfPages() > currentPaperLevel;
                lowTonerLevel = document.getNumberOfPages() > currentTonerLevel;
                checkMinTonerLevel = MINIMUM_TONER_LEVEL > currentTonerLevel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\tPrinting document... NAME OF STUDENT: " + document.getUserID() + " | DOCUMENT TITLE: " + document.getDocumentName() +
                " | NUMBER OF PAGES: " + document.getNumberOfPages());
        currentTonerLevel -= document.getNumberOfPages();
        currentPaperLevel -= document.getNumberOfPages();
        numOfDocPrinted++;
        System.out.println("\t<<Document No. " + numOfDocPrinted +">> SUCCESSFULLY PRINTED DOCUMENT. New paper level: {" + currentPaperLevel + "}. New Toner Level: {" + currentTonerLevel + "}." );
        notifyAll();
    }

//    Method to replace toner cartridge. Replace toner cartridge if toner level is below minimum toner level.
//    Replacing toner level will make toner level as Full toner level (500).
    @Override
    public synchronized void replaceTonerCartridge() {
        this.tonerReplaced = false;
        boolean unableToRefillToner = currentTonerLevel >= MINIMUM_TONER_LEVEL;
        while (unableToRefillToner){
            System.out.println("(CHECKING ON THE TONER LEVEL...) Toner Cartridge level is available... Current toner level is " + currentTonerLevel);
            try {
                wait(5000);
                unableToRefillToner = currentTonerLevel >= MINIMUM_TONER_LEVEL;
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Toner Cartridge level needs to replaced...");
        currentTonerLevel = FULL_TONER_LEVEL;
        this.tonerReplaced = true;
        System.out.println("Replace of Toner Cartridge is done. New toner level is " + currentTonerLevel);
        notifyAll();
    }

//    Refill the paper tray with papers. Only able t refill if tray is less than 200 as maximum sheets allowed is 250 & sheets per pack is 50.
    @Override
    public synchronized void refillPaper() {
        this.paperRefilled = false;
        boolean unableToRefillPaper = (FULL_PAPER_TRAY -50) <= currentPaperLevel;
        while (unableToRefillPaper){
            System.out.println("(CHECKING ON THE PAPER LEVEL...) Paper Tray level is available... Current paper level is " + currentPaperLevel);
            try {
                wait(5000);
                unableToRefillPaper = (FULL_PAPER_TRAY -50) <= currentPaperLevel;
//                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Paper Tray needs to be refilled.");
        currentPaperLevel += SHEETS_PER_PACK;
        System.out.println("Refill of Paper Tray is done. New paper level is " + currentPaperLevel);
        this.paperRefilled = true;
        notifyAll();
    }

    public String getNameOfPrinter() {
        return nameOfPrinter;
    }

    public boolean isPaperRefilled() {
        return paperRefilled;
    }

    public boolean isTonerReplaced() {
        return tonerReplaced;
    }

//    Return the current status of the printer
    @Override
    public synchronized String toString() {
        return "|######################################|\n" +
                "|         Laser Printer Report         |" +
                "\n|######################################|"+
                "\n\t* Printer Name => " + nameOfPrinter +
                "\n\t* Printer ID => " + idOfPrinter +
                "\n\t* Paper Level => " + currentPaperLevel +
                "\n\t* Toner Level => " + currentTonerLevel +
                "\n\t* No of Documents Printed => " + numOfDocPrinted;
    }
}
