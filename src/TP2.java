


/* Skeleton do trabalho TP2. */

public class TP2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Leia requestString, número de cilindros e cilindro inicial 
           do arquivo de entrada conforme o formato especificado no trabalho.
           O arquivo de entrada é passado por args. */
        
        int[] requestString = {98, 183, 37, 122, 14, 124, 65, 67};
        int numCilindros = 200;
        int initCilindro = 37;
        
        DiskScheduler fcfs = new FCFS(requestString, numCilindros, initCilindro);
        System.out.println("N�mero de cilindros percorridos com o algoritmo FCFS: " + fcfs.serviceRequests());
        fcfs.printGraph("fcfs.jpg");
        
        DiskScheduler sstf = new SSTF(requestString, numCilindros, initCilindro);
        System.out.println("N�mero de cilindros percorridos com o algoritmo SSTF: " + sstf.serviceRequests());
        sstf.printGraph("sstf.jpg");
        
        DiskScheduler scan = new SCAN(requestString, numCilindros, initCilindro);
        System.out.println("N�mero de cilindros percorridos com o algoritmo SCAN: " + scan.serviceRequests());
        scan.printGraph("scan.jpg");
   }
    
}
