package graphEditor.controllers.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitProgramAction extends AbstractAction {

    public ExitProgramAction(){
        super("Exit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Bye bye...");
        System.exit(0);
    }
}
