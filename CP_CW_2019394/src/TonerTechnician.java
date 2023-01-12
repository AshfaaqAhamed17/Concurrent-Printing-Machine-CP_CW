//THREAD CLASS
public class TonerTechnician extends Thread {

    private ServicePrinter servicePrinter;

    public TonerTechnician(String technicianName, ThreadGroup technicianThreadGroup, ServicePrinter servicePrinter) {
        super(technicianThreadGroup, technicianName);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        int tonerCartridgeCount = 0;
        for (int i = 1; i <= 3; i++) {
            servicePrinter.replaceTonerCartridge();
            if (((LaserPrinter)servicePrinter).isTonerReplaced()){
                tonerCartridgeCount++;
            }
            int num = ((int)(Math.random()*5000 + 1));
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[[Toner Technician]] " + Thread.currentThread().getName() + " finished replacing printer with [" + tonerCartridgeCount + "] toner cartridge.");
    }
}
