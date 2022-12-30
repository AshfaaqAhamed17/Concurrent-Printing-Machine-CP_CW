//THREAD CLASS
public class TonerTechnician implements Runnable {

    private String technicianName;
    private ThreadGroup technicianThreadGroup;
    private ServicePrinter servicePrinter;

    public TonerTechnician(String technicianName, ThreadGroup technicianThreadGroup, ServicePrinter servicePrinter) {
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
        int tonerCartridgeCount = 0;
        for (int i = 1; i <= 3; i++) {
            servicePrinter.replaceTonerCartridge();
            if (((LaserPrinter)servicePrinter).isTonerReplaced()){
                tonerCartridgeCount++;
            }
            int num = ((int)(Math.random())*100 + 1);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Toner Technician " + getTechnicianName() + " finished replacing printer with [" + tonerCartridgeCount + "] toner cartridge.");
    }
}
