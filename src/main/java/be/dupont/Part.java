package be.dupont;

public class Part {
    private int length;
    private int width;

    public Part(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public Part() {
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "be.dupont.Part{" +
                "length=" + length +
                ", width=" + width +
                '}';
    }
}
