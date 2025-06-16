//Author: Andrii Zhupanov 214A 55616

package transmisja;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Helpers {
    public static int[] convertFromASCItoBits(String word){
        int length = word.length() * 7;
        int[] result = new int[length];
        for(int i = 0; i < word.length();i++){
            int Char = word.charAt(i);
            for(int j = 0; j < 7; j++){
                result[i * 7 + (6 - j)] = Char & 1;
                Char = Char >> 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] res = convertFromASCItoBits("Hello");
        for(int i : res){
            System.out.print(i + " ");
        }
    }

    public static double BER(int[] bits, int[] demodulation){
        int error = 0;
        for(int i = 0; i < bits.length; i++){
            if(bits[i] != demodulation[i]){
                error++;
            }
        }

        double ber = ((double) error / (double) bits.length) * 100;
        return ber;
    };

    public static int[] changeToBits(int[] ct, double Tb, double fs){
        int bitTime = (int)(fs * Tb);
        int numBits = ct.length / bitTime;
        LinkedList<Integer> res = new LinkedList<>();
        for(int i = 0; i < numBits; i++) {
            int w = 0;
            int n = 0;
            for(int y = i * bitTime; y < (i + 1) * bitTime; y++ ) {
                if(ct[y] != 0){
                    w++;
                }
                else{
                    n++;
                }
            }
            if(w > n){
                res.add(1);
            }
            else{
                res.add(0);
            }
        }

        int[] bits = new int[res.size()];
        for(int i = 0; i < bits.length; i++) {
            bits[i] = res.get(i);
        }
        return bits;
    };

    public static double[] generateAndAddNoise(double[] xt ,double alfa){
        double[] res = new double[xt.length];
        Random rand = new Random();
        double[] noise = DoubleStream.generate(() -> rand.nextDouble() * 2 - 1).limit(xt.length).toArray();
        for(int i = 0; i < xt.length; i++){
            res[i] = xt[i] + alfa * noise[i];
            //System.out.println(noise[i] + " ");
        }
        //System.out.println();
        return res;
    }

    public static double[][] generateAndAddNoise(double[] xt, double[] xt1, double alfa){
        double[] res1 = new double[xt.length];
        double[] res2 = new double[xt1.length];
        Random rand = new Random();

        for(int i = 0; i < xt.length; i++){
            double noise1 = rand.nextDouble() * 2 - 1;
            double noise2 = rand.nextDouble() * 2 - 1;
            res1[i] = xt[i] + alfa * noise1;
            res2[i] = xt1[i] + alfa * noise2;
        }
        return new double[][]{res1, res2};
    }



    public static double[] generateAndAddNoiseModified(double[] xt ,double beta, double t0, double Tc){
        double[] res = new double[xt.length];
        for(int i = 0; i < xt.length; i++){
            double t = (i * Tc) / xt.length;
            res[i] = xt[i] * Math.exp(-beta * t) * Math.max(0, 1 - (t / t0));
            //System.out.println(noise[i] + " ");
        }
        //System.out.println();
        return res;
    }

    public static String convertFromBitsToASCII(int[] bits) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < bits.length; i += 7) {
            int asciiValue = 0;
            for (int j = 0; j < 7; j++) {
                asciiValue = (asciiValue << 1) | bits[i + j];
            }
            result.append((char) asciiValue);
        }

        return result.toString();
    }

}