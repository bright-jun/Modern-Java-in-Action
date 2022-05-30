package exercise.chapter13;

public class Ellipse implements Resizable {

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setAbsoluteSize(int width, int height) {

    }

    public static void main(String[] args) {
        Ellipse ellipse = new Ellipse();
        ellipse.setAbsoluteSize(1, 2);
        ellipse.setRelativeSize(1, 2);

    }
}
