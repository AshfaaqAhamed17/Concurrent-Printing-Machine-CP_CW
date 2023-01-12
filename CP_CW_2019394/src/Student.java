//THREAD CLASS
public class Student extends Thread{

    private Printer printer;

    public Student(String studentName, ThreadGroup studentThreadGroup, Printer printer) {
        super(studentThreadGroup, studentName);
        this.printer = printer;
    }

    @Override
    public void run() {
        Document[] documentToPrint = new Document[5];
        int numberOfTotalPages = 0;

        documentToPrint[0] = new Document(Thread.currentThread().getName(), "Module No. 001", 2);
        documentToPrint[1] = new Document(Thread.currentThread().getName(), "Module No. 002", 5);
        documentToPrint[2] = new Document(Thread.currentThread().getName(), "Module No. 003", 10);
        documentToPrint[3] = new Document(Thread.currentThread().getName(), "Module No. 004", 8);
        documentToPrint[4] = new Document(Thread.currentThread().getName(), "Module No. 005", 15);

        for (Document documentContext: documentToPrint) {
            printer.printDocument(documentContext);
            numberOfTotalPages+=documentContext.getNumberOfPages();

            int num = ((int)(Math.random()*5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(" ------>> " + Thread.currentThread().getName() + " finished Printing: 5 Documents, " + numberOfTotalPages + " pages <<------");

    }
}
