import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Class that starts the execution
    of Aragog
*/
public class Aragog {
    public void execute(int threads, int poolsize) {

        ExecutorService piscina
                = Executors.newFixedThreadPool(poolsize);

        piscina.execute(new Downloader());
        for (int t = 0; t < threads; t++) {
            piscina.execute(new Downloader());
        }

        piscina.shutdown();
        while (!piscina.isTerminated()) {

        }
    }

    public static void main(String[] args) {
        Scheduler sch = new Scheduler();
        sch.loadURLS();
        int poolsize = Integer.parseInt("4");
        Aragog aragog = new Aragog();

        while (true) {
            System.out.println("Executing..");
            aragog.execute(10, poolsize);
            sch.loadURLS();
            if (sch.isQueueEmpty()) {
                System.out.println("No more links to process");
                break;
            }
            System.out.println("Executed");
        }
    }
}
