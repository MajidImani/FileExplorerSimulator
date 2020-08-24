package fileexplorer;

import montefiore.ulg.ac.be.graphics.NullHandlerException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         if (args.length > 1) {
            System.err.println("Max one argument is allowed");
            System.exit(1);
        }

        try {
            new GuiHandler(args);
        } catch (NullHandlerException e) {
            e.printStackTrace();
        }
    }
    
}
