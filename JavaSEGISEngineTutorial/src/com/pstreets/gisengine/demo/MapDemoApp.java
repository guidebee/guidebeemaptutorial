/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pstreets.gisengine.demo;


import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;


class SimpleAuthenticator
        extends Authenticator {

    private String username,
            password;

    public SimpleAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
                username, password.toCharArray());
    }
}
/**
 *
 * @author jshen
 */
public class MapDemoApp extends javax.swing.JFrame{

    private void setDefaultProxy(){
        String  proxy = "proxy",
                port = "8080",
                username = "user name",
                password = "password";
        Authenticator.setDefault(new SimpleAuthenticator(
                username, password));

        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", proxy);
        systemProperties.setProperty("http.proxyPort", port);
    }

    public MapDemoApp(){
        
    }

}
