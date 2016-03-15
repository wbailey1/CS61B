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

import javax.swing.plaf.synth.SynthEditorPaneUI;

/**
 * Created by William on 2016-03-07.
 */
public class UndoRedoQueue {
    public FastLinkedList2<FastLinkedList2.Node> undoQueue;
    public FastLinkedList2<FastLinkedList2.Node> redoQueue;
    public FastLinkedList2<String> undoActionQueue;
    public FastLinkedList2<String> redoActionQueue;
    public FastLinkedList2<Text>.Node currentChange;
    public UndoRedoQueue() {
        undoQueue = new FastLinkedList2<>();
        redoQueue = new FastLinkedList2<>();
        undoActionQueue = new FastLinkedList2<>();
        redoActionQueue = new FastLinkedList2<>();
    }
    public void undo() {
        System.out.println("undo");
        if (!undoQueue.isEmpty()) {
            System.out.println("undo2");
            currentChange = undoQueue.deleteItem().item;
            undoQueue.size --;
            if (undoActionQueue.deleteItem().item.equals("add")) {
                System.out.println("undo6");
                undoActionQueue.size --;
                currentChange.prev.next = currentChange;
                currentChange.next.prev = currentChange;
                if (!Editor.root.getChildren().contains(currentChange.item)) {
                    Editor.root.getChildren().add(currentChange.item);
                }
                redoQueue.addItem(currentChange);
                redoActionQueue.addItem("delete");
            }
            else {
                currentChange.prev.next = currentChange.next;
                currentChange.next.prev = currentChange.prev;
                Editor.root.getChildren().remove(currentChange.item);
                redoQueue.addItem(currentChange);
                redoActionQueue.addItem("add");
            }
            Editor.renderObject.render();
        }
    }
    /*public void addItem(String iD, FastLinkedList2.Node node) {
        FastLinkedList2.Node save;
        if (iD.equals("undo")) {
            System.out.println("undo3");
            save = undoQueue.currentNode.next;
            undoQueue.currentNode.next =
                    undoQueue.newNode(node, save, undoQueue.currentNode.prev.next);
            undoQueue.currentNode = undoQueue.currentNode.next;
            undoQueue.size ++;
            undoQueue.currentPos ++;
            undoQueue.cyclePointer = undoQueue.sentinel.next;
        }
        else if (iD.equals("undoactionadd")) {
            System.out.println("undo4");
            save = undoActionQueue.currentNode.next;
            undoActionQueue.currentNode.next =
                    undoActionQueue.newNode("add", save, undoActionQueue.currentNode.prev.next);
            undoActionQueue.currentNode = undoActionQueue.currentNode.next;
            undoActionQueue.size ++;
            undoActionQueue.currentPos ++;
            undoActionQueue.cyclePointer = undoActionQueue.sentinel.next;
        }
        else if (iD.equals("undoactiondelete")) {
            System.out.println("undo5");

            save = undoActionQueue.currentNode.next;
            undoActionQueue.currentNode.next =
                    undoActionQueue.newNode("delete", save, undoActionQueue.currentNode.prev.next);
            undoActionQueue.currentNode = undoActionQueue.currentNode.next;
            undoActionQueue.size ++;
            undoActionQueue.currentPos ++;
            undoActionQueue.cyclePointer = undoActionQueue.sentinel.next;
        }
    } */
    public void redo() {
        System.out.println("redo2");
        if (!redoQueue.isEmpty()) {
            System.out.println("redo");
            currentChange = redoQueue.deleteItem().item;
            redoQueue.size --;
            if (redoActionQueue.deleteItem().item.equals("add")) {
                redoActionQueue.size --;
                currentChange.prev.next = currentChange;
                currentChange.next.prev = currentChange;
                Editor.root.getChildren().add(currentChange.item);
                undoQueue.addItem(currentChange);
                undoActionQueue.addItem("delete");
            }
            else {
                currentChange.prev.next = currentChange.next;
                currentChange.next.prev = currentChange.prev;
                Editor.root.getChildren().remove(currentChange.item);
                undoQueue.addItem(currentChange);
                undoActionQueue.addItem("add");
            }
            Editor.renderObject.render();
        }
    }
}
