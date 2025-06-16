// Author: Andrii Zhupanov 214A 55616

package transmisja;

public class FFT {

    public static Complex[] calculateFFT(double[] signal) {
        int N = signal.length;
        int n = signal.length;

        while (n % 2 == 0) {
            n /= 2;
        }
        if (n != 1){
            int power = 1;
            while (power < N) {
                power *= 2;
            }
            double[] signal2 = new double[power];
            for (int i = 0; i < N; i++) {
                signal2[i] = signal[i];
            }
            for (int i = N; i < power; i++) {
                signal2[i] = 0;
            }

            Complex[] complexSignal = new Complex[power];
            for (int i = 0; i < power; i++) {
                complexSignal[i] = new Complex(signal2[i], 0.0);
            }
            return calculateFFT(complexSignal);
        }
        else{
            Complex[] complexSignal = new Complex[N];
            for (int i = 0; i < N; i++) {
                complexSignal[i] = new Complex(signal[i], 0.0);
            }
            return calculateFFT(complexSignal);
        }
    }

    private static Complex[] calculateFFT(Complex[] signal) {
        int N = signal.length;
        if (N == 1) return new Complex[]{signal[0]};

        Complex[] even = new Complex[N / 2];
        Complex[] odd = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = signal[2 * k];
            odd[k] = signal[2 * k + 1];
        }

        Complex[] q = calculateFFT(even);
        Complex[] r = calculateFFT(odd);

        Complex[] y = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = add(q[k], multiply(wk, r[k]));
            y[k + N / 2] = subtract(q[k], multiply(wk, r[k]));
        }

        return y;
    }

    private static Complex add(Complex a, Complex b) {
        return new Complex(a.re + b.re, a.im + b.im);
    }

    private static Complex subtract(Complex a, Complex b) {
        return new Complex(a.re - b.re, a.im - b.im);
    }

    private static Complex multiply(Complex a, Complex b) {
        return new Complex(a.re * b.re - a.im * b.im, a.re * b.im + a.im * b.re);
    }
}