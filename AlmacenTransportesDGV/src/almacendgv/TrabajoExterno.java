/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package almacendgv;

import beans.FacturaDTO;
import beans.OrdenReparacionDTO;
import beans.TrabajoExternoDTO;
import beans.TransporteReparacionDTO;
import beans.UnidadTransporteDTO;
import data.OrdenReparacionDAO;
import data.TrabajoExternoDAO;
import data.TransporteReparacionDAO;
import data.UnidadTransporteDAO;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class TrabajoExterno extends javax.swing.JFrame {

    private FacturaDTO factura;
    private ControlFacturasProveedor controlFacturas;
    private String valorOriginal;
    
    /**
     * Creates new form TrabajoExterno
     */
    public TrabajoExterno() {
        try{
            initComponents();
            this.setLocationRelativeTo(null);
            this.jTFCantidad.setText("0.000");
            this.jTFPrecioUnitario.setText("0.000");
            this.jTFTotal.setText("0.000");
            this.obtenerOrdenesReparacion();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1138\n" + ex.getMessage(),
                    "Error al iniciar ventana de Trabajo Externo!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("Constructor TrabajoExterno()", 1138, UserHome.getUsuario(), ex);
            this.dispose();
        }
    }

    public FacturaDTO getFactura(){
        return this.factura;
    }
    
    public void setFactura(FacturaDTO factura){
        this.factura = factura;
    }
    
    public ControlFacturasProveedor getControlFacturas(){
        return this.controlFacturas;
    }
    
    public void setControlFacturas(ControlFacturasProveedor controlFacturas){
        this.controlFacturas = controlFacturas;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProveedor = new javax.swing.JLabel();
        jTFProveedor = new javax.swing.JTextField();
        jLFolioFactura = new javax.swing.JLabel();
        jTFFolioFactura = new javax.swing.JTextField();
        jLNumeroTrabajo = new javax.swing.JLabel();
        jTFNumeroTrabajo = new javax.swing.JTextField();
        jLDescripcion = new javax.swing.JLabel();
        jTFDescripcion = new javax.swing.JTextField();
        jLCantidad = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jLPrecioUnitario = new javax.swing.JLabel();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jLTotal = new javax.swing.JLabel();
        jTFTotal = new javax.swing.JTextField();
        jBAgregarTrabajo = new javax.swing.JButton();
        jLLogo = new javax.swing.JLabel();
        jLOrdenReparacion = new javax.swing.JLabel();
        jCBOrdenReparacion = new javax.swing.JComboBox();
        jLClaveUnidad = new javax.swing.JLabel();
        jLUsuario = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jCBClaveUnidad = new javax.swing.JComboBox();
        jMBMenu = new javax.swing.JMenuBar();
        jMArchivo = new javax.swing.JMenu();
        jMIAgregar = new javax.swing.JMenuItem();
        jMISalir = new javax.swing.JMenuItem();
        jMEditar = new javax.swing.JMenu();
        jMILimpiar = new javax.swing.JMenuItem();
        jMAyuda = new javax.swing.JMenu();
        jMIVerManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trabajo Externo - Sistema de Administración Mantenimiento");
        setResizable(false);

        jLProveedor.setText("Proveedor:");

        jTFProveedor.setEditable(false);
        jTFProveedor.setFocusable(false);

        jLFolioFactura.setText("Folio Factura:");

        jTFFolioFactura.setEditable(false);
        jTFFolioFactura.setFocusable(false);

        jLNumeroTrabajo.setText("# Trabajo Externo:");

        jTFNumeroTrabajo.setEditable(false);
        jTFNumeroTrabajo.setFocusable(false);

        jLDescripcion.setText("Descripción:");

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

        jTFPrecioUnitario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTFPrecioUnitarioCaretUpdate(evt);
            }
        });
        jTFPrecioUnitario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFPrecioUnitarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFPrecioUnitarioFocusLost(evt);
            }
        });

        jLTotal.setText("Total:");

        jBAgregarTrabajo.setText("Agregar Trabajo Externo");
        jBAgregarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarTrabajoActionPerformed(evt);
            }
        });

        jLLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Logo Efectivo Negro chico.png"))); // NOI18N

        jLOrdenReparacion.setText("Orden de Reparación:");

        jCBOrdenReparacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBOrdenReparacionItemStateChanged(evt);
            }
        });

        jLClaveUnidad.setText("Clave Unidad:");

        jLUsuario.setText("Usuario:");

        jTFUsuario.setEditable(false);
        jTFUsuario.setFocusable(false);

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

        jMISalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMISalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit-16.png"))); // NOI18N
        jMISalir.setText("Salir");
        jMISalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMISalirActionPerformed(evt);
            }
        });
        jMArchivo.add(jMISalir);

        jMBMenu.add(jMArchivo);

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

        jMBMenu.add(jMEditar);

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

        jMBMenu.add(jMAyuda);

        setJMenuBar(jMBMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLTotal)
                    .addComponent(jLCantidad)
                    .addComponent(jLOrdenReparacion)
                    .addComponent(jLFolioFactura)
                    .addComponent(jLUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBAgregarTrabajo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTFCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(jCBOrdenReparacion, 0, 170, Short.MAX_VALUE)
                                        .addComponent(jTFUsuario))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(jLPrecioUnitario))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLDescripcion)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLProveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLClaveUnidad, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jTFFolioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                    .addComponent(jLNumeroTrabajo)))
                            .addComponent(jTFTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFNumeroTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBClaveUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jLLogo)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLProveedor)
                            .addComponent(jTFProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLUsuario)
                            .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLNumeroTrabajo)
                            .addComponent(jTFNumeroTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLFolioFactura)
                            .addComponent(jTFFolioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLDescripcion)
                            .addComponent(jTFDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLOrdenReparacion)
                            .addComponent(jCBOrdenReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLLogo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLPrecioUnitario)
                    .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCantidad)
                    .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTotal)
                    .addComponent(jTFTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLClaveUnidad)
                    .addComponent(jCBClaveUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jBAgregarTrabajo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAgregarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarTrabajoActionPerformed
        this.agregar();
    }//GEN-LAST:event_jBAgregarTrabajoActionPerformed

    private void jMIAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAgregarActionPerformed
        this.agregar();
    }//GEN-LAST:event_jMIAgregarActionPerformed

    private void jMISalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMISalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMISalirActionPerformed

    private void jMILimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMILimpiarActionPerformed
        this.limpiar();
    }//GEN-LAST:event_jMILimpiarActionPerformed

    private void jMIVerManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIVerManualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMIVerManualActionPerformed

    private void jTFCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFCantidadCaretUpdate
        DecimalFormat format = new DecimalFormat("0.000");
        try{
            
            if(!"".equals(this.jTFCantidad.getText()) && 
                    !"".equals(this.jTFTotal.getText()) && 
                    !"".equals(this.jTFPrecioUnitario.getText())){
            
                double total = Double.parseDouble(this.jTFCantidad.getText()) +
                        Double.parseDouble(this.jTFPrecioUnitario.getText());
                this.jTFTotal.setText(format.format(total));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1139\n" + ex.getMessage(),
                    "Error en el calculo de los valores!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno jTFCantidadCaretUpdate()", 1139, UserHome.getUsuario(), ex);
        }
    }//GEN-LAST:event_jTFCantidadCaretUpdate

    private void jTFPrecioUnitarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioCaretUpdate
        DecimalFormat format = new DecimalFormat("0.000");
        try{
            
            if(!"".equals(this.jTFCantidad.getText()) && 
                    !"".equals(this.jTFTotal.getText()) && 
                    !"".equals(this.jTFPrecioUnitario.getText())){
            
                double total = Double.parseDouble(this.jTFCantidad.getText()) +
                        Double.parseDouble(this.jTFPrecioUnitario.getText());
                this.jTFTotal.setText(format.format(total));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1140\n" + ex.getMessage(),
                    "Error en el calculo de los valores!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno jTFPrecioUnitarioCaretUpdate()", 1140, UserHome.getUsuario(), ex);
        }
    }//GEN-LAST:event_jTFPrecioUnitarioCaretUpdate

    private void jTFCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusLost
        double cant = 0.00;
        try{
            cant = Double.parseDouble(this.valorOriginal);
        } catch(Exception ex) {
            cant = 0.00;
        }
        this.onFormatErrorSetValue(this.jTFCantidad, cant);
    }//GEN-LAST:event_jTFCantidadFocusLost

    private void jTFPrecioUnitarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioFocusLost
        double cant = 0.00;
        try{
            cant = Double.parseDouble(this.valorOriginal);
        } catch(Exception ex) {
            cant = 0.00;
        }
        this.onFormatErrorSetValue(this.jTFPrecioUnitario, cant);
    }//GEN-LAST:event_jTFPrecioUnitarioFocusLost

    private void jTFCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusGained
        this.prepararCaptura(this.jTFCantidad);
    }//GEN-LAST:event_jTFCantidadFocusGained

    private void jTFPrecioUnitarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioFocusGained
        this.prepararCaptura(this.jTFPrecioUnitario);
    }//GEN-LAST:event_jTFPrecioUnitarioFocusGained

    private void jCBOrdenReparacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBOrdenReparacionItemStateChanged
        this.mostrarUnidadesTransportePReparacion();
    }//GEN-LAST:event_jCBOrdenReparacionItemStateChanged

    private void agregar(){
        TrabajoExternoDTO trabajoExterno = new TrabajoExternoDTO();
        try {
            TrabajoExternoDAO acceso = new TrabajoExternoDAO();
            OrdenReparacionDAO accesoReparacion = new OrdenReparacionDAO();
            String claveUnidad = ((this.jCBClaveUnidad.getSelectedItem() != null && !"".equals(this.jCBClaveUnidad.getSelectedItem().toString())) ? this.jCBClaveUnidad.getSelectedItem().toString() : "");
            OrdenReparacionDTO ordenReparacion = accesoReparacion.obtenerOrdenReparacion(Integer.parseInt(this.jCBOrdenReparacion.getSelectedItem().toString()), true, true, true);
            UnidadTransporteDAO accesoTransporte = new UnidadTransporteDAO();
            UnidadTransporteDTO unidadTransporte = accesoTransporte.obtenerUnidad(claveUnidad, true, true, true);
            
            if(!this.validarTransportesPReparacion(this.jCBClaveUnidad.getSelectedItem().toString(), ordenReparacion)){
                JOptionPane.showMessageDialog(null, "La clave de Unidad de Transporte\nno pertenece "
                        + "a la orden de\nreparación indicada.\nVerifique los datos e inténtelo\notra vez."
                        , "Datos Erroneos!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            trabajoExterno.setCantidad(Double.parseDouble(this.jTFCantidad.getText()));
            trabajoExterno.setDescripcion(this.jTFDescripcion.getText());
            trabajoExterno.setFactura(factura);
            trabajoExterno.setIva(0.0);
            trabajoExterno.setMonto(Double.parseDouble(this.jTFTotal.getText()));
            trabajoExterno.setPrecioUnitario(Double.parseDouble(this.jTFPrecioUnitario.getText()));
            trabajoExterno.setStatus(true);
            trabajoExterno.setSubtotal(0.0);
            trabajoExterno.setOrdenReparacion(ordenReparacion);
            trabajoExterno.setUnidadTransporte(unidadTransporte);
            trabajoExterno.setUsuario(UserHome.getUsuario());
            
            //Validar que la cantidad sea un valor válido
            if(trabajoExterno.getCantidad() < 1){
                JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida",
                    "Cantidad inválida!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            acceso.agregarTrabajoExterno(trabajoExterno);
            
            controlFacturas.actualizarTablas();
            this.limpiar();
        } catch (SQLException ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 1144\n" + ex.getMessage(),
                        "Error al guardar los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(trabajoExterno.toString(), 1144, UserHome.getUsuario(), ex);

                boolean reparacionExitosa = false;
                TrabajoExternoDAO accesoTrabajoExterno = new TrabajoExternoDAO();
                reparacionExitosa = accesoTrabajoExterno.repararErrorAgregarTrabajoExterno();
                if(!reparacionExitosa){
                    JOptionPane.showMessageDialog(null, "Código error: 1145\n" + "No se pudo reparar la tabla",
                    "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(trabajoExterno.toString(), 1145, UserHome.getUsuario(), ex);
                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                    "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(TrabajoExterno.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 1146\n" + ex.getMessage(),
                        "Error al obtener los datos ingresados!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(trabajoExterno.toString(), 1146, UserHome.getUsuario(), ex);

                boolean reparacionExitosa = false;
                TrabajoExternoDAO accesoTrabajoExterno = new TrabajoExternoDAO();
                reparacionExitosa = accesoTrabajoExterno.repararErrorAgregarTrabajoExterno();
                if(!reparacionExitosa){
                    JOptionPane.showMessageDialog(null, "Código error: 1147\n" + "No se pudo reparar la tabla",
                    "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(trabajoExterno.toString(), 1147, UserHome.getUsuario(), ex);
                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                    "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(TrabajoExterno.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    private void obtenerOrdenesReparacion(){
        try {
            List<OrdenReparacionDTO> ordenesReparacion = new OrdenReparacionDAO().
                    obtenerOrdenesReparacionPendientes(true);
            for(OrdenReparacionDTO ordenReparacion : ordenesReparacion){
                this.jCBOrdenReparacion.addItem(ordenReparacion.getNumeroOrden());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1148\n" + ex.getMessage(),
                    "Error al obtener datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno obtenerOrdenesReparacion()", 1148, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1149\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno obtenerOrdenesReparacion()", 1149, UserHome.getUsuario(), ex);
        }
    }
    
    private void limpiar(){
        try{
            this.mostrarValores();
            this.jTFDescripcion.setText(null);
            this.jCBClaveUnidad.removeAllItems();
            this.jCBOrdenReparacion.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1150\n" + ex.getMessage(),
                    "Error al limpiar los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno limpiar()", 1150, UserHome.getUsuario(), ex);
        }
    }
    
    public void mostrarValores(){
        try{
            this.jTFCantidad.setText("0.000");
            this.jTFPrecioUnitario.setText("0.000");
            this.jTFTotal.setText("0.000");
            this.jTFFolioFactura.setText(factura.getFolio());
            this.jTFProveedor.setText(factura.getProveedor().getNombre());
            this.jTFUsuario.setText(UserHome.getUsuario().getNombre() + " " + UserHome.getUsuario().getApellidos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1151\n" + ex.getMessage(),
                    "Error al mostrar los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno mostrarValores()", 1151, UserHome.getUsuario(), ex);
        }
    }
    
    private boolean validarTransportesPReparacion(String claveTransporte, OrdenReparacionDTO reparacion){
        String mensajeError = reparacion.toString();
        try{
            TransporteReparacionDAO accesoTransporteReparacion = new TransporteReparacionDAO();
            List<TransporteReparacionDTO> transportes = accesoTransporteReparacion.
                    obtenerTransportesPReparacion(reparacion, true, true, true, true);

            for(TransporteReparacionDTO transporte : transportes){
                mensajeError += transporte.toString();
                if(claveTransporte.equals(transporte.getTransporte().getClave())){
                    return true;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1152\n" + ex.getMessage(),
                    "Error al obtener datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 1152, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1153\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 1153, UserHome.getUsuario(), ex);
        }
        return false;
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
    
    private void prepararCaptura(javax.swing.JTextField campo){
        try{
            this.valorOriginal = ((campo != null && !"".equals(campo.getText())) ? campo.getText() : "");
            campo.setText(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1154\n" + ex.getMessage(),
                    "Error al preparar captura!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("TrabajoExterno prepararCaptura()", 1154, UserHome.getUsuario(), ex);
        }
    }
    
    private void mostrarUnidadesTransportePReparacion(){
        String mensajeError = "";
        try{
            int numeroOrden = Integer.parseInt(this.jCBOrdenReparacion.getSelectedItem().toString());
            OrdenReparacionDTO ordenReparacion = new OrdenReparacionDTO();
            OrdenReparacionDAO accesoReparacion = new OrdenReparacionDAO();
            TransporteReparacionDAO accesoTReparacion = new TransporteReparacionDAO();
            ordenReparacion = accesoReparacion.obtenerOrdenReparacion(numeroOrden, true, true, true);
            List<TransporteReparacionDTO> transportes = accesoTReparacion.obtenerTransportesPReparacion(ordenReparacion, true, true, true, true);
            
            
            this.jCBClaveUnidad.removeAllItems();
            
            for(TransporteReparacionDTO transporte : transportes){
                mensajeError = transporte.toString();
                this.jCBClaveUnidad.addItem(transporte.getTransporte().getClave());
            }
            
            mensajeError += ordenReparacion.toString();
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1216\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 1216, UserHome.getUsuario(), ex);

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1217\n" + ex.getMessage(),
                        "Error al obtener los datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 1217, UserHome.getUsuario(), ex);
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
            java.util.logging.Logger.getLogger(TrabajoExterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrabajoExterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrabajoExterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrabajoExterno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrabajoExterno().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregarTrabajo;
    private javax.swing.JComboBox jCBClaveUnidad;
    private javax.swing.JComboBox jCBOrdenReparacion;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLClaveUnidad;
    private javax.swing.JLabel jLDescripcion;
    private javax.swing.JLabel jLFolioFactura;
    private javax.swing.JLabel jLLogo;
    private javax.swing.JLabel jLNumeroTrabajo;
    private javax.swing.JLabel jLOrdenReparacion;
    private javax.swing.JLabel jLPrecioUnitario;
    private javax.swing.JLabel jLProveedor;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JLabel jLUsuario;
    private javax.swing.JMenu jMArchivo;
    private javax.swing.JMenu jMAyuda;
    private javax.swing.JMenuBar jMBMenu;
    private javax.swing.JMenu jMEditar;
    private javax.swing.JMenuItem jMIAgregar;
    private javax.swing.JMenuItem jMILimpiar;
    private javax.swing.JMenuItem jMISalir;
    private javax.swing.JMenuItem jMIVerManual;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFDescripcion;
    private javax.swing.JTextField jTFFolioFactura;
    private javax.swing.JTextField jTFNumeroTrabajo;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFProveedor;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTextField jTFUsuario;
    // End of variables declaration//GEN-END:variables
}
