/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mailer;

import almacendgv.UserHome;
import java.util.Calendar;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class Mailer extends Thread {
    
    private Properties props;
    private Session session;
    private MimeMessage message;
    private Transport transport;
    private MimeMultipart multiparte;
    private AccountAddress address;
    
    public Mailer(AccountAddress address){
        this.address = address;
        
        props = new Properties();
        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");
        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port", "587");
        // Nombre del usuario
        props.setProperty("mail.smtp.user", address.getUser());
        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");
        
        session = Session.getDefaultInstance(props);
        session.setDebug(true);
        message = new MimeMessage(session);
        multiparte = new MimeMultipart();
    }
    
    public boolean send(String subject, String path, String fileName, int tipo){
        Calendar calendar = Calendar.getInstance();
        
        try {
            BodyPart adjunto = new MimeBodyPart();

            adjunto.setDataHandler(new DataHandler(new FileDataSource(path + fileName)));
            adjunto.setFileName(fileName);
            multiparte.addBodyPart(adjunto);
            // Quien envia el correo
            message.setFrom(new InternetAddress(address.getUser()));

            // A quien va dirigido
            for(String mail : new MailData().getMailAddresses(tipo)){
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            }
            
            message.setSubject(subject);
            message.setContent(multiparte);
            transport = session.getTransport("smtp");
            transport.connect(address.getUser(), address.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1366\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("MailData:getMailAddresses()", 1366, UserHome.getUsuario(), ex);
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1367\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:crearNuevoLog()", 1367, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1368\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:crearNuevoLog()", 1368, UserHome.getUsuario(), ex);
        }
        
        return true;
    }
    
    public AccountAddress getAddress(){
        return address;
    }
    
    public void setAddress(AccountAddress address){
        this.address = address;
    }
    
}
