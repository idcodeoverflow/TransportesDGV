/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class LogInDTO {
 
    private String user;
    private String password;
    private boolean validate;
    
    /**
     * Default constructor.
     */
    public LogInDTO(){}

    public LogInDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    
    @Override
    public String toString(){
        try {
            return "{LogInDTO}[(user:" + nullCheck(this.user) + "),(password:" + 
                    ((this.password != null && !"".equals(this.password)) ? "*" : "null") + 
                    "),(validate:" + this.validate + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
    
}
