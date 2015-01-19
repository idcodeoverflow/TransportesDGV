/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package almacendgv;

import beans.MarcaMotorDTO;
import data.MarcaMotorDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class ControlMarcasMotores extends javax.swing.JFrame {

    /**
     * Creates new form ControlOperadores
     */
    public ControlMarcasMotores() {
        try{
            initComponents();
            this.setLocationRelativeTo(null);
            this.estadoBotonesInicio();
            this.obtenerMarcasMotor();
            this.jTCatalogoMarcasMotor.setSelectionMode(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2014\n" + ex.getMessage(),
                    "Error al iniciar ventana Control Operadores!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("Constructor ControlOperadores()", 2014, UserHome.getUsuario(), ex);
            this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLIdMarcaMotor = new javax.swing.JLabel();
        jTFIdMarcaMotor = new javax.swing.JTextField();
        jLNombre = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jBDarBaja = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBAgregar = new javax.swing.JButton();
        jPCatalogoMarcasMotor = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTCatalogoMarcasMotor = new javax.swing.JTable();
        jBMMenu = new javax.swing.JMenuBar();
        jMArchivo = new javax.swing.JMenu();
        jMIAgregar = new javax.swing.JMenuItem();
        jMIModificar = new javax.swing.JMenuItem();
        jMIDarBaja = new javax.swing.JMenuItem();
        jMISalir = new javax.swing.JMenuItem();
        jMEditar = new javax.swing.JMenu();
        jMILimpiar = new javax.swing.JMenuItem();
        jMAyuda = new javax.swing.JMenu();
        jMIVerManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Marcas de Motor Sistema de Administración Mantenimiento");

        jLIdMarcaMotor.setText("Id Marca Motor");

        jTFIdMarcaMotor.setEditable(false);
        jTFIdMarcaMotor.setFocusable(false);

        jLNombre.setText("Nombre:");

        jBDarBaja.setText("Dar de Baja");
        jBDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDarBajaActionPerformed(evt);
            }
        });

        jBModificar.setText("Modificar");
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });

        jBAgregar.setText("Agregar");
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });

        jPCatalogoMarcasMotor.setBorder(javax.swing.BorderFactory.createTitledBorder("Catálogo de Marcas de Motor"));

        jTCatalogoMarcasMotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Marca Motor", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTCatalogoMarcasMotor.getTableHeader().setReorderingAllowed(false);
        jTCatalogoMarcasMotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTCatalogoMarcasMotorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTCatalogoMarcasMotor);
        if (jTCatalogoMarcasMotor.getColumnModel().getColumnCount() > 0) {
            jTCatalogoMarcasMotor.getColumnModel().getColumn(0).setMinWidth(150);
            jTCatalogoMarcasMotor.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTCatalogoMarcasMotor.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPCatalogoMarcasMotorLayout = new javax.swing.GroupLayout(jPCatalogoMarcasMotor);
        jPCatalogoMarcasMotor.setLayout(jPCatalogoMarcasMotorLayout);
        jPCatalogoMarcasMotorLayout.setHorizontalGroup(
            jPCatalogoMarcasMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCatalogoMarcasMotorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPCatalogoMarcasMotorLayout.setVerticalGroup(
            jPCatalogoMarcasMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCatalogoMarcasMotorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMArchivo.setText("Archivo");

        jMIAgregar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMIAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Apply-16.png"))); // NOI18N
        jMIAgregar.setText("Agregar");
        jMIAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAgregarActionPerformed(evt);
            }
        });
        jMArchivo.add(jMIAgregar);

        jMIModificar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMIModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Modify-16.png"))); // NOI18N
        jMIModificar.setText("Modificar");
        jMIModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIModificarActionPerformed(evt);
            }
        });
        jMArchivo.add(jMIModificar);

        jMIDarBaja.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMIDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Trash-16.png"))); // NOI18N
        jMIDarBaja.setText("Dar de Baja");
        jMIDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIDarBajaActionPerformed(evt);
            }
        });
        jMArchivo.add(jMIDarBaja);

        jMISalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMISalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit-16.png"))); // NOI18N
        jMISalir.setText("Salir");
        jMISalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMISalirActionPerformed(evt);
            }
        });
        jMArchivo.add(jMISalir);

        jBMMenu.add(jMArchivo);

        jMEditar.setText("Editar");

        jMILimpiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMILimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Clear-16.png"))); // NOI18N
        jMILimpiar.setText("Limpiar");
        jMILimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMILimpiarActionPerformed(evt);
            }
        });
        jMEditar.add(jMILimpiar);

        jBMMenu.add(jMEditar);

        jMAyuda.setText("Ayuda");

        jMIVerManual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMIVerManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Help-16.png"))); // NOI18N
        jMIVerManual.setText("Ver Manual");
        jMIVerManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIVerManualActionPerformed(evt);
            }
        });
        jMAyuda.add(jMIVerManual);

        jBMMenu.add(jMAyuda);

        setJMenuBar(jBMMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCatalogoMarcasMotor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBAgregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBModificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBDarBaja))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLIdMarcaMotor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFIdMarcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBAgregar, jBDarBaja, jBModificar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLIdMarcaMotor)
                    .addComponent(jTFIdMarcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNombre)
                    .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBDarBaja)
                    .addComponent(jBModificar)
                    .addComponent(jBAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPCatalogoMarcasMotor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPCatalogoMarcasMotor.getAccessibleContext().setAccessibleName("Catálogo de Marcas de Motor");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTCatalogoMarcasMotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTCatalogoMarcasMotorMouseClicked
        this.obtenerMarcaMotor();
    }//GEN-LAST:event_jTCatalogoMarcasMotorMouseClicked

    private void jMIAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAgregarActionPerformed
        this.agregar();
    }//GEN-LAST:event_jMIAgregarActionPerformed

    private void jMIModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIModificarActionPerformed
        this.modificar();
    }//GEN-LAST:event_jMIModificarActionPerformed

    private void jMIDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIDarBajaActionPerformed
        this.eliminar();
    }//GEN-LAST:event_jMIDarBajaActionPerformed

    private void jMISalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMISalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMISalirActionPerformed

    private void jMILimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMILimpiarActionPerformed
        this.limpiar();
    }//GEN-LAST:event_jMILimpiarActionPerformed

    private void jMIVerManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIVerManualActionPerformed
        
    }//GEN-LAST:event_jMIVerManualActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        this.agregar();
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        this.modificar();
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jBDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDarBajaActionPerformed
        this.eliminar();
    }//GEN-LAST:event_jBDarBajaActionPerformed

    private void agregar(){
        MarcaMotorDTO marcaMotor = new MarcaMotorDTO();
        try {
            if("".equals(this.jTFNombre.getText()) || this.jTFNombre == null){
                JOptionPane.showMessageDialog(this, "Aún no se agrega un nombre\nde motor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            MarcaMotorDAO acceso = new MarcaMotorDAO();
            marcaMotor.setNombre(this.jTFNombre.getText());
            acceso.agregarMarcaMotor(marcaMotor);
            this.limpiar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2015\n" + ex.getMessage(),
                        "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2015, UserHome.getUsuario(), ex);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2016\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2016, UserHome.getUsuario(), ex);
        }
    }
    
    private void modificar(){
        MarcaMotorDTO marcaMotor = new MarcaMotorDTO();
        try {
            if("".equals(this.jTFNombre.getText()) || this.jTFNombre == null){
                JOptionPane.showMessageDialog(this, "Aún no se agrega un nombre\nde motor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            MarcaMotorDAO acceso = new MarcaMotorDAO();
            marcaMotor.setIdMarcaMotor(Integer.parseInt(this.jTFIdMarcaMotor.getText()));
            marcaMotor.setNombre(this.jTFNombre.getText());
            acceso.modificarMarcaMotor(marcaMotor);
            this.limpiar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2017\n" + ex.getMessage(),
                        "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2017, UserHome.getUsuario(), ex);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2018\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2018, UserHome.getUsuario(), ex);
        }
    }
    
    private void eliminar(){
        MarcaMotorDTO marcaMotor = new MarcaMotorDTO();
        try {
            MarcaMotorDAO acceso = new MarcaMotorDAO();
            marcaMotor.setIdMarcaMotor(Integer.parseInt(this.jTFIdMarcaMotor.getText()));
            acceso.eliminarMarcaMotor(marcaMotor);
            this.limpiar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2019\n" + ex.getMessage(),
                        "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2019, UserHome.getUsuario(), ex);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2020\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(marcaMotor.toString(), 2020, UserHome.getUsuario(), ex);
        }
    }
    
    private void limpiar(){
        try{
            this.jTFIdMarcaMotor.setText(null);
            this.jTFNombre.setText(null);
            DefaultTableModel modelo = (DefaultTableModel) this.jTCatalogoMarcasMotor.getModel();
            while(modelo.getRowCount() > 0){
                modelo.removeRow(modelo.getRowCount() - 1);
            }
            this.estadoBotonesInicio();
            this.obtenerMarcasMotor();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2021\n" + ex.getMessage(),
                    "Error al limpiar los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ControlMarcasMotores limpiar()", 2021, UserHome.getUsuario(), ex);
        }
    }
    
    private void obtenerMarcaMotor(){
        MarcaMotorDTO marcaMotor = new MarcaMotorDTO();
        try {
            int index = this.jTCatalogoMarcasMotor.getSelectedRow();
            String numero = null;

            if(index > -1){
                numero = ((this.jTCatalogoMarcasMotor.getValueAt(index, 0) != null) ? this.jTCatalogoMarcasMotor.getValueAt(index, 0).toString() : "");
                if(numero != null) {
                    marcaMotor = new MarcaMotorDAO().obtenerMarcaMotor(Integer.parseInt(numero), true, true);
                    this.jTFIdMarcaMotor.setText(Integer.toString(marcaMotor.getIdMarcaMotor()));
                    this.jTFNombre.setText(marcaMotor.getNombre());
                    this.estadoBotonesClicOperadores();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2022\n" + ex.getMessage(),
                    "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2022, UserHome.getUsuario(), ex);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2023\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2023, UserHome.getUsuario(), ex);
        }
    }
    
    private void obtenerMarcasMotor(){
        String mensajeError = "";
        try {
            DefaultTableModel modelo = (DefaultTableModel) this.jTCatalogoMarcasMotor.getModel();
            List<MarcaMotorDTO> marcasMotor = new MarcaMotorDAO().obtenerMarcasMotores();
            while(modelo.getRowCount() > 0){
                modelo.removeRow(modelo.getRowCount() - 1);
            }
            for(MarcaMotorDTO marcaMotor : marcasMotor){
                Object datos[] = {marcaMotor.getIdMarcaMotor(), marcaMotor.getNombre()};
                modelo.addRow(datos);
            }
        }  catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2024\n" + ex.getMessage(),
                    "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 2024, UserHome.getUsuario(), ex);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2025\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 2025, UserHome.getUsuario(), ex);
        }
    }
    
    private void estadoBotonesInicio(){
        try{
            this.jBAgregar.setEnabled(true);
            this.jBDarBaja.setEnabled(false);
            this.jBModificar.setEnabled(false);

            this.jMIAgregar.setEnabled(true);
            this.jMIDarBaja.setEnabled(false);
            this.jMIModificar.setEnabled(false);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2026\n" + ex.getMessage(),
                    "Error!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ControlMarcasMotores estadoBotonesInicio()", 2026, UserHome.getUsuario(), ex);
        }
    }
    
    private void estadoBotonesClicOperadores(){
        try{
            this.jBAgregar.setEnabled(false);
            this.jBDarBaja.setEnabled(true);
            this.jBModificar.setEnabled(true);

            this.jMIAgregar.setEnabled(false);
            this.jMIDarBaja.setEnabled(true);
            this.jMIModificar.setEnabled(true);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 2027\n" + ex.getMessage(),
                    "Error!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ControlMarcasMotores estadoBotonesClicOperadores", 2027, UserHome.getUsuario(), ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ControlMarcasMotores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlMarcasMotores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlMarcasMotores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlMarcasMotores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlMarcasMotores().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBDarBaja;
    private javax.swing.JMenuBar jBMMenu;
    private javax.swing.JButton jBModificar;
    private javax.swing.JLabel jLIdMarcaMotor;
    private javax.swing.JLabel jLNombre;
    private javax.swing.JMenu jMArchivo;
    private javax.swing.JMenu jMAyuda;
    private javax.swing.JMenu jMEditar;
    private javax.swing.JMenuItem jMIAgregar;
    private javax.swing.JMenuItem jMIDarBaja;
    private javax.swing.JMenuItem jMILimpiar;
    private javax.swing.JMenuItem jMIModificar;
    private javax.swing.JMenuItem jMISalir;
    private javax.swing.JMenuItem jMIVerManual;
    private javax.swing.JPanel jPCatalogoMarcasMotor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTCatalogoMarcasMotor;
    private javax.swing.JTextField jTFIdMarcaMotor;
    private javax.swing.JTextField jTFNombre;
    // End of variables declaration//GEN-END:variables
}