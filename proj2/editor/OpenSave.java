package editor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.VPos;


/**
 * Created by William on 2016-03-06.
 */
public class OpenSave {
    public static FastLinkedList2<Text> open(String file) {
        String strRead;
        try {
            File textFile = new File(file);
            if (!textFile.exists()) {
                System.out.println("Unable to open because file with name " + file
                        + " does not exist");
                return null;
            }
            FastLinkedList2<Text> text = new FastLinkedList2<Text>();
            FileReader reader = new FileReader(textFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            int intRead = -1;
            while ((intRead = bufferedReader.read()) != -1) {
                strRead = String.valueOf((char) intRead);
                if (strRead.equals("\r")) {
                    strRead = "";
                    bufferedReader.read();
                }
                else if (strRead.equals("\n")) {
                    strRead = "";
                }
                Text textRead = new Text(0, 0, strRead);
                textRead.setTextOrigin(VPos.TOP);
                textRead.setFont(Font.font("Verdana", 12.0));
                text.addChar(textRead);
                Editor.root.getChildren().add(textRead);
            }
            bufferedReader.close();
            return text;
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when copying; exception was: " + ioException);
        }
        return null;
    }
    public static void save(String file, FastLinkedList2<Text> text) {
        try {
            File textFile = new File(file);
            if (!textFile.exists()) {
                System.out.println("Unable to save because file with name " + file
                        + " does not exist");
                return;
            }
            // Create a FileWriter to write to outputFilename. FileWriter will overwrite any data
            // already in outputFilename.
            FileWriter writer = new FileWriter(file);
            while (text.cyclePointer.item != null) {
                // The integer read can be cast to a char, because we're assuming ASCII.
                String charRead = text.cyclePointer.item.getText();
                writer.write(charRead);
                text.cyclePointer = text.cyclePointer.next;
            }
            // Close the writer.
            text.cyclePointer = text.sentinel.next;
            writer.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when copying; exception was: " + ioException);
        }
    }
}
