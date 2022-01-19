package gui;

import javax.swing.*;

/**
 * Class handles the "delete" confirmation and playlist windows
 */
public class SimpleDialog {
    JFrame f;

    public SimpleDialog(){f = new JFrame();}

    /**
     * Opens a window to ask user to confirm deletion
     * @return true if OK is pressed, false if Cancel is pressed
     */
    public static boolean delete(){
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?",
                "Delete...?",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if(confirm == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Opens window to ask user to input category name
     * @return user input
     */
    public static String Category(){
        return JOptionPane.showInputDialog(null,"Category name: ", "New/Edit Category", JOptionPane.PLAIN_MESSAGE);
    }
}
