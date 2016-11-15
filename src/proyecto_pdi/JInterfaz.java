package proyecto_pdi;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class JInterfaz extends javax.swing.JFrame 
{
    private final JControlador rolMenu;
    public BufferedImage imagen_de_controlador;
    
    public JInterfaz(JControlador C) 
    {
        initComponents();
        this.rolMenu = C;
        resultado.setEditable(false);
        confirmacion.setEditable(false);
        recortar.setEnabled(false);
        this.editar.setEnabled(false);
        this.reconocer.setEnabled(false);
        this.procesar.setEnabled(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        resultado = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        confirmacion = new javax.swing.JTextArea();
        pane_imagen = new javax.swing.JScrollPane();
        imagen = new javax.swing.JLabel();
        abrir = new javax.swing.JButton();
        confirmacion_label = new javax.swing.JLabel();
        resultado_label = new javax.swing.JLabel();
        procesar = new javax.swing.JButton();
        reconocer = new javax.swing.JButton();
        recortar = new javax.swing.JButton();
        editar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultado.setColumns(20);
        resultado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        resultado.setRows(5);
        jScrollPane2.setViewportView(resultado);

        confirmacion.setColumns(20);
        confirmacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        confirmacion.setRows(5);
        jScrollPane3.setViewportView(confirmacion);

        pane_imagen.setViewportView(imagen);

        abrir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/open-file-button.png"))); // NOI18N
        abrir.setText("Abrir");
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirActionPerformed(evt);
            }
        });

        confirmacion_label.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        confirmacion_label.setText("Confirmacion:");

        resultado_label.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        resultado_label.setText("Resultado:");

        procesar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        procesar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/back-arrow.png"))); // NOI18N
        procesar.setText("Procesar");
        procesar.setPreferredSize(new java.awt.Dimension(103, 25));
        procesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procesarActionPerformed(evt);
            }
        });

        reconocer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        reconocer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Derecha.png"))); // NOI18N
        reconocer.setText("Reconocer");
        reconocer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconocerActionPerformed(evt);
            }
        });

        recortar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        recortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1479103637_scissors.png"))); // NOI18N
        recortar.setText("Recortar");
        recortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recortarActionPerformed(evt);
            }
        });

        editar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1479110156_editor-pencil-pen-edit-write-glyph.png"))); // NOI18N
        editar.setText("Editar");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmacion_label)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(procesar, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                    .addComponent(editar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(resultado_label)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2)))
                    .addComponent(pane_imagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(abrir, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(recortar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(reconocer, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abrir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reconocer, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recortar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(pane_imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultado_label)
                    .addComponent(confirmacion_label, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(editar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(procesar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        try {
            rolMenu.AbrirArchivo();
        } catch (IOException ex) {
            Logger.getLogger(JInterfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_abrirActionPerformed

    private void procesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procesarActionPerformed
        String result = confirmacion.getText();
        result = result.replace(" ","");
        this.editar.setEnabled(false);
        this.confirmacion.setEditable(false);
        rolMenu.ProcesarDatos(result);
    }//GEN-LAST:event_procesarActionPerformed

    private void reconocerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconocerActionPerformed
        rolMenu.Reconocimiento();
        this.confirmacion.setEditable(false);
    }//GEN-LAST:event_reconocerActionPerformed

    private void recortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recortarActionPerformed
        Recortado recortar = new Recortado(imagen_de_controlador, this, rolMenu);
        try {
            recortar.Inicio(recortar);
        } catch (IOException ex) {
            Logger.getLogger(JInterfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.recortar.setSelected(false);
    }//GEN-LAST:event_recortarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
       this.confirmacion.setEditable(true);
    }//GEN-LAST:event_editarActionPerformed

    public void setValues(BufferedImage chosen_picture) {
        imagen_de_controlador = chosen_picture;
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(chosen_picture).getImage().getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_DEFAULT));
        imagen.setIcon(imageIcon);
    }
    
    public void Colocar_Confirmacion(String result){
        result = result.replace(" ","");
        result = result.replace("+"," + ");
        result = result.replace("-"," - ");
        result = result.replace("="," = ");
        this.confirmacion.setText(result);
        this.confirmacion.setCaretPosition(0);
        this.editar.setEnabled(true);
    }
    
    public void Mostrar_Solucion(double[] solucion, char[] variables){
        String salida = "";
        for(int i = 0; i < solucion.length; i++){
            salida += variables[i]+" = "+(Double.toString(solucion[i]))+'\n';
        }
        resultado.setText(salida);
    }
    
    public void setEneabled_B(int i) {
        switch (i) {
            case 0:
                this.reconocer.setEnabled(true);
                break;
            case 1:
                this.procesar.setEnabled(true);
                break;
            default:
                this.recortar.setEnabled(true);
                break;
        }
    }
    
    public void setEditable_B() {
        this.confirmacion.setText(null);
        this.confirmacion.setEditable(false);
        this.resultado.setText(null);
    }
     
    public void setDisabled_B(int i) {
        switch (i) {
            case 0:
                this.reconocer.setEnabled(false);
                break;
            case 1:
                this.procesar.setEnabled(false);
                break;
            default:
                this.recortar.setEnabled(false);
                break;
        }
    }
     
    public static void main(String args[]) {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir;
    private javax.swing.JTextArea confirmacion;
    private javax.swing.JLabel confirmacion_label;
    private javax.swing.JButton editar;
    private javax.swing.JLabel imagen;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane pane_imagen;
    private javax.swing.JButton procesar;
    private javax.swing.JButton reconocer;
    private javax.swing.JButton recortar;
    private javax.swing.JTextArea resultado;
    private javax.swing.JLabel resultado_label;
    // End of variables declaration//GEN-END:variables
}
