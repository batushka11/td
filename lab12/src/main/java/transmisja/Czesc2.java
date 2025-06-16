package transmisja;

import org.jfree.data.xy.XYSeries;

import java.util.Arrays;

public class Czesc2 {
    public static void main(String[] args) {

        int[] bits = Helpers.convertFromASCItoBits("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        int[] encode7_4 = Hamming7_4.encode(bits);

        double A = 5;
        double A1 = 10;
        double A2 = 20;

        double B = encode7_4.length;
        double Tc = 6;
        double t0 = Tc * 0.95;
        double Tb = Tc / B;
        double W = 2;

        double fn = W / Tb;
        double fs = 2000;
        double h = 540;
        double fn1 = (W + 1) / Tb;
        double fn2 = (W + 2) / Tb;

        int index = 0;
        double[] alfas = new double[1000];
        double[] bers = new double[1000];

        //ASC 7,4

        double[] za = Modulation.za(A1,A2,encode7_4,fn,fs,Tb);
        double[] xt = Modulation.xt(za, fn, fs, A, 0);

        for(double i = 0; i < 12; i += 0.3){
            double[] yt = Helpers.generateAndAddNoiseModified(xt,i, t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            //Plot.generatePlot(pt.length,fs,pt,"Step 3 - ASK");
            int[] ct = Demodulation.ct(pt, h);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming7_4.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot = Plot.createAlfaBersPlot(bers,alfas,"ASK 7,4");


        //PSC 7,4
        index = 0;
        double[] zp = Modulation.zp(encode7_4,fn,fs,Tb);
        double[] xt1 = Modulation.xt(zp, fn, fs, A, Math.PI);
        for(double i = 0; i < 12; i += 0.3){
            double[] yt = Helpers.generateAndAddNoiseModified(xt1,i, t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            int[] ct = Demodulation.ct(pt, 0);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming7_4.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot1 = Plot.createAlfaBersPlot(bers,alfas,"PSK 7,4");

        //FSC 7,4
        index=0;
        double[] zf = Modulation.zf(encode7_4,fn1,fn2,fs,Tb);
        double[] xt2 = Modulation.xt1(zf,fn1,fs);
        double[] xt3 = Modulation.xt1(zf,fn2,fs);
        for(double i = 0; i < 12; i += 0.3){
            double[] yt1 = Helpers.generateAndAddNoiseModified(xt2,i, t0, Tc);
            double[] yt2 = Helpers.generateAndAddNoiseModified(xt3,i, t0, Tc);
            double[] pt1 = Demodulation.pt(yt1,fs,Tb);
            double[] pt2 = Demodulation.pt(yt2,fs,Tb);
            double[] pt  = Demodulation.ptDifferences(pt1,pt2);
            int[] ct = Demodulation.ct(pt, 0);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming7_4.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot2 = Plot.createAlfaBersPlot(bers,alfas,"FSK 7,4");




        int[] encode15_11 = Hamming15_11.encode(bits);
        B = encode15_11.length;
        Tb = Tc / B;

        fn = W / Tb;
        fn1 = (W + 1) / Tb;
        fn2 = (W + 2) / Tb;

        // ASC 15,11
        index=0;
        za = Modulation.za(A1,A2,encode15_11,fn,fs,Tb);
        xt = Modulation.xt(za, fn, fs, A, 0);
        h=660;

        for(double i = 0; i < 12; i += 0.3){
            double[] yt = Helpers.generateAndAddNoiseModified(xt,i, t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            int[] ct = Demodulation.ct(pt, h);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming15_11.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot3 = Plot.createAlfaBersPlot(bers,alfas,"ASK 15,11");


        //PSC 15,11
        index = 0;
        zp = Modulation.zp(encode15_11,fn,fs,Tb);
        xt1 = Modulation.xt(zp, fn, fs, A, Math.PI);
        for(double i = 0; i < 12; i += 0.3){
            double[] yt = Helpers.generateAndAddNoiseModified(xt1,i, t0, Tc);
            double[] pt = Demodulation.pt(yt, fs, Tb);
            int[] ct = Demodulation.ct(pt, 0);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming15_11.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot4 = Plot.createAlfaBersPlot(bers,alfas,"PSK 15,11");

        //FSC 15,11
        index=0;
        zf = Modulation.zf(encode15_11,fn1,fn2,fs,Tb);
        xt2 = Modulation.xt1(zf,fn1,fs);
        xt3 = Modulation.xt1(zf,fn2,fs);
        for(double i = 0; i < 12; i += 0.3){
            double[] yt1 = Helpers.generateAndAddNoiseModified(xt2,i, t0, Tc);
            double[] yt2 = Helpers.generateAndAddNoiseModified(xt3,i, t0, Tc);
            double[] pt1 = Demodulation.pt(yt1,fs,Tb);
            double[] pt2 = Demodulation.pt(yt2,fs,Tb);
            double[] pt  = Demodulation.ptDifferences(pt1,pt2);
            int[] ct = Demodulation.ct(pt, 0);
            int[] result = Helpers.changeToBits(ct, Tb, fs);
            int[] decode = Hamming15_11.decode(result);
            double ber = Helpers.BER(bits, decode);
//            String s = Helpers.convertFromBitsToASCII(decode);
//            System.out.println(s);
            bers[index] = ber;
            alfas[index] = i;
            index++;
        }
        XYSeries plot5 = Plot.createAlfaBersPlot(bers,alfas,"FSK 15,11");
        Plot.plot3(plot,plot1,plot2,"");
        Plot.plot3(plot3,plot4,plot5,"");
    }
}

