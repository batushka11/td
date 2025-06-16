//Author: Andrii Zhupanov 214A 55616

package transmisja;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Plot {
    public static XYSeries createPlot(double fs, int n, double[] arr, String Name) {
        XYSeries plot = new XYSeries(Name);
        for(int k = 0; k < n; k++){
            double t = (double) k / fs;
            plot.add(t, arr[k]);
        }
        return plot;
    }

    public static XYSeries createPlotCT(int n, int[] arr,double Tb, String Name) {
        XYSeries plot = new XYSeries(Name);
        for(int k = 0; k < n; k++){
            double tStart = k * Tb;
            double tEnd = (k + 1) * Tb;

            plot.add(tStart, arr[k]);
            plot.add(tEnd, arr[k]);
        }
        return plot;
    }

    public static XYSeries createFFTPlot(double fs, int n, Complex[] fft, String Name) {
        XYSeries plot = new XYSeries(Name);

        for (int k = 0; k <= (n / 2) - 1; k++) {
            double mK = 10 * Math.log10(fft[k].mK());
            //double mK = fft[k].mK() / n;
            double fk = k * fs / n;
            plot.add(fk, mK);
        }
        return plot;
    }

    public static void plot(XYSeries plot, String Name) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(plot);
        JFreeChart jfreeChart =  ChartFactory.createXYLineChart(
                Name, "", "",
                dataset, PlotOrientation.VERTICAL, true, true, false);
//        XYPlot logPlot = jfreeChart.getXYPlot();
//        LogarithmicAxis logAxis = new LogarithmicAxis("Hz");
//        logPlot.setDomainAxis(logAxis);
        JFrame frame = new JFrame(Name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(jfreeChart);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void generatePlot(int N, double fs, double signal[], String Name) {
        XYSeries plot = Plot.createPlot(fs, N, signal, Name);
        Plot.plot(plot, Name);
    }

    public static void generateFFTPlot(int N, double fs, double signal[], String Name) {
        Complex FFT[] = transmisja.FFT.calculateFFT(signal);
        XYSeries plot = Plot.createFFTPlot(fs, N, FFT, Name);
        Plot.plot(plot, Name);
    }

    public static void generatePlotCT(int N, int signal[], double Tb, String Name) {
        XYSeries plot = Plot.createPlotCT(N, signal, Tb, Name);
        Plot.plot(plot, Name);
    }

    public static XYSeries createAlfaBersPlot(double[] bers, double[] alfas, String Name) {
        XYSeries plot = new XYSeries(Name);
        for(int k = 0; k < 40; k++){
            plot.add(alfas[k], bers[k]);
        }
        return plot;
    }

    public static void plot3(XYSeries plot,XYSeries plot1,XYSeries plot2, String Name) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(plot);
        dataset.addSeries(plot1);
        dataset.addSeries(plot2);
        JFreeChart jfreeChart =  ChartFactory.createXYLineChart(
                Name, "", "",
                dataset, PlotOrientation.VERTICAL, true, true, false);
//        XYPlot logPlot = jfreeChart.getXYPlot();
//        LogarithmicAxis logAxis = new LogarithmicAxis("Hz");
//        logPlot.setDomainAxis(logAxis);
        JFrame frame = new JFrame(Name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(jfreeChart);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
