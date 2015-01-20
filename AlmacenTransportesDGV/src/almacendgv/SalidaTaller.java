/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package almacendgv;

import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.SalidaAlmacenDTO;
import beans.SalidaEspecialDTO;
import data.OrdenReparacionDAO;
import data.RefaccionDAO;
import data.SalidaAlmacenDAO;
import data.SalidaEspecialDAO;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class SalidaTaller extends javax.swing.JFrame {

    private RefaccionDTO refaccion;
    private String valorOriginal;
    private double precioUnitario;
    private double cantidad;
    private double total;
    private ControlSalidasAlmacen controlSalidas;
    
    /**
     * Creates new form SalidaBodega
     */
    public SalidaTaller() {
        try{
            initComponents();
            this.setLocationRelativeTo(null);

            this.refaccion = null;
            this.cantidad = 0.0;
            this.total = 0.0;
            this.precioUnitario = 0.0;

            this.actualizarListas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1084\n" + ex.getMessage(),
                    "Error al iniciar ventana Salida Especial!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("Constructor SalidaEspecial()", 1084, UserHome.getUsuario(), ex);
            this.dispose();
        }
    }

    public RefaccionDTO getRefaccion(){
        return this.refaccion;
    }
    
    public void setRefaccion(RefaccionDTO refaccion){
        this.refaccion = refaccion;
    }
    
    public ControlSalidasAlmacen getControlSalidas(){
        return controlSalidas;
    }
    
    public void setControlSalidas(ControlSalidasAlmacen controlSalidas){
        this.controlSalidas = controlSalidas;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLUsuario = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jLFecha = new javax.swing.JLabel();
        jTFFecha = new javax.swing.JTextField();
        jLNumeroSalida = new javax.swing.JLabel();
        jTFNumeroSalida = new javax.swing.JTextField();
        jLOrdenReparacion = new javax.swing.JLabel();
        jCBOrdenReparacion = new javax.swing.JComboBox();
        jLClaveRefaccion = new javax.swing.JLabel();
        jTFClaveRefaccion = new javax.swing.JTextField();
        jLCantidad = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jLPrecioUnitario = new javax.swing.JLabel();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jLTotal = new javax.swing.JLabel();
        jTFTotal = new javax.swing.JTextField();
        jLBeneficiario = new javax.swing.JLabel();
        jBAgregarSalida = new javax.swing.JButton();
        jLLogo = new javax.swing.JLabel();
        jTFBeneficiario = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMArchivo = new javax.swing.JMenu();
        jMIAgregarSalida = new javax.swing.JMenuItem();
        jMISalir = new javax.swing.JMenuItem();
        jMEditar = new javax.swing.JMenu();
        jMILimpiar = new javax.swing.JMenuItem();
        jMIBuscarRefaccion = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMIVerManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salida Especial de Almacen - Sistema de Administración Mantenimiento");

        jLUsuario.setText("Usuario:");

        jTFUsuario.setEditable(false);
        jTFUsuario.setFocusable(false);

        jLFecha.setText("Fecha:");

        jTFFecha.setEditable(false);
        jTFFecha.setFocusable(false);

        jLNumeroSalida.setText("Número Salida:");

        jTFNumeroSalida.setEditable(false);
        jTFNumeroSalida.setFocusable(false);

        jLOrdenReparacion.setText("Orden de Reparación:");

        jLClaveRefaccion.setText("Clave Refacción:");

        jTFClaveRefaccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFClaveRefaccionFocusLost(evt);
            }
        });

        jLCantidad.setText("Cantidad:");

        jTFCantidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFCantidadCaretUpdate(evt);
            }
        });
        jTFCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFCantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFCantidadFocusLost(evt);
            }
        });

        jLPrecioUnitario.setText("Precio Unitario:");

        jTFPrecioUnitario.setEditable(false);
        jTFPrecioUnitario.setFocusable(false);

        jLTotal.setText("Total:");

        jLBeneficiario.setText("Beneficiario:");

        jBAgregarSalida.setText("Agregar Salida Especial");
        jBAgregarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarSalidaActionPerformed(evt);
            }
        });

        jLLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Logo Efectivo Negro chico.png"))); // NOI18N

        jMArchivo.setText("Archivo");

        jMIAgregarSalida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMIAgregarSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Apply-16.png"))); // NOI18N
        jMIAgregarSalida.setText("Agregar Salida Especial");
        jMIAgregarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAgregarSalidaActionPerformed(evt);
            }
        });
        jMArchivo.add(jMIAgregarSalida);

        jMISalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMISalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit-16.png"))); // NOI18N
        jMISalir.setText("Salir");
        jMISalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMISalirActionPerformed(evt);
            }
        });
        jMArchivo.add(jMISalir);

        jMenuBar1.add(jMArchivo);

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

        jMIBuscarRefaccion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMIBuscarRefaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16.png"))); // NOI18N
        jMIBuscarRefaccion.setText("Buscar Refacción");
        jMIBuscarRefaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBuscarRefaccionActionPerformed(evt);
            }
        });
        jMEditar.add(jMIBuscarRefaccion);

        jMenuBar1.add(jMEditar);

        jMenu3.setText("Ayuda");

        jMIVerManual.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMIVerManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Help-16.png"))); // NOI18N
        jMIVerManual.setText("Ver Manual");
        jMIVerManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIVerManualActionPerformed(evt);
            }
        });
        jMenu3.add(jMIVerManual);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLBeneficiario)
                    .addComponent(jLPrecioUnitario)
                    .addComponent(jLClaveRefaccion)
                    .addComponent(jLNumeroSalida)
                    .addComponent(jLUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTFNumeroSalida)
                    .addComponent(jTFClaveRefaccion)
                    .addComponent(jTFPrecioUnitario)
                    .addComponent(jTFBeneficiario))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLFecha)
                            .addComponent(jLOrdenReparacion)
                            .addComponent(jLCantidad)
                            .addComponent(jLTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFFecha)
                            .addComponent(jCBOrdenReparacion, 0, 200, Short.MAX_VALUE)
                            .addComponent(jTFCantidad)
                            .addComponent(jTFTotal)))
                    .addComponent(jBAgregarSalida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLLogo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLLogo)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLUsuario)
                            .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLFecha)
                            .addComponent(jTFFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLNumeroSalida)
                            .addComponent(jTFNumeroSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLOrdenReparacion)
                            .addComponent(jCBOrdenReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLClaveRefaccion)
                            .addComponent(jTFClaveRefaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLCantidad)
                            .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLPrecioUnitario)
                            .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLTotal)
                            .addComponent(jTFTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLBeneficiario)
                            .addComponent(jBAgregarSalida)
                            .addComponent(jTFBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Salida Especial de Almacén - Grupo REMEX");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMIAgregarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAgregarSalidaActionPerformed
        this.agregar();
    }//GEN-LAST:event_jMIAgregarSalidaActionPerformed

    private void jMISalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMISalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMISalirActionPerformed

    private void jMILimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMILimpiarActionPerformed
        this.limpiar();
    }//GEN-LAST:event_jMILimpiarActionPerformed

    private void jMIBuscarRefaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBuscarRefaccionActionPerformed
        this.buscar();
    }//GEN-LAST:event_jMIBuscarRefaccionActionPerformed

    private void jMIVerManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIVerManualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMIVerManualActionPerformed

    private void jBAgregarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarSalidaActionPerformed
        this.agregar();
    }//GEN-LAST:event_jBAgregarSalidaActionPerformed

    private void jTFClaveRefaccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFClaveRefaccionFocusLost
        this.mostrarPrecioRefaccion();
    }//GEN-LAST:event_jTFClaveRefaccionFocusLost

    private void jTFCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusLost
        double cant = 0.0;
        try{
            cant = Double.parseDouble(this.valorOriginal);
        } catch(Exception ex) {
            cant = 0.0;
        }
        this.onFormatErrorSetValue(this.jTFCantidad, cant);
    }//GEN-LAST:event_jTFCantidadFocusLost

    private void jTFCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusGained
        this.prepararCaptura(this.jTFCantidad);
    }//GEN-LAST:event_jTFCantidadFocusGained

    private void jTFCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFCantidadCaretUpdate
        this.calcularTotal();
    }//GEN-LAST:event_jTFCantidadCaretUpdate

    private void agregar(){
        SalidaAlmacenDTO salidaAlmacen = new SalidaAlmacenDTO();
        SalidaEspecialDTO salidaEspecial = new SalidaEspecialDTO();
        try{
            if("".equals(this.jTFBeneficiario.getText()) || this.jTFBeneficiario == null){
                JOptionPane.showMessageDialog(this, "Aún no se agrega un beneficiario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SalidaEspecialDAO accesoAlmacen = new SalidaEspecialDAO();
            OrdenReparacionDTO ordenReparacion = new OrdenReparacionDTO();
            OrdenReparacionDAO accesoReparacion = new OrdenReparacionDAO();
            RefaccionDTO refaccionReq = new RefaccionDTO();
            RefaccionDAO accesoRefaccion = new RefaccionDAO();
            this.mostrarPrecioRefaccion();
            
            boolean agregarSalidaEspecialExitoso = false;
            double cantidadPiezas = Double.parseDouble(this.jTFCantidad.getText());
            double precioUnitarioRef = Double.parseDouble(this.jTFPrecioUnitario.getText());
            double costo = cantidadPiezas * precioUnitarioRef;
            String claveRefaccion = ((this.jTFClaveRefaccion != null && !"".equals(this.jTFClaveRefaccion.getText())) ? this.jTFClaveRefaccion.getText() : "");
            double existenciaPiezas = accesoRefaccion.obtenerExistenciaRefaccion(claveRefaccion, true, true);
            int numeroOrden = Integer.parseInt(this.jCBOrdenReparacion.getSelectedItem().toString());
            String nombreBeneficiario = ((this.jTFBeneficiario != null && !"".equals(this.jTFBeneficiario.getText())) ? this.jTFBeneficiario.getText() : "");
            nombreBeneficiario = nombreBeneficiario.trim();
            
            refaccionReq = accesoRefaccion.obtenerRefaccion(claveRefaccion, true, false);
            ordenReparacion = accesoReparacion.obtenerOrdenReparacion(numeroOrden, true, false, true);
            
            //validar que exista la clave de refaccion en el inventario
            if(refaccionReq == null){
                JOptionPane.showMessageDialog(null, "La clave de refacción\nno existe en el inventario.",
                    "Refacción no econtrada!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            //validar la cantidad de piezas en el inventario
            if(existenciaPiezas < cantidadPiezas){
                JOptionPane.showMessageDialog(null, "El inventario actual no\npuede cubrir la salida\nde almacén indicada",
                    "Inventario insuficiente!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            //validar que nombre del beneficiario sea valido
            if(nombreBeneficiario.equals("")){
                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de\nbeneficiario.",
                    "Nombre de Beneficiario inválido!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            salidaAlmacen.setCantidad(cantidadPiezas);
            salidaAlmacen.setCosto(costo);
            salidaAlmacen.setOrdenReparacion(ordenReparacion);
            salidaAlmacen.setRefaccion(refaccionReq);
            salidaAlmacen.setStatus(true);
            salidaAlmacen.setTipo(2);
            salidaAlmacen.setUsuario(UserHome.getUsuario());
            
            
            //Validar que la cantidad sea un valor válido
            if(salidaAlmacen.getCantidad() < 1){
                JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida",
                    "Cantidad inválida!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            
            //salidaAlmacen.setNumeroSalida(numeroSalida);
            
            salidaEspecial = new SalidaEspecialDTO(0, nombreBeneficiario, salidaAlmacen);
            
            agregarSalidaEspecialExitoso = accesoAlmacen.agregarSalidaEspecial(salidaEspecial);
            
            if(!agregarSalidaEspecialExitoso){
                boolean reparacionExitosa = false;
                reparacionExitosa = accesoAlmacen.repararErrorAgregarSalidaEspecial();
                if(!reparacionExitosa){
                    JOptionPane.showMessageDialog(null, "Código error: 1085\n" + "No se pudo reparar la tabla",
                    "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(salidaAlmacen.toString() + salidaEspecial.toString(), 1085, UserHome.getUsuario(), new Exception("#NA"));

                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                    "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            this.controlSalidas.obtenerSalidas();
            this.limpiar();
            this.mostrarValores();
            
        } catch (SQLException ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 1086\n" + ex.getMessage(),
                        "Error al obtener datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(salidaAlmacen.toString() + salidaEspecial.toString(), 1086, UserHome.getUsuario(), ex);
                boolean reparacionExitosa = false;
                SalidaEspecialDAO accesoSalidaEspecial = new SalidaEspecialDAO();
                reparacionExitosa = accesoSalidaEspecial.repararErrorAgregarSalidaEspecial();
                if(!reparacionExitosa){
                    JOptionPane.showMessageDialog(null, "Código error: 1087\n" + "No se pudo reparar la tabla",
                    "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(salidaAlmacen.toString() + salidaEspecial.toString(), 1087, UserHome.getUsuario(), ex);

                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                    "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(SalidaEspecial.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 1088\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(salidaAlmacen.toString() + salidaEspecial.toString(), 1088, UserHome.getUsuario(), ex);

                boolean reparacionExitosa = false;
                SalidaEspecialDAO accesoSalidaEspecial = new SalidaEspecialDAO();
                reparacionExitosa = accesoSalidaEspecial.repararErrorAgregarSalidaEspecial();
                if(!reparacionExitosa){
                    JOptionPane.showMessageDialog(null, "Código error: 1089\n" + "No se pudo reparar la tabla",
                    "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(salidaAlmacen.toString() + salidaEspecial.toString(), 1089, UserHome.getUsuario(), ex);

                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                    "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(SalidaEspecial.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
      
    }
    
    private void limpiar(){
        try {
            this.jTFClaveRefaccion.setText(null);
            this.jTFFecha.setText(null);
            this.jTFNumeroSalida.setText(null);
            this.jCBOrdenReparacion.setSelectedIndex(0);
            this.jTFBeneficiario.setText(null);
            this.mostrarValores();
            this.actualizarListas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1090\n"
                    + "Error al limpiar los datos.", "Error!!!",
                    JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial limpiar()", 1090, UserHome.getUsuario(), ex);

        }
    }
    
    private void buscar(){
        BuscarCampoSimple buscarCampo = new BuscarCampoSimple();
        buscarCampo.setTipoRespuesta(1);
        buscarCampo.setRecibirTexto(this.jTFClaveRefaccion);
        buscarCampo.setLocationRelativeTo(null);
        buscarCampo.setVisible(true);
    }
    
    private double obtenerRefaccionPrecioUnitario(){
        double precio = 0.00;
        try {
            RefaccionDAO refaccion = new RefaccionDAO();
            String claveRefaccion = ((this.jTFClaveRefaccion != null && !"".equals(this.jTFClaveRefaccion.getText())) ? this.jTFClaveRefaccion.getText() : "");
            precio = refaccion.obtenerPrecioRefaccion(claveRefaccion, true, true);
        } catch (SQLException ex) {
            precio = 0.00;
            JOptionPane.showMessageDialog(null, "Código error: 1091\n" + ex.getMessage(),
                    "Error al obtener el precio de la refaccion en la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial obtenerRefaccionPrecioUnitario()", 1091, UserHome.getUsuario(), ex);

        } catch (Exception ex) {
            precio = 0.00;
            JOptionPane.showMessageDialog(null, "Código error: 1092\n" + ex.getMessage(),
                    "Error al obtener el precio!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial obtenerRefaccionPrecioUnitario()", 1092, UserHome.getUsuario(), ex);
        }
        return precio;
    }
    
    private void calcularTotal(){
        try{
            DecimalFormat formatD = new DecimalFormat("0.000");
            double cantidadReq = Double.parseDouble(((this.jTFCantidad != null && !"".
                    equals(this.jTFCantidad.getText())) ? this.jTFCantidad.getText() : "0.000"));
            
            double precio = Double.parseDouble(((this.jTFPrecioUnitario != null && !"".
                    equals(this.jTFPrecioUnitario.getText())) ? this.jTFPrecioUnitario.getText() : "0.000"));
            double totalRef = (double)(cantidadReq * precio);
            this.jTFTotal.setText(formatD.format(totalRef));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1093\n" + ex.getMessage(),
                    "Error al calcular el total!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial calcularTotal()", 1093, UserHome.getUsuario(), ex);
        }
    }
    
    private void prepararCaptura(javax.swing.JTextField campo){
        try {
            this.valorOriginal = ((campo != null && !"".equals(campo.getText())) ? campo.getText() : "");
            campo.setText(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1094\n"
                    + "Error al tomar el valor del campo.", "Error!!!",
                    JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial prepararCaptura()", 1094, UserHome.getUsuario(), ex);
        }
    }
    
    private void valorOriginal(javax.swing.JTextField campo){
        try {
            campo.setText(this.valorOriginal);
            this.valorOriginal = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1095\n"
                    + "Error al insertar el valor\noriginal del campo.", "Error!!!",
                    JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial valorOriginal()", 1095, UserHome.getUsuario(), ex);
        }
    }
    
    public void mostrarValores(){
        try {
            this.jTFCantidad.setText("0.000");
            this.jTFUsuario.setText(UserHome.getUsuario().getNombre() + " " + UserHome.getUsuario().getApellidos());
            this.jTFPrecioUnitario.setText("0.000");
            this.jTFTotal.setText("0.000");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1096\n"
                    + "Error al mostrar los valores.", "Error!!!",
                    JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial mostrarValores()", 1096, UserHome.getUsuario(), ex);
        }
    }
    
    private void obtenerOrdenesReparacionPendientes(){
        try {
            List<OrdenReparacionDTO> ordenesReparacion = new OrdenReparacionDAO().
                    obtenerOrdenesReparacionPendientes(true);
            this.jCBOrdenReparacion.removeAllItems();
            for(OrdenReparacionDTO ordenReparacion : ordenesReparacion){
                this.jCBOrdenReparacion.addItem(ordenReparacion.getNumeroOrden());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1097\n" + ex.getMessage(),
                    "Error al obtener datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial obtenerOrdenesReparacionPendientes()", 1097, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1098\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial obtenerOrdenesReparacionPendientes()", 1098, UserHome.getUsuario(), ex);
        }
    }
    
    private void onFormatErrorSetValue(javax.swing.JTextField campo, double value){
        DecimalFormat formatD  = new DecimalFormat("0.000");
        try{
            double n = Double.parseDouble(campo.getText());
            campo.setText(formatD.format(n));
        } catch (Exception ex) {
            campo.setText(formatD.format(value));
        }
    }
    
    private void onFormatErrorSetValue(javax.swing.JTextField campo, int value){
        try{
            int n = (int)(Double.parseDouble(campo.getText()));
            campo.setText(Integer.toString(n));
        } catch (Exception ex) {
            campo.setText(Integer.toString(value));
        }
    }
    
    private void actualizarListas(){
        this.obtenerOrdenesReparacionPendientes();
    }
    
    private void mostrarPrecioRefaccion(){
        try {
            DecimalFormat formatD = new DecimalFormat("0.000");
            this.jTFPrecioUnitario.setText(formatD.format(this.obtenerRefaccionPrecioUnitario()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1099\n" + ex.getMessage() 
                    + "\nError al obtener el precio de la refacción.\nSe borrara la información de"
                    + "\nlos campos de texto por seguridad.", "Error!!!",
                        JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("SalidaEspecial mostrarPrecioRefaccion()", 1099, UserHome.getUsuario(), ex);
            this.limpiar();
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
            java.util.logging.Logger.getLogger(SalidaTaller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalidaTaller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalidaTaller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalidaTaller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalidaTaller().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregarSalida;
    private javax.swing.JComboBox jCBOrdenReparacion;
    private javax.swing.JLabel jLBeneficiario;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLClaveRefaccion;
    private javax.swing.JLabel jLFecha;
    private javax.swing.JLabel jLLogo;
    private javax.swing.JLabel jLNumeroSalida;
    private javax.swing.JLabel jLOrdenReparacion;
    private javax.swing.JLabel jLPrecioUnitario;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JLabel jLUsuario;
    private javax.swing.JMenu jMArchivo;
    private javax.swing.JMenu jMEditar;
    private javax.swing.JMenuItem jMIAgregarSalida;
    private javax.swing.JMenuItem jMIBuscarRefaccion;
    private javax.swing.JMenuItem jMILimpiar;
    private javax.swing.JMenuItem jMISalir;
    private javax.swing.JMenuItem jMIVerManual;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JTextField jTFBeneficiario;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFClaveRefaccion;
    private javax.swing.JTextField jTFFecha;
    private javax.swing.JTextField jTFNumeroSalida;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTextField jTFUsuario;
    // End of variables declaration//GEN-END:variables
}
