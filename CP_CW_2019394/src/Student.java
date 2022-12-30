//THREAD CLASS
public class Student implements Runnable{

    private String studentName;
    private ThreadGroup studentThreadGroup;
    private Printer printer;

    public Student(String studentName, ThreadGroup studentThreadGroup, Printer printer) {
        super();
        this.studentName = studentName;
        this.studentThreadGroup = studentThreadGroup;
        this.printer = printer;
    }

    @Override
    public void run() {
        Document[] document = new Document[5];
        int numberOfTotalPages = 0;

        document[0] = new Document(Thread.currentThread().getName(), "FYP - Proposal", 10);
        document[1] = new Document(Thread.currentThread().getName(), "CP", 10);
        document[2] = new Document(Thread.currentThread().getName(), "FYP - Thesis", 10);
        document[3] = new Document(Thread.currentThread().getName(), "ASS-WD", 10);
        document[4] = new Document(Thread.currentThread().getName(), "FM", 10);

        for (Document documentContext: document) {
            printer.printDocument(documentContext);
            numberOfTotalPages+=documentContext.getNumberOfPages();

            int num = ((int)(Math.random())*100 + 1);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(" ------>> " + Thread.currentThread().getName() + " finished Printing: 5 Documents, " + numberOfTotalPages + " pages <<------");

    }
}
