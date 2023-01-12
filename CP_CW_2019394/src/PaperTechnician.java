//THREAD CLASS
public class PaperTechnician extends Thread{

    private ServicePrinter servicePrinter;

    public PaperTechnician(String technicianName, ThreadGroup technicianThreadGroup, ServicePrinter servicePrinter) {
        super(technicianThreadGroup, technicianName);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        int paperPacksCount = 0;
        for (int i = 1; i <= 3; i++) {
            servicePrinter.refillPaper();
            if (((LaserPrinter)servicePrinter).isPaperRefilled()){
                paperPacksCount++;
            }
            int num = ((int)(Math.random()*5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[[Paper Technician]] " + Thread.currentThread().getName() +" finished refilling printer with [" + paperPacksCount + "] paper packs.");
    }
}
