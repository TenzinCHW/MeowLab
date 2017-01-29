package ESC.Week1;

/**
 * Created by HanWei on 26/1/2017.
 */
public class ComplexNumber {
    private double real;
    private double fake;
    private double mag;
    private double angle;

    public ComplexNumber(double r, double im, boolean polar) {
        if (polar) {
            mag = r;
            angle = im;
            calcCart();
        } else {
            real = r;
            fake = im;
            calcPolar();
        }
    }

    public ComplexNumber() {
        this(0.0,0.0, true);
    }

    private void calcPolar() {
        mag = Math.sqrt(Math.pow(real, 2) + Math.pow(fake, 2));
        angle = Math.atan(fake/real);
    }

    private void calcCart() {
        real = mag * Math.cos(angle);
        fake = mag * Math.sin(angle);
    }

    ComplexNumber add(ComplexNumber one) {
        ComplexNumber result = new ComplexNumber();
        result.real = this.real + one.real;
        result.fake = this.fake + one.fake;
        result.calcPolar();
        return result;
    }

    ComplexNumber subtract(ComplexNumber one) {
        ComplexNumber result = new ComplexNumber();
        result.real = this.real - one.real;
        result.fake = this.fake - one.fake;
        result.calcPolar();
        return result;
    }

    ComplexNumber multiply(ComplexNumber one) {
        ComplexNumber result = new ComplexNumber();
        result.real = this.real * one.real - this.fake * one.fake;
        result.fake = this.real * one.fake + this.fake * one.real;
        result.calcPolar();
        return result;
    }

    ComplexNumber divide(ComplexNumber one) {
        ComplexNumber result = new ComplexNumber();
        ComplexNumber numerator = this.multiply(conjugate(one));
        ComplexNumber denominator = one.multiply(conjugate(one));
        result.real = numerator.real/denominator.real;
        result.fake = numerator.fake/denominator.real;
        result.calcPolar();
        return result;
    }

    static ComplexNumber conjugate(ComplexNumber complex) {
        ComplexNumber result = new ComplexNumber(complex.real, - complex.fake, false);
        result.calcPolar();
        return result;
    }

    @Override
    public String toString() {
        return "Real part: " + real + " Imaginary part: " + fake +
                "\n Magnitude: " + mag + " Angle: " + angle;

    }
}
