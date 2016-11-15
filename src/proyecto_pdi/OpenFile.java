package proyecto_pdi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

class OpenFile {
    JFileChooser fileChooser = new JFileChooser(new File("C:\\ImagenesPDI"));
    BufferedImage chosen_picture;
    Mat image;
    String Extension;
    float tamano;
    String nombre;
    
    public OpenFile(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, BMP, & PNG Images", "jpg", "bmp", "png");
        fileChooser.setFileFilter(filter);      
    };
    
    public void PickMe() throws FileNotFoundException, IOException{
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String Ruta = file.getAbsolutePath();
            String[] arrayRuta = Ruta.split("\\.");
            int Tam = arrayRuta.length - 1;
            Extension = arrayRuta[Tam];
            tamano = (float) ((float)file.length()/1048576)*8;
            nombre = file.getName();
            if(Extension.equals("bmp") || Extension.equals("BMP")){
               
               image = Highgui.imread(file.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);
               chosen_picture =  CargarBMP(Ruta);
               
            }else if (Extension.equals("jpg") || Extension.equals("JPG") || Extension.equals("png") || Extension.equals("PNG")){
            try
            {
                //image = new Mat();
                //System.out.print(Ruta);
                image = Highgui.imread(Ruta, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
                chosen_picture = ImageIO.read(file);
            }
            catch (final IOException not_action)
            {
            }
            }else{
                chosen_picture = null;
            }
        }
    }
    
    public void saveImage() {
        int saveValue = fileChooser.showSaveDialog(null);
        if (saveValue == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(chosen_picture, Extension, new File(fileChooser.getSelectedFile().getAbsolutePath()));
            } catch (IOException e) {
            }
        }
    }
    
    public static BufferedImage CargarBMP (String pathImageFile) throws FileNotFoundException {
        InputStream imagen_bmp = new FileInputStream(pathImageFile);
        try { 
            int ancho, alto, bits, tamano_imagen, paleta;
            Image image = null;
            BufferedImage imagen = null;
            byte bmpHeader[] = new byte[14];    
            imagen_bmp.read(bmpHeader,0,14);
            byte bmpInfo[] = new byte[40];     
            imagen_bmp.read(bmpInfo,0,40);
            ancho = (((int)bmpInfo[7]&0xff)<<24) | (((int)bmpInfo[6]&0xff)<<16) | (((int)bmpInfo[5]&0xff)<<8) | (int)bmpInfo[4]&0xff; 
            alto = (((int)bmpInfo[11]&0xff)<<24) | (((int)bmpInfo[10]&0xff)<<16) | (((int)bmpInfo[9]&0xff)<<8) | (int)bmpInfo[8]&0xff; 
            bits = (((int)bmpInfo[15]&0xff)<<8) | (int)bmpInfo[14]&0xff;    
            tamano_imagen = (((int)bmpInfo[23]&0xff)<<24) | (((int)bmpInfo[22]&0xff)<<16) | (((int)bmpInfo[21]&0xff)<<8) | (int)bmpInfo[20]&0xff;   
            paleta = (((int)bmpInfo[35]&0xff)<<24) | (((int)bmpInfo[34]&0xff)<<16) | (((int)bmpInfo[33]&0xff)<<8) | (int)bmpInfo[32]&0xff; 
          
            
            switch (bits) {
                case 24:
                    {
                        
                        
                        tamano_imagen = alto*ancho*3;
                       
                        int relleno = sacar_padding24(ancho);    
                        int data_int[] = new int[alto*ancho];  
                        
                        byte data_bytes[] = new byte[(ancho+relleno)*3*alto];  
                        
                        imagen_bmp.read(data_bytes,0,(ancho+relleno)*3*alto);
                        int k = 0;
                        for (int j=0;j<alto;j++) {
                            for (int i=0;i<ancho;i++) {
                                                 
                                data_int[ancho*(alto-j-1)+i] = (255&0xff)<<24 | (((int)data_bytes[k+2]&0xff)<<16) | (((int)data_bytes[k+1]&0xff)<<8) | (int)data_bytes[k]&0xff;
                                k += 3;
                            }
                            k += relleno;
                        } 
                           imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                           imagen.setRGB(0, 0, ancho, alto, data_int, 0, ancho);
                       
                        break;
                    }
                case 16:
                    {
                        
                        int relleno = (tamano_imagen/alto)-ancho*2;    
                        int data_int[] = new int[alto*ancho];  
                        byte data_bytes[] = new byte[(ancho+relleno)*2*alto]; 
                        imagen_bmp.read(data_bytes,0,(ancho+relleno)*2*alto);
                        int k = 0;
                        for (int j=0;j<alto;j++) {
                            for (int i=0;i<ancho;i++) {
                                
                                data_int[ancho*(alto-j-1)+i] = (255&0xff)<<24 | (((((int)data_bytes[k+1]>>>2)&0x3f)|0x60)<<3<<16) | ((((int)(((data_bytes[k+1]&0x3)<<3) | ((data_bytes[k]&0xe0)>>>5)))|0x60)<<3<<8) | ((((int)data_bytes[k]&0x1f)|0x60)<<3);
                                k += 2;
                            }
                            k += relleno;
                        } 
                        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                        imagen.setRGB(0, 0, ancho, alto, data_int, 0, ancho);
                        break;                    
                    }
                case 8:
                    {
                        int colors = 0;
                        if (paleta > 0) colors = paleta;
                        else colors = 256;
                        if (tamano_imagen == 0) {
                            tamano_imagen = ((((ancho*bits)+31)&~31)>>3);
                            tamano_imagen *= alto;
                        }
                        
                        int imagePalette[] = new int[colors];
                        byte imagePaletteBytes[] = new byte[colors*4];
                        imagen_bmp.read(imagePaletteBytes,0,colors*4);
                        int k = 0;
                        for (int i=0;i<colors;i++) {
                            imagePalette[i] = (255&0xff)<<24 | (((int)imagePaletteBytes[k+2]&0xff)<<16) | (((int)imagePaletteBytes[k+1]&0xff)<<8) | (int)imagePaletteBytes[k]&0xff;
                            k += 4;
                        }
                         
                        int relleno = (tamano_imagen/alto)-ancho;    
                        int data_int[] = new int[ancho*alto]; 
                        byte data_intBytes[] = new byte[(ancho+relleno)*alto];  
                        imagen_bmp.read(data_intBytes,0,(ancho+relleno)*alto);
                        k = 0;
                        for (int j=0;j<alto;j++) {
                            for (int i=0;i<ancho;i++) {
                                
                                data_int[ancho*(alto-j-1)+i] = imagePalette[((int)data_intBytes[k]&0xff)];
                                k++;
                            }
                            k += relleno;
                        } 
                        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                        imagen.setRGB(0, 0, ancho, alto, data_int, 0, ancho);
                        
                        break;   
                    }
                case 4:
                    {
                        int colors = 0;
                        if (paleta > 0) colors = paleta;   
                        else colors = 16;
                        
                        int imagePalette[] = new int[colors];
                        byte imagePaletteBytes[] = new byte[colors*4];
                        imagen_bmp.read(imagePaletteBytes,0,colors*4);
                        int k = 0;
                        for (int i=0;i<colors;i++) {
                            imagePalette[i] = (255&0xff)<<24 | (((int)imagePaletteBytes[k+2]&0xff)<<16) | (((int)imagePaletteBytes[k+1]&0xff)<<8) | (int)imagePaletteBytes[k]&0xff;
                            k += 4;
                        }      
                       
                        tamano_imagen = (((ancho*bits)+31)&~31)>>3;
                        int data_int[] = new int[ancho*alto];  
                        byte data_intBytes[] = new byte[tamano_imagen];  
                        k = 0;
                        for (int j=0;j<alto;j++) {
                            imagen_bmp.read(data_intBytes,0,tamano_imagen);
                            k = 0;
                            for (int i=0;i<ancho;i++) {
                                
                                if (ancho*(alto-j-1)+i > ancho*alto-1) break;
                                if (k > tamano_imagen*alto-1) break;
                                for (int l=0;l<2;l++) {
                                    if (l == 0) {
                                        data_int[ancho*(alto-j-1)+i] = imagePalette[((int)(data_intBytes[k]>>4)&0xf)];
                                        i++;
                                        if (i >= ancho) break;
                                    } else data_int[ancho*(alto-j-1)+i] = imagePalette[((int)data_intBytes[k]&0xf)];
                                }
                                k++;
                            }
                        } 
                        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                        imagen.setRGB(0, 0, ancho, alto, data_int, 0, ancho);
                        break;
                    }
                case 1:
                    {
                        int colors = 0;
                        if (paleta > 0) colors = paleta;
                        else colors = 2;
                        
                        int imagePalette[] = new int[colors];
                        byte imagePaletteBytes[] = new byte[colors*4];
                        imagen_bmp.read(imagePaletteBytes,0,colors*4);
                        int k = 0;
                        for (int i=0;i<colors;i++) {
                            imagePalette[i] = (255&0xff)<<24 | (((int)imagePaletteBytes[k+2]&0xff)<<16) | (((int)imagePaletteBytes[k+1]&0xff)<<8) | (int)imagePaletteBytes[k]&0xff;
                            k += 4;
                        }   
                        
                        tamano_imagen = (((ancho*bits)+31)&~31)>>3;
                        int data_int[] = new int[ancho*alto];  
                        byte data_intBytes[] = new byte[tamano_imagen]; 
                        k = 0;
                        for (int j=0;j<alto;j++) {
                            imagen_bmp.read(data_intBytes,0,tamano_imagen);
                            k = 0;
                            for (int i=0;i<ancho; i++) {
                                //Se va cargando píxel por píxel el contenido de la imagen
                                if (ancho*(alto-j-1)+i > ancho*alto-1) break;
                                if (k > tamano_imagen*alto-1) break;
                                for (int l=0;l<8;l++) {
                                    data_int[ancho*(alto-j-1)+i] = imagePalette[((int)(data_intBytes[k]>>(8-l-1))&0x1)];
                                    if (l != 7) {
                                        i++;
                                        if (i >= ancho) break;
                                    }
                                }
                                k++;
                            }
                        } 
                        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                        imagen.setRGB(0, 0, ancho, alto, data_int, 0, ancho);
                        break;
                    }
            }
            imagen_bmp.close();
            return imagen;
        } catch (Exception e) {
            
        }
        return null;   
    }

    public static int sacar_padding24(int ancho){
	int padding;
        double aux_padding;
        
	aux_padding = ((double)(ancho*3)/4);
        
	aux_padding = (double) (aux_padding - Math.floor(aux_padding));
	if(aux_padding == 0.5){
		padding = 2;
        }else if (aux_padding == 0.25){
		padding = 3;
        }else if (aux_padding == 0.75){
		padding = 1;
        }else{
		padding = 0;
        }

	return padding;
    }
    
}
