// Author: Andrii Zhupanov 214A 55616

package transmisja;

public class Complex {
    public final double re;
    public final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public String toString() {
        return String.format("(%.2f, %.2fi)", re, im);
    }

    public double mK(){
        return Math.sqrt(re*re + im*im);
    }
}
