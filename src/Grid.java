import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;


import java.awt.*;
import java.io.*;

public class Grid implements KeyboardHandler {

    //PROPERTIES
    private int cellSize;
    private int width;
    private int height;
    public Keyboard keyboard;
    private int xPosition;
    private int yPosition;
    public Rectangle dot;
    public Color color;
    public int cols;
    public int rows;
    private Rectangle[] coordinates;
    private int index;

    //CONSTRUCTORS
    public Grid() {
        this.keyboard = new Keyboard(this);
        addKeyboardDown();
        addKeyboardLeft();
        addKeyboardRight();
        addKeyboardUp();
        addKeyboardPaint();
        addKeyboardReset();
        addKeyboardErase();
        addKeyboardSave();
        addKeyboardLoad();
    }

    //GETTERS & SETTERS


    //METHODS
    public void draw(Color color, int cols, int rows, int cellSize) {
        this.yPosition = cellSize;
        this.xPosition = cellSize;
        this.width = cols * cellSize;
        this.height = rows * cellSize;
        this.cellSize = cellSize;
        int xPosition = cellSize;
        int yPosition = cellSize;
        int colN = 0;
        int rowN = 0;
        this.color = color;
        this.cols = cols;
        this.rows = rows;
        this.coordinates = new Rectangle[cols * rows];

        while (rowN < rows) {
            while (colN < cols) {
                Rectangle r = new Rectangle(xPosition + colN * cellSize, yPosition + rowN * cellSize, cellSize, cellSize);
                r.setColor(color);
                r.draw();
                colN++;
            }
            rowN++;
            colN = 0;
            xPosition = cellSize;
        }
    }

    public void pointer() {
        boolean blink = true;
        dot = new Rectangle(cellSize, cellSize, cellSize, cellSize);

        while (blink == true) {

            dot.setColor(Color.PINK);
            dot.fill();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            dot.draw();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void moveRight() {
        if (xPosition >= width) {
            return;
        }
        xPosition += cellSize;
        dot.delete();
        dot = new Rectangle(xPosition, yPosition, cellSize, cellSize);
    }

    public void moveUp() {
        if (yPosition <= cellSize) {
            return;
        }
        yPosition -= cellSize;
        dot.delete();
        dot = new Rectangle(xPosition, yPosition, cellSize, cellSize);
    }

    public void moveDown() {
        if (yPosition >= height) {
            return;
        }
        yPosition += cellSize;
        dot.delete();
        dot = new Rectangle(xPosition, yPosition, cellSize, cellSize);
    }

    public void moveLeft() {
        if (xPosition <= cellSize) {
            return;
        }
        xPosition -= cellSize;
        dot.delete();
        dot = new Rectangle(xPosition, yPosition, cellSize, cellSize);
    }

    public void paint() {
        Rectangle r = new Rectangle(xPosition, yPosition, cellSize, cellSize);
        r.setColor(Color.BLACK);
        r.fill();
        coordinates[index] = r;
        index++;
    }

    public void saveBoard() {
        String result = "";

        BufferedWriter bWriter = null;
        try {
            bWriter = new BufferedWriter(new FileWriter("coordinates x y"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i] == null) {
                break;
            }
            result += coordinates[i].getX() + " " + coordinates[i].getY() + "\n";
        }

        try {
            bWriter.write(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void eraseBoard() {
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i] == null) {
                return;
            }
            coordinates[i].delete();
        }
    }

    public String loadSavedBoard() {
        String line = "";
        String result = "";
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader("coordinates x y"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!((line = bReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result += line;
            }

        try {
            bReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.split("1");
        System.out.println(result);
        return result;
    }

    public void test() {
        String text = "texto aqui";
        System.out.println(text);
        text.split("x");
        System.out.println(text);
    }

    //KEYBOARD
    public void addKeyboardPaint() {
        KeyboardEvent paint = new KeyboardEvent();
        paint.setKey(KeyboardEvent.KEY_A);
        paint.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(paint);
    }

    public void addKeyboardLeft() {
        KeyboardEvent moveLeft = new KeyboardEvent();
        moveLeft.setKey(KeyboardEvent.KEY_LEFT);
        moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveLeft);
    }

    public void addKeyboardRight() {
        KeyboardEvent moveRight = new KeyboardEvent();
        moveRight.setKey(KeyboardEvent.KEY_RIGHT);
        moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveRight);
    }

    public void addKeyboardUp() {
        KeyboardEvent moveUp = new KeyboardEvent();
        moveUp.setKey(KeyboardEvent.KEY_UP);
        moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveUp);
    }

    public void addKeyboardDown() {
        KeyboardEvent moveDown = new KeyboardEvent();
        moveDown.setKey(KeyboardEvent.KEY_DOWN);
        moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveDown);
    }

    public void addKeyboardReset() {
        KeyboardEvent resetBoard = new KeyboardEvent();
        resetBoard.setKey(KeyboardEvent.KEY_R);
        resetBoard.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(resetBoard);
    }

    public void addKeyboardErase() {
        KeyboardEvent eraseBoard = new KeyboardEvent();
        eraseBoard.setKey(KeyboardEvent.KEY_E);
        eraseBoard.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(eraseBoard);
    }

    public void addKeyboardSave() {
        KeyboardEvent saveBoard = new KeyboardEvent();
        saveBoard.setKey(KeyboardEvent.KEY_S);
        saveBoard.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(saveBoard);
    }

    public void addKeyboardLoad() {
        KeyboardEvent loadSavedBoard = new KeyboardEvent();
        loadSavedBoard.setKey(KeyboardEvent.KEY_D);
        loadSavedBoard.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(loadSavedBoard);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int keyPressed = keyboardEvent.getKey();
        switch (keyPressed) {
            case KeyboardEvent.KEY_DOWN:
                moveDown();
                break;
            case KeyboardEvent.KEY_UP:
                moveUp();
                break;
            case KeyboardEvent.KEY_LEFT:
                moveLeft();
                break;
            case KeyboardEvent.KEY_RIGHT:
                moveRight();
                break;
            case KeyboardEvent.KEY_A:
                paint();
                break;
            case KeyboardEvent.KEY_D:
                loadSavedBoard();
                break;
            case KeyboardEvent.KEY_E:
                eraseBoard();
                break;
            case KeyboardEvent.KEY_S:
                saveBoard();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }
}