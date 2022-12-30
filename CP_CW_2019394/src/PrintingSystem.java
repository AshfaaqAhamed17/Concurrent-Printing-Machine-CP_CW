import java.util.Scanner;

public class PrintingSystem {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup students = new ThreadGroup("students");
        ThreadGroup technicians = new ThreadGroup("technicians");

        ServicePrinter laserPrinter = new LaserPrinter("IIT - Printer", 2019394, 50, 50, students);

        Runnable runnableStudent1 = new Student("Student 01", students, laserPrinter);
        Runnable runnableStudent2 = new Student("Student 02", students, laserPrinter);
        Runnable runnableStudent3 = new Student("Student 03", students, laserPrinter);
        Runnable runnableStudent4 = new Student("Student 04", students, laserPrinter);

        Thread student1 = new Thread(students, runnableStudent1, "Student 001");
        Thread student2 = new Thread(students, runnableStudent2, "Student 002");
        Thread student3 = new Thread(students, runnableStudent3, "Student 003");
        Thread student4 = new Thread(students, runnableStudent4, "Student 004");

        Runnable runnablePaperTechnician = new PaperTechnician("Tech 01", technicians, laserPrinter);
        Runnable runnableTonerTechnician = new TonerTechnician("Tech 02", technicians, laserPrinter);

        Thread paperTechnician = new Thread(technicians, runnablePaperTechnician, "Tech 01");
        Thread tonerTechnician = new Thread(technicians, runnableTonerTechnician, "Tech 02");

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        student1.join();
        student2.join();
        student3.join();
        student4.join();
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
                    System.out.println(laserPrinter.toString());
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
