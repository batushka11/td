// Author: Andrii Zhupanov 214A 55616

package transmisja;

import java.util.LinkedList;

public class Demodulation {
    public static double[] pt(double[] xt, double fs, double Tb){
        int bitTime = (int)(fs * Tb);
        int numBits = xt.length / bitTime;
        LinkedList<Double> res = new LinkedList<>();
        for(int i = 0; i < numBits; i++) {
            double sum = 0;
            for(int y = i * bitTime; y < (i + 1) * bitTime; y++ ) {
                sum += xt[y];
                res.add(sum);
            }
        }

        double[] pt = new double[res.size()];
        for(int i = 0; i < pt.length; i++) {
            pt[i] = res.get(i);
        }
        return pt;
    };

    public static double[] ptDifferences(double[] pt1, double[] pt2){
        double[] bits = new double[pt1.length];
        for(int i = 0; i < pt1.length; i++){
            bits[i] = pt2[i] - pt1[i];
        }

        return bits;
    }

    public static int[] ct(double[] pt, double h) {
        int[] ct = new int[pt.length];
        for (int i = 0; i < pt.length; i++) {
            if (pt[i] > h) {
                ct[i] = 1;
            } else {
                ct[i] = 0;
            }
        }
        return ct;
    }

}
