public class TonerTechnician extends Thread {

    private ServicePrinter servicePrinter;

    public TonerTechnician(ThreadGroup group, String name, ServicePrinter servicePrinter) {
        super(group, name);
        this.servicePrinter = servicePrinter;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            servicePrinter.replaceTonerCartridge();
            int num = ((int)(Math.random())*100 + 1);
            try {
                Thread.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Toner Technician " + getName() + " finished attempts to replace printer with toner cartridge.");
    }
}
