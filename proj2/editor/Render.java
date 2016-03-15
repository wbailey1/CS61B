package editor;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

/**
 * Created by William on 2016-03-05.
 */
public class Render {
    static int INITX = 5;
    static int INITY = 0;
    public static Rectangle cursor;
    private Group ROOT;
    private FastLinkedList2<Text> text;
    private int windowWidth;
    public static LineArray<FastLinkedList2<Text>.Node> lineIndex;
    int charHeight;
    int charWidth;
    int currentLine;

    public Render(Group root, FastLinkedList2<Text> textToDisplay, int WW) {
        ROOT = root;
        text = textToDisplay;
        windowWidth = WW;
        cursor = new Rectangle(5, 0);
        cursor.setHeight(20);
        cursor.setWidth(1);
        cursor.setX(5);
        cursor.setY(0);
    }
    public void updatePositions() {
        int xcord = INITX;
        int ycord = INITY;
        int wordSize = 0;
        int position = 0;
        int line = 0;
        lineIndex = new LineArray<FastLinkedList2<Text>.Node>();
        FastLinkedList2.Node lastSpace = null;
        Text textItem;
        textItem = text.cyclePointer.item;
        lineIndex.addLast(text.cyclePointer);
        charHeight = (int) Math.round(textItem.getLayoutBounds().getHeight());
        while (textItem != null) {
            position ++;
            if (text.cyclePointer == text.currentNode) {
                text.currentPos = position;
                currentLine = line;
            }
            if (textItem.getText().equals("")) {
                wordSize = 0;
                xcord = 5;
                ycord = ycord + charHeight;
                textItem.setX(xcord);
                textItem.setY(ycord);
                lineIndex.addLast(text.cyclePointer.next);
                text.cyclePointer = text.cyclePointer.next;
                line ++;
            }
            else {
                if (textItem.getText().equals(" ")) {
                    lastSpace = text.cyclePointer.next;
                    wordSize = 0;
                }
                textItem.setX(xcord);
                textItem.setY(ycord);
                charWidth = (int) Math.round(textItem.getLayoutBounds().getWidth());
                xcord = xcord + charWidth;
                wordSize += charWidth;
                if (xcord >= windowWidth - 5) {
                    xcord = 5;
                    ycord = ycord + charHeight;
                    line ++;
                    if (wordSize >= windowWidth - 10) {
                        text.cyclePointer = text.cyclePointer.next;
                    } else {
                        text.cyclePointer = lastSpace;
                    }
                    if (text.cyclePointer.next.item != null) {
                        lineIndex.addLast(text.cyclePointer);
                    }
                } else {
                    text.cyclePointer = text.cyclePointer.next;
                }
            }
            textItem = text.cyclePointer.item;
        }
        text.cyclePointer = text.sentinel.next;
    }
    private class CursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.TRANSPARENT};

        CursorBlinkEventHandler() {
            changeColor();
        }

        private void changeColor() {
            cursor.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }
    public void updateCursor() {
        // Figure out the size of the current text.
        if (text.currentPos == 0) {
            cursor.setX(5);
            cursor.setY(0);
        }
        else if (text.currentNode.item != null) {
            Text textItem = text.currentNode.item;
            double textWidth = Math.round(textItem.getLayoutBounds().getWidth());
            cursor.setHeight(charHeight);
            cursor.setWidth(1);
            cursor.setX(textItem.getX() + textWidth);
            cursor.setY(textItem.getY());
            if (text.currentNode.next.item != null && !textItem.getText().equals("") && text.currentNode.next.item.getX() == 5 || cursor.getX() > windowWidth - 5) {
                cursor.setX(5);
                cursor.setY(textItem.getY() + charHeight);
            }
        }
    }

    public void makeRectangleColorChange() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        CursorBlinkEventHandler cursorChange = new CursorBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void render() {
        ROOT = Editor.root;
        text = Editor.textToDisplay;
        windowWidth = Editor.windowWidth;
        updatePositions();
        updateCursor();
    }
    public void cursorToPosition(double x, double y, Group root, int WW) {
        boolean done = false;
        int i = 0;
        if (lineIndex.size() > 1 && y > 0) {
            i = (int) Math.round(y / charHeight);
        }
        text.cyclePointer = lineIndex.get(i);
        if (x < 5 + charWidth) {
            text.currentNode = text.cyclePointer.prev;
        }
        else {
            if (!text.cyclePointer.item.getText().equals("")) {
                while (!done && text.cyclePointer.next.item != null && !lineIndex.contains(text.cyclePointer.next.next)) {
                    System.out.println(text.cyclePointer.item.getText());
                    if (Math.abs(text.cyclePointer.next.item.getX() - x) < Math.abs(text.cyclePointer.item.getX() - x)) {
                        text.cyclePointer = text.cyclePointer.next;
                    } else {
                        done = true;
                    }
                }
            }
            if (text.cyclePointer.item.getText().equals("")) {
                text.currentNode = text.cyclePointer.prev;
            } else {
                text.currentNode = text.cyclePointer;
            }
        }
        text.cyclePointer = text.sentinel.next;
        render();
    }
}
