//Author: Andrii Zhupanov 214A 55616
package transmisja;

import org.jfree.data.xy.XYSeries;
import java.util.Arrays;

public class czesc2 {
    public static void main(String[] args) {
        int[] bits = Helpers.convertFromASCItoBits("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        int[] encode7_4 = Hamming7_4.encode(bits);

        double A = 1;
        double A1 = 1;
        double A2 = 0.5;
        double B = encode7_4.length;
        double Tc = 6;
        double Tb = Tc / B;
        double W = 2;
        double fn = W / Tb;
        double fs = 2000;
        double fn1 = (W + 1) / Tb;
        double fn2 = (W + 2) / Tb;
        double t0 = Tc * 0.95;

        double[] alfas = new double[10];
        for(int i = 0; i < 10; i++) {
            alfas[i] = i * 3.0 / 9.0;
        }

        double[] bers_ask_74 = new double[10];
        double[] bers_psk_74 = new double[10];
        double[] bers_fsk_74 = new double[10];

        double[] za = Modulation.za(A1, A2, encode7_4, fn, fs, Tb);
        double[] xt = Modulation.xt(za, fn, fs, A, 0);

        for(int i = 0; i < 10; i++) {
            double[] yt = Helpers.generateAndAddNoiseModified(xt, alfas[i], t0, Tc);
            double[] pt = Demodulation.ptASC(yt, fs, Tb);
            // uzyto LLM
            double min = Arrays.stream(pt).min().orElse(0);
            double max = Arrays.stream(pt).max().orElse(1);
            double bestBER = 100.0;


            for(int j = 0; j <= 50; j++) {
                double h = min + (max - min) * j / 50.0;
                int[] ct = Demodulation.ctASK(pt, h);
                int[] decode = Hamming7_4.decode(ct);
                double ber = Helpers.BER(bits, decode);
                if(ber < bestBER) {
                    bestBER = ber;
                }
            }
            // koniec
            bers_ask_74[i] = bestBER;
        }

        double[] zp = Modulation.zp(encode7_4, fn, fs, Tb);
        double[] xt1 = Modulation.xt(zp, fn, fs, A, Math.PI);

        for(int i = 0; i < 10; i++) {
            double[] yt = Helpers.generateAndAddNoiseModified(xt1, alfas[i], t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            int[] ct = Demodulation.ct(pt, 0);
            int[] decode = Hamming7_4.decode(ct);
            bers_psk_74[i] = Helpers.BER(bits, decode);
        }

        double[] zf = Modulation.zf(encode7_4, fn1, fn2, fs, Tb);
        double[] xt2 = Modulation.xt1(zf, fn1, fs);
        double[] xt3 = Modulation.xt1(zf, fn2, fs);

        for(int i = 0; i < 10; i++) {
            double[] yt1 = Helpers.generateAndAddNoiseModified(xt2, alfas[i], t0, Tc);
            double[] yt2 = Helpers.generateAndAddNoiseModified(xt3, alfas[i], t0, Tc);
            double[] pt1 = Demodulation.pt(yt1, fs, Tb);
            double[] pt2 = Demodulation.pt(yt2, fs, Tb);
            double[] pt = Demodulation.ptDifferences(pt1, pt2);
            int[] ct = Demodulation.ct(pt, 0);
            int[] decode = Hamming7_4.decode(ct);
            bers_fsk_74[i] = Helpers.BER(bits, decode);
        }

        int[] encode15_11 = Hamming15_11.encode(bits);
        B = encode15_11.length;
        Tb = Tc / B;
        fn = W / Tb;
        fn1 = (W + 1) / Tb;
        fn2 = (W + 2) / Tb;

        double[] bers_ask_1511 = new double[10];
        double[] bers_psk_1511 = new double[10];
        double[] bers_fsk_1511 = new double[10];

        za = Modulation.za(A1, A2, encode15_11, fn, fs, Tb);
        xt = Modulation.xt(za, fn, fs, A, 0);

        for(int i = 0; i < 10; i++) {
            double[] yt = Helpers.generateAndAddNoiseModified(xt, alfas[i], t0, Tc);
            double[] pt = Demodulation.ptASC(yt, fs, Tb);
            // uzyto LLM
            double min = Arrays.stream(pt).min().orElse(0);
            double max = Arrays.stream(pt).max().orElse(1);
            double bestBER = 100.0;

            for(int j = 0; j <= 50; j++) {
                double h = min + (max - min) * j / 50.0;
                int[] ct = Demodulation.ctASK(pt, h);
                int[] decode = Hamming15_11.decode(ct);
                double ber = Helpers.BER(bits, decode);
                if(ber < bestBER) {
                    bestBER = ber;
                }
            }
            //koniec
            bers_ask_1511[i] = bestBER;
        }

        zp = Modulation.zp(encode15_11, fn, fs, Tb);
        xt1 = Modulation.xt(zp, fn, fs, A, Math.PI);

        for(int i = 0; i < 10; i++) {
            double[] yt = Helpers.generateAndAddNoiseModified(xt1, alfas[i], t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            int[] ct = Demodulation.ct(pt, 0);
            int[] decode = Hamming15_11.decode(ct);
            bers_psk_1511[i] = Helpers.BER(bits, decode);
        }

        zf = Modulation.zf(encode15_11, fn1, fn2, fs, Tb);
        xt2 = Modulation.xt1(zf, fn1, fs);
        xt3 = Modulation.xt1(zf, fn2, fs);

        for(int i = 0; i < 10; i++) {
            double[] yt1 = Helpers.generateAndAddNoiseModified(xt2, alfas[i], t0, Tc);
            double[] yt2 = Helpers.generateAndAddNoiseModified(xt3, alfas[i], t0, Tc);
            double[] pt1 = Demodulation.pt(yt1, fs, Tb);
            double[] pt2 = Demodulation.pt(yt2, fs, Tb);
            double[] pt = Demodulation.ptDifferences(pt1, pt2);
            int[] ct = Demodulation.ct(pt, 0);
            int[] decode = Hamming15_11.decode(ct);
            bers_fsk_1511[i] = Helpers.BER(bits, decode);
        }

        XYSeries plot = Plot.createAlfaBersPlot(bers_ask_74, alfas, "ASK 7,4");
        XYSeries plot1 = Plot.createAlfaBersPlot(bers_psk_74, alfas, "PSK 7,4");
        XYSeries plot2 = Plot.createAlfaBersPlot(bers_fsk_74, alfas, "FSK 7,4");
        Plot.plot3(plot, plot1, plot2, "BER/β - Hamming (7,4)");

        XYSeries plot3 = Plot.createAlfaBersPlot(bers_ask_1511, alfas, "ASK 15,11");
        XYSeries plot4 = Plot.createAlfaBersPlot(bers_psk_1511, alfas, "PSK 15,11");
        XYSeries plot5 = Plot.createAlfaBersPlot(bers_fsk_1511, alfas, "FSK 15,11");
        Plot.plot3(plot3, plot4, plot5, "BER/β - Hamming (15,11)");
    }
}