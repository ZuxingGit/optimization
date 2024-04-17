package zuxingCode;

public class Pixel {
    //a = alpha, r = red, g = green, b = blue
    int a=0, r=0, g=0, b=0;

    public Pixel() {
        this.a = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }
    
    public Pixel(int p) {
        this.a = (p >> 24) & 0xff;
        this.r = (p >> 16) & 0xff;
        this.g = (p >> 8) & 0xff;
        this.b = p & 0xff;
    }

    public int getARGB_Integer() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int getAlpha() {
        return a;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public void setARGB(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Pixel [a=" + a + ", r=" + r + ", g=" + g + ", b=" + b + "]";
    }
}
