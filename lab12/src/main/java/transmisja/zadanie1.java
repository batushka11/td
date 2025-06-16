package transmisja;

public class zadanie1 {
    public static void main(String[] args) {
        int[] bits = {0,0,0,1};
        //int[] bits = Helpers.convertFromASCItoBits("Hello World!");
        int[] encode7_4 = Hamming7_4.encode(bits);
        double A = 5;
        double A1 = 10;
        double A2 = 20;

        double B = encode7_4.length;
        double Tc = 2;
        double Tb = Tc / B;
        double W = 2;

        double fn = W / Tb;
        double fs = 2000;
        double h = 9000;

        //Dla ASK
        double za[] = Modulation.za(A1,A2,encode7_4,fn,fs,Tb);
        double xt[] = Modulation.xt(za, fn, fs, A, 0);
        double pt[] = Demodulation.pt(xt, fs, Tb);
        int ct[] = Demodulation.ct(pt, h);
        int[] result = Helpers.changeToBits(ct, Tb, fs);
        int[] decode7_4 = Hamming7_4.decode(result);



        //Dla PSK
        double zp[] = Modulation.zp(bits,fn,fs,Tb);
        double xt1[] = Modulation.xt(zp, fn, fs, A, Math.PI);
        double pt1[] = Demodulation.pt(xt1, fs, Tb);
        int ct1[] = Demodulation.ct(pt1, 0);


//        int recreateBits[] = Helpers.changeToBits(ct1,Tb,fs);
        for(int i = 0; i < decode7_4.length; i++){
            System.out.print(decode7_4[i]+" ");
        }
//
//        System.out.println();
//        int recreateBits1[] = Helpers.changeToBits(ct,Tb,fs);
//        for(int i = 0; i < recreateBits1.length; i++){
//            System.out.print(recreateBits1[i]+" ");
//        }

//        double zp[] = Modulation.zp(bits,fn,fs,Tb);

//        Plot.generatePlot(za.length,fs,za,"ASK");
//        Plot.generatePlot(zf.length,fs,zf,"FSK");
//        Plot.generatePlot(zp.length,fs,zp,"PSK");

    }
}
