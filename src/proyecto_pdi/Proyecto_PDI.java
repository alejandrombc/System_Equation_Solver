package proyecto_pdi;

import java.io.IOException;
import org.opencv.core.Core;

public class Proyecto_PDI {
    public static void main(String args[]) throws IOException
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Agregamos OpenCV
        JControlador rolC = new JControlador ();
        
    }
}
