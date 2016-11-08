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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class SCAN implements DiskScheduler{
    private int[] requestString;
    private int numCilindros;
    private int initCilindro;
    
    public SCAN(int[] requestString, int numCilindros, int initCilindro){
        this.numCilindros = numCilindros;
        this.initCilindro = initCilindro;
        this.requestString = SCANify(requestString);
    }
    
    private int[] SCANify(int[] requestString){
    	//cria uma c�pia da lista para preservar o requestString original
    	int[] aux = requestString.clone();
    	
    	Arrays.sort(aux);
    	
    	//pega os cilindros maiores que o cilindro inicial e os coloca no vetor em ordem crescente
    	int[] result = new int[aux.length + 1];
    	int i = 0;
    	for(int j = 0; j < aux.length; j++){
    		if(aux[j] >= initCilindro){
    			result[i] = aux[j];
    			i++;
    		}
    	}
    	
    	//adiciona �ltimo cilindro ao vetor
    	result[i] = numCilindros - 1;
    	i++;
    	
    	//pega os cilindros menores que o cilindro inicial e os coloca no vetor em ordem decrescente
    	for(int j = aux.length - 1; j >= 0; j--){
    		if(aux[j] < initCilindro){
    			result[i] = aux[j];
    			i++;
    		}
    	}
    	return result;
    }
    
    @Override
    public int serviceRequests() {
        int total = 0, i;
        
        /* Determine o número de cilindros percorridos pelo cabeçote de leitura
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
        
        XYSeries series = new XYSeries("SCAN");
        
        /* Adiciona o pontos XY do gráfico de linhas. */
        series.add(y_axis, initCilindro);
        
        for(i=0;i<requestString.length;i++){
            series.add(y_axis-((i+1)*10), requestString[i]);
        }
        
        /* Adiciona a serie criada a um SeriesCollection. */
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        /* Gera o gráfico de linhas */
        JFreeChart chart = ChartFactory.createXYLineChart(
            /* Title */
            "SCAN Scheduler Algorithm",
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
        
        /* Configura a espessura da linha do gráfico  */
        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        
        /* Escreve o gráfico para um arquivo indicado. */
        try {
            ChartUtilities.saveChartAsJPEG(new File(filename), chart, 500, 300);
        } catch (IOException ex) {
            Logger.getLogger(SCAN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
