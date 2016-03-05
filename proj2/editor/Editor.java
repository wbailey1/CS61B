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



public class Editor extends Application {
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;

        /** The Text to display on the screen. */
        private static final int SIZE = 250;
        private Text displayText = new Text(SIZE, SIZE, "");
        FastLinkedList2<Text> textToDisplay = new FastLinedList2<Text>();
        private static final int FONTSIZE = 20;
        private int fontSize = FONTSIZE;

        private String fontName = "Verdana";

        private void removeLast() {
            if (!textToDisplay.isEmpty()) {
                textToDisplay.deleteChar();
            }
        }

        public KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            textCenterX = 0;
            textCenterY = 0;


            // Initialize some empty
            // capitalization.text and add it to root so that it will be displayed.
            displayText = new Text(textCenterX, textCenterY, "");
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));

            // All new Nodes need to be added to the root in order to be displayed.
            root.getChildren().add(displayText);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
     
                String eventChar = keyEvent.getCharacter();
                Text eventText = new Text(0, 0, eventChar);

                
                if (textToDisplay.length() > 0 && textToDisplay.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length,
                    // as well as the backspace key, which is
                    // represented as a character of value = 8 on Windows.
                    displayText.setText(textToDisplay);
                    keyEvent.consume();
                }

            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {
                    fontSize += 5;
                    displayText.setFont(Font.font(fontName, fontSize));
                } else if (code == KeyCode.DOWN) {
                    fontSize = Math.max(0, fontSize - 5);
                    displayText.setFont(Font.font(fontName, fontSize));
                } else if (code == KeyCode.BACK_SPACE) {
                    removeLast();
                    displayText.setText(textToDisplay);
                }
            }
        }
        public void updatePositions() {
            double xcoord = 0;
            double ycoord = 0;
            Text textItem;
            for (int i = 0; i < textToDisplay.size(); i++) {
                textItem = textToDisplay.cyclePointer.next.item;
                textItem.setX(xcoord);
                textItem.setY(ycoord);
                textItem.setTextOrigin(VPos.TOP);
                textItem.setFont(Font.font(fontName, fontSize));
                xcoord = xcoord + textItem.getLayoutBounds().getWidth();
                if (xcoord > windowWidth) {
                    xcoord = 0;
                    ycoord = ycoord + textItem.getLayoutBounds().getHeight();
                }
                textToDisplay.cyclePointer = textToDisplay.cyclePointer.next;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        final int WINDOW_SIZE = 500;
        int windowWidth = WINDOW_SIZE;
        int windowHeight = WINDOW_SIZE;
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.WHITE);

        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root, windowWidth, windowHeight);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        primaryStage.setTitle("Editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
