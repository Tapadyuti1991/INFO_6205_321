import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

public class PlottingTheResults extends ApplicationFrame {


    public PlottingTheResults(String title,Double[] allFittestPerson) {
        super(title);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Happiness Activity -16 Hours " ," Generations","Total Reward Point",
                createDataset(allFittestPerson) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
//        renderer.setSeriesPaint( 1 , Color.GREEN );
//        renderer.setSeriesPaint( 2 , Color.YELLOW );
//        renderer.setSeriesPaint( 3 , Color.BLACK );
//        renderer.setSeriesPaint( 4 , Color.BLUE );
//        renderer.setSeriesPaint( 5 , Color.CYAN );
        renderer.setSeriesStroke( 0 , new BasicStroke( 0.5f ) );
//        renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
//        renderer.setSeriesStroke( 2 , new BasicStroke( 1.0f ) );
//        renderer.setSeriesStroke( 3 , new BasicStroke( 1.0f ) );
//        renderer.setSeriesStroke( 4 , new BasicStroke( 1.0f ) );
//        renderer.setSeriesStroke( 5 , new BasicStroke( 1.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );

    }
    private XYDataset createDataset(Double [] allresult ) {
        final XYSeries constantLength1 = new XYSeries( "Experiment No 1" );
        Double totalPoints = 0.0;
        for(int i = 0 ; i <allresult.length ; i++) {
            constantLength1.add(i, allresult[i]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( constantLength1 );

        return dataset;
    }
}
