/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.BasicStroke;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SSTF implements DiskScheduler{
    private int[] requestString;
    private int numCilindros;
    private int initCilindro;
    private int[] orderedRequest;
    
    public SSTF(int[] requestString, int numCilindros, int initCilindro){
        this.numCilindros = numCilindros;
        this.initCilindro = initCilindro;
        this.requestString = SSTFify(requestString);
    }
    
    private int[] SSTFify(int[] requestString){
    	//cria uma cópia da lista para preservar o requestString original
    	int[] result = new int[requestString.length];
    	for(int i = 0; i < requestString.length; i++){
    		result[i] = requestString[i];
    	}
    	
    	//compara os valores da lista com o cilindro atual(na primeira execução é o initCilindro)
    	//em busca dos cilindros com menor tempo de encontro
    	int currentCilinder = initCilindro;
    	for(int i = 0; i < result.length; i++){
    		int shortestTime = Integer.MAX_VALUE;
    		int shortestPos = 0;
    		for(int j = i; j < result.length; j++){
    			int dif = abs(currentCilinder - result[j]);
    			if(dif <= shortestTime){
    				shortestTime = dif;
    				shortestPos = j;
    			}
    		}
    		
    		//manda o valor com o menor tempo de encontro para o início da lista
    		int aux = result[i];
    		result[i] = result[shortestPos];
    		result[shortestPos] = aux;
    		
    		currentCilinder = result[i];
    	}
    	return result;
    }
    
    @Override
    public int serviceRequests() {
        int total = 0, i;
        
        /* Determine o nÃºmero de cilindros percorridos pelo cabeÃ§ote de leitura
           para o algoritmo FCFS. */
        total = abs(initCilindro - requestString[0]);
        
        for(i=0;i<requestString.length-1;i++){
            total += abs(requestString[i]-requestString[i+1]);
        }
        
        return total;
    }

    @Override
    public void printGraph(String filename) {
        int i;
        int y_axis = requestString.length * 10;
        
        XYSeries series = new XYSeries("SSTF");
        
        /* Adiciona o pontos XY do grÃ¡fico de linhas. */
        series.add(y_axis, initCilindro);
        
        for(i=0;i<requestString.length;i++){
            series.add(y_axis-((i+1)*10), requestString[i]);
        }
        
        /* Adiciona a serie criada a um SeriesCollection. */
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        /* Gera o grÃ¡fico de linhas */
        JFreeChart chart = ChartFactory.createXYLineChart(
            /* Title */
            "SSTF Scheduler Algorithm",
            /* Title x*/
            "",
            /* Title y */
            "Cilindro",
            dataset,
            /* Plot Orientation */
            PlotOrientation.HORIZONTAL,
            /* Show Legend */
            false,
            /* Use tooltips */
            false,
            /* Configure chart to generate URLs? */
            false
        );
        
        /* Configura a espessura da linha do grÃ¡fico  */
        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        
        /* Escreve o grÃ¡fico para um arquivo indicado. */
        try {
            ChartUtilities.saveChartAsJPEG(new File(filename), chart, 500, 300);
        } catch (IOException ex) {
            Logger.getLogger(SSTF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
