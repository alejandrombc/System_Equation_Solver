package proyecto_pdi;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import net.sourceforge.tess4j.*;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class JControlador 
{
    private final OpenFile of;
    private final JInterfaz rolMenu;
    public BufferedImage imagen;
    public boolean recortado;
    
    public JControlador()
    {
        of = new OpenFile();

        rolMenu = new JInterfaz(this);
        recortado = false;
        rolMenu.setVisible(true);
        rolMenu.setTitle("Solucionador de ecuaciones lineales - PDI I-2016");
    }
    
    public void AbrirArchivo() throws IOException 
    {
        of.PickMe();
        rolMenu.setDisabled_B(0);
        rolMenu.setDisabled_B(1);
        rolMenu.setEditable_B();
        if(of.chosen_picture != null)
        {
            imagen = of.chosen_picture;
            rolMenu.setValues(imagen);
            rolMenu.setEneabled_B(0);
            rolMenu.setEneabled_B(2);
        }else
            System.out.println("Imagen no soportada");
    }
    
    int[][] transpuesta (int a [][], int n, int m)
    {   
        int i, j;
        int[][] b = new int[m][n];
        for (i = 0 ; i < m ; i++)
            for (j = 0 ; j < n ; j++)
                b[i][j] = a [j][i];
        return b;
    }    
        
    @SuppressWarnings("empty-statement")
            
    void aplicacion_convolucion(int tam, double[][] matriz_convolucion)
    {
        int height, width, x, y, filterX, filterY, imageX, imageY;
        double red, green, blue;
        
        height = imagen.getHeight();
        width = imagen.getWidth();

        if(height > tam && width > tam)
        {
            BufferedImage picture_2;

            picture_2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for(x = 0; x < width; x++)
                for(y = 0; y < height; y++)
                {
                    red = 0.0; green = 0.0; blue = 0.0;

                    for(filterX = 0; filterX < tam; filterX++)
                        for(filterY = 0; filterY < tam; filterY++)
                        {
                            imageX = ((x - tam / 2 + filterX + width) % width);
                            imageY = ((y - tam / 2 + filterY + height) % height);
                            Color c = new Color(imagen.getRGB(imageX, imageY),true);

                            red += c.getRed() * matriz_convolucion[filterX][filterY];
                            green += c.getGreen() * matriz_convolucion[filterX][filterY];;
                            blue += c.getBlue() * matriz_convolucion[filterX][filterY];;
                        }

                        Color myColor = new Color(min(max((int)red,0),255), min(max((int)green,0),255), min(max((int)blue,0),255));
                        int temp = myColor.getRGB();
                        picture_2.setRGB(x, y, temp);
                }

            imagen = picture_2;
            rolMenu.setValues(imagen);
        }
    }
         
    void Gaussian_blur(int tam)
    {
        double factor = 0.25; //Factor para multiplicar
        int limit, tope, i, j, k; 
        int [][] pascal_triangle = new int [tam][1]; //Arreglo de pascal
        int [][] aux = new int [tam][1]; //Auxiliar que tendra el anterior
       
        tope = 4;
        limit = tam-3;
        aux[0][0] = 1; aux[1][0] = 2; aux[2][0] = 1; 
        pascal_triangle[0][0] = 1; pascal_triangle[1][0] = 2; pascal_triangle[2][0] = 1; 
        
        for(i = 0; i < limit; i++)
        { 
            for(j = 0; j < tope; j++)
            {
                if(j == 0) pascal_triangle[j][0] = 1;
                else 
                    if(j == tope-1){ //Si es la ultima pongo 1 guardo mi nuevo aux
                        pascal_triangle[j][0] = 1;
                        for (k = 0; k < tope; k++ )
                            aux[k][0] = pascal_triangle[k][0];
                    }
                    else
                        pascal_triangle[j][0] = aux[j-1][0] + aux[j][0];
            }
            factor = factor * 0.5;
            tope++; //Lo hare de nuevo asi que tengo un nuevo tope
        }
        
        //Transpongo el arreglo para su multiplicacion
        
        int [][] pascal_triangle_trans = transpuesta(pascal_triangle, tam, 1);
        double[][] pascal_matrix = new double[tam][tam];
        factor = factor * factor;
        
        //Multiplico ambas matrices con el factor para tener el kernel
        
        for (i = 0; i < pascal_triangle.length; i++)
            for (j = 0; j < pascal_triangle_trans[0].length; j++)
            {
                for (k = 0; k < pascal_triangle[0].length; k++)
                    pascal_matrix[i][j] += pascal_triangle[i][k] * pascal_triangle_trans[k][j];
                pascal_matrix[i][j] = pascal_matrix[i][j] * factor;
            }
        
        aplicacion_convolucion(tam, pascal_matrix);  
    }
 
    public void ProcesarDatos(String resultado) 
    {
        recortado = false;
        int valor, resultado_final, i, j;
        String lines[] = resultado.split("\\r?\\n"), auxiliar, numero;
        HashMap<Character, Integer> variables = new HashMap<>();
        double[][] matriz_ecuaciones;
        boolean igualdad = true;
        ArrayList<Integer> arreglo_valores = new ArrayList<>(); 
        ArrayList<Character> variables_auxiliar = new ArrayList<>(); 
        double[] arreglo_resultado = new double[lines.length];
        
        for(i = 0; i < lines.length; i++)
        {
            auxiliar = lines[i].replace(" ","");
            numero = "";
            valor = 0;
            resultado_final = 0;
            igualdad = true;
            
            for(j = 0; j < auxiliar.length(); j++)
            {
                if(auxiliar.charAt(j) == '=') igualdad = false;
                else
                {
                    //Si es una letra agrego al mapa el numero que la antecede
                    if(Character.isLetter(auxiliar.charAt(j)))
                    {
                        //Si ya agregue eso al mapa solo sumo
                        if(variables.get(auxiliar.charAt(j)) != null )
                        {
                            switch (numero) 
                            {
                                case "":
                                    if(igualdad) valor = 1;
                                    else valor = -1;
                                    break;
                                case "+":
                                    if(igualdad) valor = 1;
                                    else valor = -1;
                                    break;
                                case "-":
                                    if(igualdad) valor = -1;
                                    else valor = 1;
                                    break;
                                default:
                                    if(igualdad) valor = Integer.parseInt(numero);
                                    else valor = -1*Integer.parseInt(numero);
                                    break;
                            }
                            
                            variables.put(auxiliar.charAt(j), variables.get(auxiliar.charAt(j))+ valor);
                            numero = "";
                            valor = 0;
                            
                        }
                        else
                        {
                            //Sino lo agrego por primera vez
                            switch (numero) 
                            {
                                 case "":
                                    if(igualdad) valor = 1;
                                    else valor = -1;
                                    break;
                                case "+":
                                    if(igualdad) valor = 1;
                                    else valor = -1;
                                    break;
                                case "-":
                                    if(igualdad) valor = -1;
                                    else valor = 1;
                                    break;
                                default:
                                    if(igualdad) valor = Integer.parseInt(numero);
                                    else valor = -1*Integer.parseInt(numero);
                                    break;
                            }
                            
                            //Agrego a la lista de variables (esta las usare para siempre quedar igual)
                            variables.put(auxiliar.charAt(j), valor);
                            numero = "";
                            valor = 0;
                            
                        }   
                    }
                    else
                    {
                        //Si no es char guardo todo en un string, ejemplo "+24" o "-24"
                        numero += auxiliar.charAt(j);
                        if((j+1 == auxiliar.length() || auxiliar.charAt(j+1) == '-' || auxiliar.charAt(j+1) == '+' || auxiliar.charAt(j+1) == '='))
                        {
                            if(igualdad) resultado_final += -1*Integer.parseInt(numero);
                            else resultado_final += Integer.parseInt(numero);
                            numero = ""; 
                        }
                    }
                }
            }
              
            Set set = variables.entrySet();
            Iterator w = set.iterator();
     
            while(w.hasNext()) 
            {
                Map.Entry me = (Map.Entry)w.next();
                arreglo_valores.add((Integer) me.getValue());
                variables.put((Character)me.getKey(), 0);
                if(i == 1) variables_auxiliar.add((Character) me.getKey());
            }
            arreglo_resultado[i] = resultado_final;
        }
        
        if(variables.size() == lines.length)
        {
            int k = 0;
            matriz_ecuaciones = new double[variables.size()][variables.size()];
            char[] variable_solucion = new char[variables.size()];
            
            for(i = 0; i < variables.size(); i++)
                for(j = 0; j < variables.size(); j++)
                    matriz_ecuaciones[i][j] = arreglo_valores.get(k++);
                
            for(i = 0; i < arreglo_resultado.length; i++)
                    variable_solucion[i] = variables_auxiliar.get(i);
            
            Cramer cramer = new Cramer();
            double[] solucion = cramer.cramers(matriz_ecuaciones,arreglo_resultado);
            rolMenu.Mostrar_Solucion(solucion, variable_solucion);
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Sistema invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void Otsu_umbral() 
    {
        Mat image;
        
        if(!recortado) image = of.image;
        else
        {
            image = Highgui.imread("delete.jpg", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
            File f = new File("delete.jpg");
            f.delete();
        }    
        
        Imgproc.threshold(image, image, -1, 255,Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);   
        //Creo un Buffered con la info del Mat
        BufferedImage gray = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_BYTE_GRAY);
        byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
        image.get(0, 0, data);
        imagen = gray;
    }
    
    public void Reconocimiento()
    {
        Gaussian_blur(3);
        Otsu_umbral();
        
        BufferedImage imageFile = imagen; //Imagen a cargar
        ITesseract instance = new Tesseract(); // Creamos la instancia del motor Tesseract de Tess4J
        
        //Limitamos los caracteres para mejor efciencia 
        instance.setTessVariable("tessedit_char_whitelist", "xyzw0123456789+-*="); 
        
        try 
        {
            String result = instance.doOCR(imageFile); //Aplico OCR a imagen y resulta String
            rolMenu.Colocar_Confirmacion(result);
            rolMenu.setDisabled_B(0);
            rolMenu.setEneabled_B(1);
            rolMenu.setDisabled_B(2);
        } 
        catch (TesseractException e) 
        {
            System.err.println(e.getMessage()); //Imprimo error si hay
        }
    }
}
