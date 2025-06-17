package transmisja;

import org.jfree.data.xy.XYSeries;

import java.util.Arrays;

public class czesc1 {
    public static void main(String[] args) {

        //int[] bits = Helpers.convertFromASCItoBits("rect");
        int[] bits = {
                1,0,1,0,0,1,1,0,1,1,1,
                0,1,1,1,1,0,0,1,0,1,0,
                1,1,0,0,0,1,1,1,0,1,0,
                0,0,1,1,0,1,1,0,1,0,0
        };
        //int[] bits = Helpers.convertFromASCItoBits("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        int[] encode7_4 = Hamming7_4.encode(bits);

        double A = 5;
        double A1 = 1;
        double A2 = 0.5;

        double B = encode7_4.length;
        double Tc = 1;
        double Tb = Tc / B;
        double W = 2;

        double fn = W / Tb;
        double fs = 1000;
        double h = 0.007710 * (44 / B);
        double fn1 = (W + 1) / Tb;
        double fn2 = (W + 2) / Tb;

        int index = 0;
        double[] alfas = new double[26];
        double[] bers = new double[26];

        //ASC 7,4
        double[] za = Modulation.za(A1,A2,encode7_4,fn,fs,Tb);
        double[] xt = Modulation.xt(za, fn, fs, A, 0);

        for(double i = 0; i < 30; i += 1.2){
            double[] yt = Helpers.generateAndAddNoise(xt,i);
            double[] pt = Demodulation.pt(yt, fs, Tb);
//            if(i == 2.7)
//                Plot.generatePlot(pt.length,fs,pt,"Step 3 - ASK");
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
        for(double i = 0; i < 30; i += 1.2){
            double[] yt = Helpers.generateAndAddNoise(xt1,i);
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
        for(double i = 0; i < 30; i += 1.2){
//            double[][] noise = Helpers.generateAndAddNoise(xt2, xt3, i);
//            double[] yt1 = noise[0];
//            double[] yt2 = noise[1];
            double[] yt1 = Helpers.generateAndAddNoise(xt2,i);
            double[] yt2 = Helpers.generateAndAddNoise(xt3,i);
//            if(i==0.3){
//                System.out.println("yt1[10] = " + yt1[10] + ", xt2[10] = " + xt2[10]);
//                System.out.println("yt2[10] = " + yt2[10] + ", xt3[10] = " + xt3[10]);
//            }
            double[] pt1 = Demodulation.pt(yt1,fs,Tb);
            double[] pt2 = Demodulation.pt(yt2,fs,Tb);
            double[] pt  = Demodulation.ptDifferences(pt1,pt2);
//            System.out.println(Arrays.toString(pt));
//            Plot.generatePlot(pt.length,fs,pt,"Step 4 - pt");
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
        Plot.plot3(plot,plot1,plot2,"");

//        int[] encode15_11 = Hamming15_11.encode(bits);
//        B = encode15_11.length;
//        Tb = Tc / B;
//
//        fn = W / Tb;
//        fn1 = (W + 1) / Tb;
//        fn2 = (W + 2) / Tb;
//        h = 680;
//
//        // ASC 15,11
//        index=0;
//        za = Modulation.za(A1,A2,encode15_11,fn,fs,Tb);
//        xt = Modulation.xt(za, fn, fs, A, 0);
//
//        for(double i = 0; i < 30; i += 1.2){
//            double[] yt = Helpers.generateAndAddNoise(xt,i);
//            double[] pt = Demodulation.pt(yt, fs, Tb);
//            int[] ct = Demodulation.ct(pt, h);
////            Plot.generatePlot(pt.length,fs,pt,"Step 3 - ASK");
//            int[] result = Helpers.changeToBits(ct, Tb, fs);
//            int[] decode = Hamming15_11.decode(result);
//            double ber = Helpers.BER(bits, decode);
////            String s = Helpers.convertFromBitsToASCII(decode);
////            System.out.println(s);
//            bers[index] = ber;
//            alfas[index] = i;
//            index++;
//        }
//        XYSeries plot3 = Plot.createAlfaBersPlot(bers,alfas,"ASK 15,11");
//
//
//        //PSC 15,11
//        index = 0;
//        zp = Modulation.zp(encode15_11,fn,fs,Tb);
//        xt1 = Modulation.xt(zp, fn, fs, A, Math.PI);
//        for(double i = 0; i < 30; i += 1.2){
//            double[] yt = Helpers.generateAndAddNoise(xt1,i);
//            double[] pt = Demodulation.pt(yt, fs, Tb);
//            int[] ct = Demodulation.ct(pt, 0);
//            int[] result = Helpers.changeToBits(ct, Tb, fs);
//            int[] decode = Hamming15_11.decode(result);
//            double ber = Helpers.BER(bits, decode);
////            String s = Helpers.convertFromBitsToASCII(decode);
////            System.out.println(s);
//            bers[index] = ber;
//            alfas[index] = i;
//            index++;
//        }
//        XYSeries plot4 = Plot.createAlfaBersPlot(bers,alfas,"PSK 15,11");
//
//        //FSC 15,11
//        index=0;
//        zf = Modulation.zf(encode15_11,fn1,fn2,fs,Tb);
//        xt2 = Modulation.xt1(zf,fn1,fs);
//        xt3 = Modulation.xt1(zf,fn2,fs);
//        for(double i = 0; i < 30; i += 1.2){
////            double[][] noise = Helpers.generateAndAddNoise(xt2, xt3, i);
////            double[] yt1 = noise[0];
////            double[] yt2 = noise[1];
//            double[] yt1 = Helpers.generateAndAddNoise(xt2,i);
//            double[] yt2 = Helpers.generateAndAddNoise(xt3,i);
//            double[] pt1 = Demodulation.pt(yt1,fs,Tb);
//            double[] pt2 = Demodulation.pt(yt2,fs,Tb);
//            double[] pt  = Demodulation.ptDifferences(pt1,pt2);
////            Plot.generatePlot(pt.length,fs,pt,"Step 4 - pt");
//            int[] ct = Demodulation.ct(pt, 0);
//            int[] result = Helpers.changeToBits(ct, Tb, fs);
//            int[] decode = Hamming15_11.decode(result);
//            double ber = Helpers.BER(bits, decode);
////            String s = Helpers.convertFromBitsToASCII(decode);
////            System.out.println(s);
//            bers[index] = ber;
//            alfas[index] = i;
//            index++;
//        }
//        XYSeries plot5 = Plot.createAlfaBersPlot(bers,alfas,"FSK 15,11");
//
//        Plot.plot3(plot3,plot4,plot5,"");
   }
}

