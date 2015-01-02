/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mailer;

import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class AccountAddress {
    
    private String user;
    private String password;
    
    public AccountAddress(){
        user = "";
        password = "";
    }
    
    public AccountAddress(String user, String password){
        this.user = user;
        this.password = password;
    }
    
    public String getUser(){
        return user;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }    
    
    @Override
    public String toString(){
        return "{AccountAddress}[(user:" + nullCheck(this.user) + "),(password:" + 
                nullCheck(this.password) + ")]";
    }
}
