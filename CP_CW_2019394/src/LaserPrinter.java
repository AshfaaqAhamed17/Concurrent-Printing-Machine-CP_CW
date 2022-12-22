//MONITOR CLASS
public class LaserPrinter implements ServicePrinter{

    private String name;
    private int id;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numOfDocPrinted;
    private ThreadGroup students;
//    private boolean paperRefilled = false; // To keep track of call to refill Paper() is  successful or not.
//    private boolean tonerReplaced = false; // To keep track of call to toner Replaced() is  successful or not.

    public LaserPrinter(String name, int id, int currentPaperLevel, int currentTonerLevel, ThreadGroup students) {
        this.name = name;
        this.id = id;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
        this.numOfDocPrinted = 0;
        this.students = students;
    }

    private boolean allStudentsHaveFinishedPrinting() {
        return students.activeCount() < 1;
    }

    @Override
    public synchronized void printDocument(Document document) {
        boolean insufficientPaperLvl = document.getNumberOfPages() > currentPaperLevel;
        boolean insufficientTonerLvl = document.getNumberOfPages() > currentTonerLevel;
        while (insufficientPaperLvl || insufficientTonerLvl){
            if (insufficientPaperLvl && insufficientTonerLvl){
                System.out.println("INSUFFICIENT TONER LEVEL & PAPER LEVEL -> " + "Printing document pause. -- NAME: " + document.getUserID() + " | DOC NAME: " + document.getDocumentName() +
                        " | Number of pages: " + document.getNumberOfPages());
            }else if (insufficientTonerLvl){
                System.out.println("INSUFFICIENT TONER LEVEL -> " + "Printing document pause. -- NAME: " + document.getUserID() + " | DOC NAME: " + document.getDocumentName() +
                        " | Number of pages: " + document.getNumberOfPages());
            }else{
                System.out.println("INSUFFICIENT PAPER LEVEL -> " + "Printing document pause. -- NAME: " + document.getUserID() + " | DOC NAME: " + document.getDocumentName() +
                        " | Number of pages: " + document.getNumberOfPages());
            }
            try {
                wait(5000);
                insufficientPaperLvl = document.getNumberOfPages() > currentPaperLevel;
                insufficientTonerLvl = document.getNumberOfPages() > currentTonerLevel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\tPrinting document. -- NAME: " + document.getUserID() + " | DOC NAME: " + document.getDocumentName() +
                " | Number of pages: " + document.getNumberOfPages());
        currentTonerLevel -= document.getNumberOfPages();
        currentPaperLevel -= document.getNumberOfPages();
        numOfDocPrinted++;
        System.out.println("\t(" + numOfDocPrinted +") SUCCESSFULLY PRINTED DOCUMENT. New paper level: {" + currentPaperLevel + "}. New Toner Level: {" + currentTonerLevel + "}." );
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        boolean unableToRefillToner = currentTonerLevel > MINIMUM_TONER_LEVEL;
        while (unableToRefillToner){
            System.out.println("(TONER LEVEL CHECK...) Toner Cartridge level is available... Current toner level is " + currentTonerLevel);
            try {
                wait(5000);
//                if(allStudentsHaveFinishedPrinting()) {
                unableToRefillToner = currentTonerLevel >= MINIMUM_TONER_LEVEL;
                return;
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Toner Cartridge level need to refilled...");
        currentTonerLevel += PAGES_PER_TONER_CARTRIDGE;
        System.out.println("Replace of Toner Cartridge is done. New toner level is " + currentTonerLevel);
        notifyAll();
    }

    @Override
    public synchronized void refillPaper() {
        boolean unableToRefillPaper = (FULL_PAPER_TRAY -50) <= currentPaperLevel;
        while (unableToRefillPaper){
            System.out.println("(PAPER LEVEL CHECK...) Paper Tray level is available... Current paper level is " + currentPaperLevel);
            try {
                wait(5000);
//                if(allStudentsHaveFinishedPrinting()) {
//                }
                unableToRefillPaper = (FULL_PAPER_TRAY -50) <= currentPaperLevel;
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Paper Tray needs to be refilled.");
        currentPaperLevel += SHEETS_PER_PACK;
        System.out.println("Refill of Paper Tray is done. New paper level is " + currentPaperLevel);
        notifyAll();
    }

    @Override
    public synchronized String toString() {
        return "Laser Printer Report" +
                "\n--------------------"+
                "\n* Printer Name => " + name +
                "\n* Printer ID => " + id +
                "\n* Paper Level => " + currentPaperLevel +
                "\n* Toner Level => " + currentTonerLevel +
                "\n* No of Documents Printed => " + numOfDocPrinted;
    }
}
