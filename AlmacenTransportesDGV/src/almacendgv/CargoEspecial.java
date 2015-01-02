/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package almacendgv;

import beans.CargoDirectoDTO;
import beans.CargoEspecialDTO;
import beans.FacturaDTO;
import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.UsuarioDTO;
import data.CargoDirectoDAO;
import data.CargoEspecialDAO;
import data.OrdenReparacionDAO;
import data.RefaccionDAO;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class CargoEspecial extends javax.swing.JFrame {

    private FacturaDTO factura;
    private UsuarioDTO usuario;
    private ControlFacturasProveedor controlFacturas;
    private String valorOriginal;

    /**
     * Creates new form CargoEspecial
     */
    public CargoEspecial() {
        initComponents();
        try {
            this.setLocationRelativeTo(null);
            this.obtenerOrdenesReparacion();
            this.limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 720\n" + ex.getMessage(),
                    "Error al iniciar ventana Cargo Especial!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("Constructor CargoEspecial()", 720, UserHome.getUsuario(), ex);
            this.dispose();
        }
    }

    public void mostrarInformacion() {
        try {
            //this.jTFPorcentajeIVA.setText("16.000");
            this.jTFProveedor.setText(factura.getProveedor().getIdProveedor() + "# " + factura.getProveedor().getNombre());
            this.jTFFolio.setText(factura.getFolio());
            this.jTFUsuario.setText(UserHome.getUsuario().getNombre() + " " + UserHome.getUsuario().getApellidos());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 721\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial mostrarInformacion()", 721, UserHome.getUsuario(), ex);
        }
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ControlFacturasProveedor getControlFacturas() {
        return this.controlFacturas;
    }

    public void setControlFacturas(ControlFacturasProveedor controlFacturas) {
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

        jLUsuario = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jLProveedor = new javax.swing.JLabel();
        jTFProveedor = new javax.swing.JTextField();
        jLFolio = new javax.swing.JLabel();
        jTFFolio = new javax.swing.JTextField();
        jLOrdenReparacion = new javax.swing.JLabel();
        jCBOrdenReparacion = new javax.swing.JComboBox();
        jLNumeroCargoEspecial = new javax.swing.JLabel();
        jTFCargoEspecial = new javax.swing.JTextField();
        jLClaveRefaccion = new javax.swing.JLabel();
        jTFClaveRefaccion = new javax.swing.JTextField();
        jLPrecioUnitario = new javax.swing.JLabel();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jLCantidad = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jLTotal = new javax.swing.JLabel();
        jTFTotal = new javax.swing.JTextField();
        jLLogo = new javax.swing.JLabel();
        jBAgregarCargo = new javax.swing.JButton();
        jLBeneficiario = new javax.swing.JLabel();
        jTFBeneficiario = new javax.swing.JTextField();
        jMBMenu = new javax.swing.JMenuBar();
        jMArchivo = new javax.swing.JMenu();
        jMIAgregar = new javax.swing.JMenuItem();
        jMISalir = new javax.swing.JMenuItem();
        jMEditar = new javax.swing.JMenu();
        jMILimpiar = new javax.swing.JMenuItem();
        jMIBuscarParte = new javax.swing.JMenuItem();
        jMAyuda = new javax.swing.JMenu();
        jMIVerManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cargo Especial - Sistema de Administración Mantenimiento");

        jLUsuario.setText("Usuario:");

        jTFUsuario.setEditable(false);
        jTFUsuario.setFocusable(false);

        jLProveedor.setText("Proveedor:");

        jTFProveedor.setEditable(false);
        jTFProveedor.setFocusable(false);

        jLFolio.setText("Folio:");

        jTFFolio.setEditable(false);
        jTFFolio.setFocusable(false);

        jLOrdenReparacion.setText("Orden de Reparación:");

        jLNumeroCargoEspecial.setText("# Cargo Especial:");

        jTFCargoEspecial.setEditable(false);
        jTFCargoEspecial.setFocusable(false);

        jLClaveRefaccion.setText("Clave de Refacción:");

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

        jLTotal.setText("Total:");

        jLLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Logo Efectivo Negro chico.png"))); // NOI18N

        jBAgregarCargo.setText("Agregar Cargo Especial");
        jBAgregarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarCargoActionPerformed(evt);
            }
        });

        jLBeneficiario.setText("Nombre del Beneficiario:");

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

        jMIBuscarParte.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMIBuscarParte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16.png"))); // NOI18N
        jMIBuscarParte.setText("Buscar Parte");
        jMIBuscarParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBuscarParteActionPerformed(evt);
            }
        });
        jMEditar.add(jMIBuscarParte);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLPrecioUnitario)
                            .addComponent(jLNumeroCargoEspecial)
                            .addComponent(jLFolio)
                            .addComponent(jLUsuario)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLTotal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTFTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jTFUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFFolio, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFCargoEspecial, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLProveedor)
                            .addComponent(jLOrdenReparacion)
                            .addComponent(jLClaveRefaccion)
                            .addComponent(jLCantidad)
                            .addComponent(jLBeneficiario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFProveedor)
                            .addComponent(jCBOrdenReparacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTFClaveRefaccion)
                            .addComponent(jTFCantidad)
                            .addComponent(jTFBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBAgregarCargo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLLogo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLUsuario)
                            .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLProveedor)
                            .addComponent(jTFProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLFolio)
                            .addComponent(jTFFolio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLOrdenReparacion)
                            .addComponent(jCBOrdenReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLNumeroCargoEspecial)
                            .addComponent(jTFCargoEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLClaveRefaccion)
                            .addComponent(jTFClaveRefaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jTFBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLBeneficiario))
                .addGap(18, 18, 18)
                .addComponent(jBAgregarCargo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jBAgregarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarCargoActionPerformed
        this.agregar();
    }//GEN-LAST:event_jBAgregarCargoActionPerformed

    private void jTFPrecioUnitarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioCaretUpdate
        DecimalFormat format = new DecimalFormat("0.000");
        try {

            if (!"".equals(this.jTFCantidad.getText()) && !"".equals(this.jTFTotal.getText()) 
                    && !"".equals(this.jTFPrecioUnitario.getText())) {

                double total = Double.parseDouble(this.jTFCantidad.getText())
                        * Double.parseDouble(this.jTFPrecioUnitario.getText());
                this.jTFTotal.setText(format.format(total));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 722\n" + ex.getMessage(),
                    "Error en el calculo de los valores!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial jTFPrecioUnitarioCaretUpdate()", 722, UserHome.getUsuario(), ex);
        }
    }//GEN-LAST:event_jTFPrecioUnitarioCaretUpdate

    private void jTFCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTFCantidadCaretUpdate
        DecimalFormat format = new DecimalFormat("0.000");
        try {

            if (!"".equals(this.jTFCantidad.getText()) && !"".equals(this.jTFTotal.getText()) 
                    && !"".equals(this.jTFPrecioUnitario.getText())) {

                double total = Double.parseDouble(this.jTFCantidad.getText())
                        * Double.parseDouble(this.jTFPrecioUnitario.getText());
                this.jTFTotal.setText(format.format(total));
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 723\n" + ex.getMessage(),
                    "Error en el calculo de los valores!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial jTFCantidadCaretUpdate()", 723, UserHome.getUsuario(), ex);
        }
    }//GEN-LAST:event_jTFCantidadCaretUpdate

    private void jTFPrecioUnitarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioFocusLost
        double cant = 0.00;
        try {
            cant = Double.parseDouble(this.valorOriginal);
        } catch (Exception ex) {
            cant = 0.00;
        }
        this.onFormatErrorSetValue(this.jTFPrecioUnitario, cant);
    }//GEN-LAST:event_jTFPrecioUnitarioFocusLost

    private void jTFCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusLost
        double cant = 0.00;
        try {
            cant = Double.parseDouble(this.valorOriginal);
        } catch (Exception ex) {
            cant = 0.00;
        }
        this.onFormatErrorSetValue(this.jTFCantidad, cant);
    }//GEN-LAST:event_jTFCantidadFocusLost

    private void jTFPrecioUnitarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFPrecioUnitarioFocusGained
        this.prepararCaptura(this.jTFPrecioUnitario);
    }//GEN-LAST:event_jTFPrecioUnitarioFocusGained

    private void jTFCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFCantidadFocusGained
        this.prepararCaptura(this.jTFCantidad);
    }//GEN-LAST:event_jTFCantidadFocusGained

    private void jMIBuscarParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBuscarParteActionPerformed
        this.buscar();
    }//GEN-LAST:event_jMIBuscarParteActionPerformed

    public void agregar() {
        CargoDirectoDTO cargoDirecto = new CargoDirectoDTO();
        CargoEspecialDTO cargoEspecial = new CargoEspecialDTO();
        try {
            if ("".equals(this.jTFBeneficiario.getText()) || this.jTFBeneficiario == null) {
                JOptionPane.showMessageDialog(this, "Aún no se agrega un beneficiario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            CargoDirectoDAO accesoCargoDirecto = new CargoDirectoDAO();
            CargoEspecialDAO accesoCargoEspecial = new CargoEspecialDAO();
            RefaccionDAO accesoRefaccion = new RefaccionDAO();
            RefaccionDTO refaccionReq = new RefaccionDTO();
            OrdenReparacionDAO ordenReparacion = new OrdenReparacionDAO();
            FacturaDTO fact = this.factura;
            String nombreBeneficiario = "";
            String claveRefaccion = "";
            int numeroCargo = 0;
            boolean agregarCargoDirectoExitoso = false;
            boolean agregarCargoEspecialExitoso = false;
            nombreBeneficiario = ((this.jTFBeneficiario != null && !"".equals(this.jTFBeneficiario.getText())) ? this.jTFBeneficiario.getText() : "");
            nombreBeneficiario = nombreBeneficiario.trim();
            claveRefaccion = ((this.jTFClaveRefaccion != null && !"".equals(this.jTFClaveRefaccion.getText())) ? this.jTFClaveRefaccion.getText() : "");

            //validar que exista la clave de refaccion en el inventario
            refaccionReq = accesoRefaccion.obtenerRefaccion(claveRefaccion, true, true);
            if (refaccionReq == null) {
                JOptionPane.showMessageDialog(null, "La clave de refacción\nno existe en el inventario.",
                        "Refacción no econtrada!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //validar que nombre del beneficiario sea valido
            if (nombreBeneficiario.equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de\nbeneficiario.",
                        "Nombre de Beneficiario inválido!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Agregar los datos de cargo directo
            cargoDirecto.setCantidad(Double.parseDouble(this.jTFCantidad.getText()));
            cargoDirecto.setFactura(fact);
            cargoDirecto.setIva(0.0);
            //cargoDirecto.setNumeroCargoDirecto();//No se agrega el numero de cargo directo por que se genera automaticamente
            cargoDirecto.setOrdenReparacion(ordenReparacion.
                    obtenerOrdenReparacion(Integer.parseInt(this.jCBOrdenReparacion.
                                    getSelectedItem().toString()), true, true, true));
            cargoDirecto.setPrecioUnitario(Double.parseDouble(this.jTFPrecioUnitario.getText()));
            cargoDirecto.setRefaccion(refaccionReq);
            cargoDirecto.setStatus(true);
            cargoDirecto.setSubtotal(0.0);
            cargoDirecto.setTotal(Double.parseDouble(this.jTFTotal.getText()));
            cargoDirecto.setUsuario(UserHome.getUsuario());
            numeroCargo = accesoCargoDirecto.obtenerUltimoCargoDirecto() + 1;
            cargoDirecto.setNumeroCargoDirecto(numeroCargo);

            //Validar que la cantidad sea válida
            if (cargoDirecto.getCantidad() < 1) {
                JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida",
                        "Cantidad inválida!!!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Agregar los datos de cargo especial
            cargoEspecial = new CargoEspecialDTO(1, nombreBeneficiario, cargoDirecto);

            agregarCargoDirectoExitoso = accesoCargoDirecto.agregarCargoDirecto(cargoDirecto);
            agregarCargoEspecialExitoso = accesoCargoEspecial.agregarCargoEspecial(cargoEspecial);

            if (!agregarCargoEspecialExitoso && agregarCargoDirectoExitoso) {
                boolean reparacionExitosa = false;
                reparacionExitosa = accesoCargoDirecto.repararErrorClasificacionCargoDirecto(cargoDirecto, accesoCargoDirecto.obtenerUltimoCargoDirecto() + 1);
                if (reparacionExitosa) {
                    JOptionPane.showMessageDialog(null, "Código error: 727\n" + "No se pudo reparar la tabla",
                            "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(cargoDirecto.toString(), 727, UserHome.getUsuario(), new Exception("#NA"));
                }
            }

            if (!agregarCargoEspecialExitoso) {
                boolean reparacionExitosa = false;
                reparacionExitosa = accesoCargoEspecial.repararErrorAgregarCargoEspecial();
                if (!reparacionExitosa) {
                    JOptionPane.showMessageDialog(null, "Código error: 1342\n" + "No se pudo reparar la tabla",
                            "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(cargoEspecial.toString(), 1342, UserHome.getUsuario(), new Exception("#NA"));
                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                            "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            this.controlFacturas.actualizarTablas();
            this.limpiar();
        } catch (SQLException ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 728\n" + ex.getMessage(),
                        "Error al guardar datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(cargoDirecto.toString() + cargoEspecial.toString(), 728, UserHome.getUsuario(), ex);
                boolean reparacionExitosa = false;
                CargoEspecialDAO accesoCargoEspecial = new CargoEspecialDAO();
                reparacionExitosa = accesoCargoEspecial.repararErrorAgregarCargoEspecial();
                if (!reparacionExitosa) {
                    JOptionPane.showMessageDialog(null, "Código error: 729\n" + "No se pudo reparar la tabla",
                            "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(cargoDirecto.toString() + cargoEspecial.toString(), 729, UserHome.getUsuario(), ex);
                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                            "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(CargoEspecial.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            try {
                JOptionPane.showMessageDialog(null, "Código error: 730\n" + ex.getMessage(),
                        "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(cargoDirecto.toString() + cargoEspecial.toString(), 730, UserHome.getUsuario(), ex);
                boolean reparacionExitosa = false;
                CargoEspecialDAO accesoCargoEspecial = new CargoEspecialDAO();
                reparacionExitosa = accesoCargoEspecial.repararErrorAgregarCargoEspecial();
                if (!reparacionExitosa) {
                    JOptionPane.showMessageDialog(null, "Código error: 731\n" + "No se pudo reparar la tabla",
                            "Error en acceso a datos!!!\nError al reparar la tabla.", JOptionPane.ERROR_MESSAGE);
                    ErrorLogger.scribirLog(cargoDirecto.toString() + cargoEspecial.toString(), 731, UserHome.getUsuario(), ex);
                } else {
                    JOptionPane.showMessageDialog(null, "Reparación exitosa!!!",
                            "La tabla se reparó correctamente.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex1) {
                //Logger.getLogger(CargoEspecial.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    public void limpiar() {
        try {
            this.limpiarCampos();
            this.jCBOrdenReparacion.removeAllItems();
            this.obtenerOrdenesReparacion();
            this.jCBOrdenReparacion.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 732\n" + ex.getMessage(),
                    "Error al limpiar los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial limpiar()", 732, UserHome.getUsuario(), ex);
        }
    }

    private void limpiarCampos() {
        try {
            this.jTFBeneficiario.setText(null);
            this.jTFCantidad.setText("0.000");
            this.jTFClaveRefaccion.setText(null);
            this.jTFPrecioUnitario.setText("0.000");
            this.jTFTotal.setText("0.000");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 733\n" + ex.getMessage(),
                    "Error al limpiar los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial limpiarCampos()", 733, UserHome.getUsuario(), ex);
        }
    }

    public void obtenerOrdenesReparacion() {
        String mensajeError = "";
        try {
            List<OrdenReparacionDTO> ordenesReparacion = new OrdenReparacionDAO().
                    obtenerOrdenesReparacionPendientes(true);
            for (OrdenReparacionDTO ordenReparacion : ordenesReparacion) {
                mensajeError = ordenReparacion.toString();
                this.jCBOrdenReparacion.addItem(ordenReparacion.getNumeroOrden());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 734\n" + ex.getMessage(),
                    "Error al obtener datos de la BD!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 734, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 735\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(mensajeError, 735, UserHome.getUsuario(), ex);
        }
    }

    private void onFormatErrorSetValue(javax.swing.JTextField campo, double value) {
        DecimalFormat formatD = new DecimalFormat("0.000");
        try {
            double n = Double.parseDouble(campo.getText());
            campo.setText(formatD.format(n));
        } catch (Exception ex) {
            campo.setText(formatD.format(value));
        }
    }

    private void onFormatErrorSetValue(javax.swing.JTextField campo, int value) {
        try {
            int n = (int) (Double.parseDouble(campo.getText()));
            campo.setText(Integer.toString(n));
        } catch (Exception ex) {
            campo.setText(Integer.toString(value));
        }
    }

    private void prepararCaptura(javax.swing.JTextField campo) {
        try {
            this.valorOriginal = ((campo != null && !"".equals(campo.getText())) ? campo.getText() : "");
            campo.setText(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 736\n" + ex.getMessage(),
                    "Error al obtener los datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("CargoEspecial prepararCaptura()", 736, UserHome.getUsuario(), ex);
        }
    }

    private void buscar() {
        BuscarCampoSimple buscarCampo = new BuscarCampoSimple();
        buscarCampo.setTipoRespuesta(1);
        buscarCampo.setRecibirTexto(this.jTFClaveRefaccion);
        buscarCampo.setLocationRelativeTo(null);
        buscarCampo.setVisible(true);
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
            java.util.logging.Logger.getLogger(CargoEspecial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargoEspecial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargoEspecial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargoEspecial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargoEspecial().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregarCargo;
    private javax.swing.JComboBox jCBOrdenReparacion;
    private javax.swing.JLabel jLBeneficiario;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLClaveRefaccion;
    private javax.swing.JLabel jLFolio;
    private javax.swing.JLabel jLLogo;
    private javax.swing.JLabel jLNumeroCargoEspecial;
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
    private javax.swing.JMenuItem jMIBuscarParte;
    private javax.swing.JMenuItem jMILimpiar;
    private javax.swing.JMenuItem jMISalir;
    private javax.swing.JMenuItem jMIVerManual;
    private javax.swing.JTextField jTFBeneficiario;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCargoEspecial;
    private javax.swing.JTextField jTFClaveRefaccion;
    private javax.swing.JTextField jTFFolio;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFProveedor;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTextField jTFUsuario;
    // End of variables declaration//GEN-END:variables

}
