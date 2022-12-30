//public class PaperTechnician extends Thread{
public class PaperTechnician implements Runnable{

    private String technicianName;
    private ThreadGroup technicianThreadGroup;
    private ServicePrinter servicePrinter;

    public PaperTechnician(String technicianName, ThreadGroup technicianThreadGroup, ServicePrinter servicePrinter) {
        super();
        this.technicianName = technicianName;
        this.technicianThreadGroup = technicianThreadGroup;
        this.servicePrinter = servicePrinter;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    @Override
    public void run() {
        int paperPacksCount = 0;
        for (int i = 1; i <= 3; i++) {
            servicePrinter.refillPaper();
            if (((LaserPrinter)servicePrinter).isPaperRefilled()){
                paperPacksCount++;
            }
            int num = ((int)(Math.random())*100 + 1);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Paper Technician " + getTechnicianName() +" finished refilling printer with [" + paperPacksCount + "] paper packs.");
    }
}
