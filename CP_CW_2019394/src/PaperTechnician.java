public class PaperTechnician extends Thread{

    private ServicePrinter servicePrinter;

    public PaperTechnician(ThreadGroup group, String name, ServicePrinter servicePrinter) {
        super(group, name);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            servicePrinter.refillPaper();
            int num = ((int)(Math.random())*100 + 1);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Paper Technician " + getName() +" finished attempts to refill printer with paper packs.");
    }
}
