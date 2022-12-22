public class PrintingSystem {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup students = new ThreadGroup("students");
        ThreadGroup technicians = new ThreadGroup("technicians");

//        ServicePrinter laserPrinter = new LaserPrinter("IIT - Printer", 2019394, 650, 100,0);
        ServicePrinter laserPrinter = new LaserPrinter("IIT - Printer", 2019394, 50, 50,students);

        Thread student1 = new Student("Student 01",students, laserPrinter);
        Thread student2 = new Student("Student 02",students, laserPrinter);
        Thread student3 = new Student("Student 03",students, laserPrinter);
        Thread student4 = new Student("Student 04",students, laserPrinter);

        Thread paperTechnician = new PaperTechnician(technicians,"Tech 01", laserPrinter);
        Thread tonerTechnician = new TonerTechnician(technicians,"Tech 02", laserPrinter);

        // start the threads so that they enter the runnable state and await their turn of execution
        // however, we cannot assure which thread will actually execute first or the order in which they'll execute
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        // wait for all threads to terminate upon completion of their execution
        // before proceeding to print the summary and exit the program
        student1.join();
        student2.join();
        student3.join();
        student4.join();
        paperTechnician.join();
        tonerTechnician.join();

        System.out.println("\nAll tasks completed. Printing printer status...\n");

        System.out.print("==================================================\n" +
                "                PRINTER SUMMARY                  \n" +
                "=================================================\n");
        System.out.println(laserPrinter.toString());
    }

}
