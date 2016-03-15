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
import javafx.scene.input.MouseEvent;

import javax.xml.ws.FaultAction;


public class Editor extends Application {
    static String filename;
    protected static Group root;
    public static FastLinkedList2<Text> textToDisplay;
    public static int windowWidth;
    int windowHeight;
    public static Render renderObject;
    public static UndoRedoQueue undoRedoObject;
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        final int WINDOW_SIZE = 500;
        windowWidth = WINDOW_SIZE;
        windowHeight = WINDOW_SIZE;
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.WHITE);
        renderObject = new Render(root, null, windowWidth);
        root.getChildren().add(renderObject.cursor);
        undoRedoObject = new UndoRedoQueue();
        renderObject.makeRectangleColorChange();
        textToDisplay = OpenSave.open(filename);
        undoRedoObject.undoQueue = new FastLinkedList2<>();
        undoRedoObject.undoActionQueue = new FastLinkedList2<>();
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler();
        EventHandler<MouseEvent> mouseEventHandler =
                new MouseClickEventHandler(root, windowWidth, renderObject);
        scene.setOnMouseClicked(mouseEventHandler);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        primaryStage.setTitle("Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Expected usage: Editor <filename>");
            System.exit(1);
        }
        filename = args[0];
        launch(args);
    }

    private class MouseClickEventHandler implements EventHandler<MouseEvent> {
        /** A Text object that will be used to print the current mouse position. */
        Group ROOT;
        int windowWidth;
        Render renderObject;


        MouseClickEventHandler(final Group root, int width, Render renderer) {
            ROOT = root;
            windowWidth = width;
            renderObject = renderer;
        }


        @Override
        public void handle(MouseEvent mouseEvent) {
            // Because we registered this EventHandler using setOnMouseClicked, it will only called
            // with mouse events of type MouseEvent.MOUSE_CLICKED.  A mouse clicked event is
            // generated anytime the mouse is pressed and released on the same JavaFX node.
            double mousePressedX = mouseEvent.getX() - renderObject.charWidth;
            double mousePressedY = mouseEvent.getY() - (renderObject.charHeight / 2);
            renderObject.cursorToPosition(mousePressedX, mousePressedY, ROOT, windowWidth);
        }
    }

    public class KeyEventHandler implements EventHandler<KeyEvent> {
        int INITX = 5;
        int INITY = 0;
        private Text eventText;
        private static final int FONTSIZE = 12;
        private int fontSize = FONTSIZE;

        private String fontName = "Verdana";

        public KeyEventHandler() {
            fontSize = (int) textToDisplay.cyclePointer.item.getFont().getSize();
            fontName = textToDisplay.cyclePointer.item.getFont().getName();
            renderObject.render();
            // Initialize some empty
            // capitalization.text and add it to root so that it will be displayed.
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            // All new Nodes need to be added to the root in order to be displayed.
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED && !keyEvent.isShortcutDown()) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                eventText = new Text(INITX, INITY, "");
                eventText.setTextOrigin(VPos.TOP);
                eventText.setFont(Font.font(fontName, fontSize));
                String eventChar = keyEvent.getCharacter();
                System.out.println(eventChar);
                if (eventChar.equals("\r")) {
                    eventText.setText("");
                    textToDisplay.addChar(eventText);
                    renderObject.render();
                    keyEvent.consume();
                }
                else if (eventChar.length() > 0 && eventChar.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length,
                    // as well as the backspace key, which is
                    // represented as a character of value = 8 on Windows.
                    eventText.setText(eventChar);
                    textToDisplay.addChar(eventText);
                    renderObject.render();
                    keyEvent.consume();
                }
                root.getChildren().add(eventText);
                undoRedoObject.redoQueue = new FastLinkedList2<>();
                undoRedoObject.redoActionQueue = new FastLinkedList2<>();

            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
            /* if (code == KeyCode.UP) {
                fontSize += 5;
                displayText.setFont(Font.font(fontName, fontSize));
            } else if (code == KeyCode.DOWN) {
                fontSize = Math.max(0, fontSize - 5);
                displayText.setFont(Font.font(fontName, fontSize));
            } */ if (code == KeyCode.BACK_SPACE) {
                    Text deleted = textToDisplay.deleteChar().item;
                    root.getChildren().remove(deleted);
                    undoRedoObject.redoQueue = new FastLinkedList2<>();
                    undoRedoObject.redoActionQueue = new FastLinkedList2<>();
                    renderObject.render();
                }
                else if (code == KeyCode.LEFT) {
                    if (textToDisplay.currentNode != textToDisplay.sentinel) {
                        textToDisplay.currentNode = textToDisplay.currentNode.prev;
                        textToDisplay.currentPos --;
                        renderObject.render();
                    }
                }
                else if (code == KeyCode.RIGHT) {
                    if (textToDisplay.currentNode.next != textToDisplay.sentinel) {
                        textToDisplay.currentNode = textToDisplay.currentNode.next;
                        textToDisplay.currentPos ++;
                        renderObject.render();
                    }
                }
                else if (code == KeyCode.UP) {
                    if (!textToDisplay.isEmpty() && textToDisplay.currentNode != textToDisplay.sentinel) {
                        //if (textToDisplay.currentNode == Render.lineIndex.get(renderObject.currentLine).prev) {
                       //     textToDisplay.currentNode = Render.lineIndex.get(renderObject.currentLine - 1);
                       // }
                       // else {
                            double x = textToDisplay.currentNode.item.getX();
                            double textHeight = textToDisplay.cyclePointer.item.getLayoutBounds().getHeight();
                            double y = textToDisplay.currentNode.item.getY() - textHeight;
                            renderObject.cursorToPosition(x, y, root, windowWidth);
                        //}
                    }
                }
                else if (code == KeyCode.DOWN) {
                    FastLinkedList2<Text>.Node lastLineNode = Render.lineIndex.get(Render.lineIndex.size() - 1);
                    double lastLineY = lastLineNode.item.getY();
                    if (textToDisplay.currentNode.item.getY() != lastLineY) {
                        if (textToDisplay.currentNode == textToDisplay.sentinel) {
                            textToDisplay.currentNode = textToDisplay.currentNode.next;
                        }
                        //if (textToDisplay.currentNode != Render.lineIndex.get(renderObject.currentLine)) {
                            double x = textToDisplay.currentNode.item.getX();
                            double textHeight = textToDisplay.cyclePointer.item.getLayoutBounds().getHeight();
                            double y = textToDisplay.currentNode.item.getY() + textHeight;
                            renderObject.cursorToPosition(x, y, root, windowWidth);
                        //}
                        //else {
                          //  textToDisplay.currentNode = Render.lineIndex.get(renderObject.currentLine + 1);
                        //}
                    }
                }
                else if (keyEvent.isShortcutDown()) {
                    if (code == KeyCode.S) {
                        OpenSave.save(filename, textToDisplay);
                    }
                    else if (code == KeyCode.Z) {
                        undoRedoObject.undo();
                        renderObject.render();
                    }
                    else if (code == KeyCode.Y) {
                        undoRedoObject.redo();
                        renderObject.render();
                    }
                }
                keyEvent.consume();
            }
        }

    }
}
