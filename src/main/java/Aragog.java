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
        Downloader[] downloaders = new Downloader[threads];
        for (int t = 0; t < threads; t++) {
            try{
            downloaders[t] = new Downloader();
            piscina.execute(downloaders[t]); }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        piscina.shutdown();
        while (!piscina.isTerminated()) {

        }
    }

    public static void main(String[] args) {
        Scheduler sch = new Scheduler();
        sch.loadURLS();
        int poolsize = Integer.parseInt(args[0]);
        Aragog aragog = new Aragog();

        while (true) {
            System.out.println("Executing..");
            aragog.execute(5, poolsize);
            sch.loadURLS();
            System.out.println("Executed");
        }
    }
}
