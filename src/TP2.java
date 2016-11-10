import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/* Skeleton do trabalho TP2. */

public class TP2 {

    /**
     * @param args the command line arguments
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static void main(String[] args) throws NumberFormatException, IOException {
        /* Leia requestString, nÃºmero de cilindros e cilindro inicial 
           do arquivo de entrada conforme o formato especificado no trabalho.
           O arquivo de entrada Ã© passado por args. */
    	
    	BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
        
    	int numCilindros = Integer.parseInt(in.readLine());
    	int initCilindro = Integer.parseInt(in.readLine());
    	String[] requests = in.readLine().split(" ");
    	int[] requestString = new int[requests.length];

    	requestString = Arrays.asList(requests).stream().mapToInt(e -> Integer.parseInt(e)).toArray();	
    	in.close();
    	
        DiskScheduler fcfs = new FCFS(requestString, numCilindros, initCilindro);
        System.out.println("Número de cilindros percorridos com o algoritmo FCFS: " + fcfs.serviceRequests());
        fcfs.printGraph("fcfs.jpg");
        
        DiskScheduler sstf = new SSTF(requestString, numCilindros, initCilindro);
        System.out.println("Número de cilindros percorridos com o algoritmo SSTF: " + sstf.serviceRequests());
        sstf.printGraph("sstf.jpg");
        
        DiskScheduler scan = new SCAN(requestString, numCilindros, initCilindro);
        System.out.println("Número de cilindros percorridos com o algoritmo SCAN: " + scan.serviceRequests());
        scan.printGraph("scan.jpg");
        
        DiskScheduler cscan = new CSCAN(requestString, numCilindros, initCilindro);
        System.out.println("Número de cilindros percorridos com o algoritmo CSCAN: " + cscan.serviceRequests());
        cscan.printGraph("cscan.jpg");
        
        DiskScheduler look = new LOOK(requestString, numCilindros, initCilindro);
        System.out.println("Número de cilindros percorridos com o algoritmo LOOK: " + look.serviceRequests());
        look.printGraph("look.jpg");
        
   }
    
}
