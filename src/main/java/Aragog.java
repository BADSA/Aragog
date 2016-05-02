/*
    Class that starts the execution
    of Aragog
*/
public class Aragog {

    public static void main(String[] args) {
        Scheduler sch = new Scheduler();
        sch.loadURLS();

        int nthreads = Integer.parseInt(args[0]);
        for (int t = 0; t < nthreads; t++) {
            System.out.println("Starting Downloader " + t);
            (new Thread(new Downloader())).start();
        }
    }
}
