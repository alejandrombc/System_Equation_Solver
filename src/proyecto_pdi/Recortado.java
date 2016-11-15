package proyecto_pdi;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;
  
public class Recortado extends JPanel
{
    BufferedImage imagen_a_recortar;
    Dimension size;
    Rectangle clip;
    JSpinner Ancho;
    JSpinner Alto;
    int pos_x;
    int pos_y;
    JFrame f;
    JInterfaz interfaz;
    JControlador controlador;
    
  
    public Recortado(BufferedImage imagen_a_recortar, JInterfaz inter, JControlador C)
    {
        this.controlador = C;
        this.interfaz = inter;
        this.imagen_a_recortar = imagen_a_recortar;
        size = new Dimension(imagen_a_recortar.getWidth(), imagen_a_recortar.getHeight());
    }
  
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g2.drawImage(imagen_a_recortar, x, y, this);
        
        if(clip == null)
            createClip();
        g2.setPaint(Color.BLUE);
        g2.setStroke(new BasicStroke(2));
        g2.draw(clip);
    }
  
    public void setClip(int x, int y)
    {
        int x0 = (getWidth() - size.width)/2;
        int y0 = (getHeight() - size.height)/2;
        if(x < x0 || x + clip.width  > x0 + size.width || y < y0 || y + clip.height > y0 + size.height)
            return;
        clip.setLocation(x, y);
        repaint();
    }
  
    @Override
    public Dimension getPreferredSize()
    {
        return size;
    }
  
    private void createClip()
    {
        int ancho = (int) Ancho.getValue();
        int alto = (int) Alto.getValue();
        if(ancho > imagen_a_recortar.getWidth()) ancho = imagen_a_recortar.getWidth()-1;
        if(alto > imagen_a_recortar.getHeight()) alto = imagen_a_recortar.getHeight()-1;
        
        Ancho.setValue(ancho);
        Alto.setValue(alto);
        
        clip = new Rectangle(ancho, alto);
        clip.x = (getWidth() - clip.width)/2;
        clip.y = (getHeight() - clip.height)/2;
    }
  
    public void saveImage(BufferedImage chosen_picture) {
        try 
        {
            File outputfile = new File("delete.jpg");
            ImageIO.write(chosen_picture, "jpg", outputfile);
        } 
        catch (IOException e) 
        {
        }
    }
    
    public void clipImage()
    {
        BufferedImage clipped = null;
        try
        {
            int w = clip.width;
            int h = clip.height;
            int x0 = (getWidth()  - size.width)/2;
            int y0 = (getHeight() - size.height)/2;
            int x = clip.x - x0;
            int y = clip.y - y0;
            clipped = imagen_a_recortar.getSubimage(x, y, w, h);
        }
        catch(RasterFormatException rfe)
        {
            System.out.println("raster format error: " + rfe.getMessage());
        }
        this.interfaz.setValues(clipped);
        this.controlador.imagen = clipped;
        this.controlador.recortado = true;
        saveImage(clipped);
        f.dispose();
    }
    
    private JPanel getUIPanel() throws IOException
    {
        JButton clip = new JButton("Recortar Imagen");
        clip.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clipImage();
            }
        });
        Dimension boton = new Dimension(140, 41);
        clip.setPreferredSize(boton);
        Image img = ImageIO.read(new File("src/imagenes/1479108010_content-cut.png"));
        clip.setIcon(new ImageIcon(img));
        
        Ancho = new JSpinner();
        Alto = new JSpinner();
        
        JLabel ancho_label = new JLabel("Ancho");
        JLabel alto_label = new JLabel("Alto");
        JLabel separacion = new JLabel("       ");
        JLabel separacion_2 = new JLabel("     ");
        
        Alto.setValue(imagen_a_recortar.getHeight()/4);
        Ancho.setValue(imagen_a_recortar.getWidth()/2);
        
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        ancho_label.setFont(boldFont);
        alto_label.setFont(boldFont);
        
        Dimension spiner = new Dimension(55, 30);
        Alto.setPreferredSize(spiner);
        Alto.setFont(boldFont);
        Ancho.setPreferredSize(spiner);
        Ancho.setFont(boldFont);
                
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                createClip();
                setClip(pos_x, pos_y);
                repaint();
            }
        };
        
        Ancho.addChangeListener(listener);
        Alto.addChangeListener(listener);
        
        JPanel panel = new JPanel();
        panel.add(clip);
        panel.add(separacion);
        panel.add(ancho_label);
        panel.add(Ancho);
        panel.add(separacion_2);
        panel.add(alto_label);
        panel.add(Alto);
        return panel;
    }   
    
    public void Inicio(Recortado test) throws IOException
    {
        ClipMover mover = new ClipMover(test);
        test.addMouseListener(mover);
        test.addMouseMotionListener(mover);
        f = new JFrame();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().add(new JScrollPane(test));
        f.getContentPane().add(test.getUIPanel(), "South");
        f.setTitle("Recortado - PDI I-2016");
        f.setVisible(true);
    }
}
  
class ClipMover extends MouseInputAdapter
{
    Recortado cropping;
    Point offset;
    boolean dragging;
  
    public ClipMover(Recortado c)
    {
        cropping = c;
        offset = new Point();
        dragging = false;
    }
  
    @Override
    public void mousePressed(MouseEvent e)
    {
        Point p = e.getPoint();
        if(cropping.clip.contains(p))
        {
            offset.x = p.x - cropping.clip.x;
            offset.y = p.y - cropping.clip.y;
            dragging = true;
        }
    }
  
    @Override
    public void mouseReleased(MouseEvent e)
    {
        dragging = false;
    }
  
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if(dragging)
        {
            int x = e.getX() - offset.x;
            int y = e.getY() - offset.y;
            cropping.pos_x = x;
            cropping.pos_y = y;
            cropping.setClip(x, y);
        }
    }
}