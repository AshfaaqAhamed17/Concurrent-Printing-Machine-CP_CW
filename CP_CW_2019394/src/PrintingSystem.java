import java.util.Scanner;

public class PrintingSystem {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

//        Thread groups used in the system.
        ThreadGroup students = new ThreadGroup("students");
        ThreadGroup technicians = new ThreadGroup("technicians");

        ServicePrinter laserPrinter = new LaserPrinter("IIT - Printer", 2019394, 10, 10);
        System.out.println("===================INITIAL STATE OF THE PRINTER===================\n");
        System.out.println(laserPrinter);
        System.out.println("\n==================================================================");
        System.out.println("==================================================================\n\n");
        System.out.println("                   <<Printer starting the printing process>>");
        System.out.println("                   -----------------------------------------\n");

//        Initialize the Student threads
        Thread threadStudentNoOne = new Student("Student 01", students, laserPrinter);
        Thread threadStudentNoTwo = new Student("Student 02", students, laserPrinter);
        Thread threadStudentNoThree = new Student("Student 03", students, laserPrinter);
        Thread threadStudentNoFour = new Student("Student 04", students, laserPrinter);

//        Initialize the Technician threads
        Thread paperTechnician = new PaperTechnician("Tech 01", technicians, laserPrinter);
        Thread tonerTechnician = new TonerTechnician("Tech 02", technicians, laserPrinter);

//        Start the student & technician threads and wait for the execution time in the runnable state
        threadStudentNoOne.start();
        threadStudentNoTwo.start();
        threadStudentNoThree.start();
        threadStudentNoFour.start();
        paperTechnician.start();
        tonerTechnician.start();

//        Print summary after all threads are terminated.
        threadStudentNoOne.join();
        threadStudentNoTwo.join();
        threadStudentNoThree.join();
        threadStudentNoFour.join();
        paperTechnician.join();
        tonerTechnician.join();

        System.out.println("\n===============| All tasks completed. Printing printer status... |===============");

        optionMenu:
        while (true) {
            System.out.println("\n\n\t (1) - Print printer summary");
            System.out.println("\t (2) - End system");
            System.out.print("\t\t -- Select option: ");
            String selectMenu = input.nextLine();

            switch (selectMenu) {
                case "1":
                    System.out.println("===========================================================================================================");
                    System.out.println("                                                PRINTER SUMMARY                                           ");
                    System.out.println("===========================================================================================================");
                    System.out.println(laserPrinter);
                    break;

                case "2":
                    System.out.println("=========================================================================================================");
                    System.out.println("=                                 Thank you for using \"" + ((LaserPrinter) laserPrinter).getNameOfPrinter()+"\"                                   =");
                    System.out.println("=========================================================================================================");
                    break optionMenu;
                default:
                    System.out.println("\n------------------------------Invalid Option------------------------------\n");
                    break;

            }
        }
    }
}
