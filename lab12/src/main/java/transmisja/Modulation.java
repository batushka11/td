//Author: Andrii Zhupanov 214A 55616

package transmisja;

public class Modulation {
    public static double[] za(double A1, double A2, int[] Bits, double fn, double fs, double Tb){
        int bitTime = (int)(fs * Tb);
        double[] signal = new double[Bits.length * bitTime];

        for(int n = 0; n < signal.length; n++) {
            double t = (double) n / fs;
            double f = 0;
            if(Bits[ n / bitTime] == 0){
                f = A1 * Math.sin(2 * Math.PI * t * fn);
            }
            else {
                f = A2 * Math.sin(2 * Math.PI * t * fn);
            }
            signal[n] = f;
        }
        return signal;
    }

    public static double[] zf(int[] Bits, double fn1, double fn2, double fs, double Tb){
        int bitTime = (int)(fs * Tb);
        double[] signal = new double[Bits.length * bitTime];

        for(int n = 0; n < signal.length; n++) {
            double t = (double) n / fs;
            double s;
            if(Bits[n / bitTime] == 0){
                s = Math.sin(2 * Math.PI * t * fn1);
            }
            else {
                s = Math.sin(2 * Math.PI * t * fn2);
            }
            signal[n] = s;
        }
        return signal;
    }

    public static double[] zp(int[] Bits, double fn, double fs, double Tb){
        int bitTime = (int)(fs * Tb);
        double[] signal = new double[Bits.length * bitTime];

        for(int n = 0; n < signal.length; n++) {
            double t = (double) n / fs;
            double f = 0;
            if(Bits[n / bitTime] == 0){
                f = Math.sin(2 * Math.PI * t * fn);
            }
            else {
                f = Math.sin(2 * Math.PI * t * fn + Math.PI);
            }
            signal[n] = f;
        }
        return signal;
    }

    public static double[] xt(double[] signal, double fn, double fs, double A, double fi){
        double[] res = new double[signal.length];
        for(int n = 0; n < signal.length; n++) {
            double t = (double) n / fs;
            res[n] = signal[n] * A * Math.sin(2 * Math.PI * t * fn + fi);
        }

        return res;
    }

    public static double[] xt1(double[] signal, double fn, double fs){
        double[] res = new double[signal.length];
        for(int n = 0; n < signal.length; n++) {
            double t = (double) n / fs;
            res[n] = signal[n] * Math.sin(2 * Math.PI * t * fn);
        }

        return res;
    }
}
