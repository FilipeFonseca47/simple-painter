import org.academiadecodigo.simplegraphics.graphics.Color;

public class Main {
    public static void main(String[] args) {

        Grid grid = new Grid();
        grid.test();
        grid.draw(Color.BLACK, 30, 30, 20);

        grid.pointer();

        grid.test();


    }
}
