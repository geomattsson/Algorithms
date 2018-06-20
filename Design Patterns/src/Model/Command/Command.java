package Model.Command;

import java.util.Stack;

public interface Command {
    Stack<Command> undo = new Stack();
    Stack<Command> redo = new Stack();

    void execute();
    void undo();
    void redo();
}
