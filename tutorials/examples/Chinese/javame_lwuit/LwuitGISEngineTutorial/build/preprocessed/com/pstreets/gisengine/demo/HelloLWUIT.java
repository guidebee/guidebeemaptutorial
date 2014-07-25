/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pstreets.gisengine.demo;

import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

public class HelloLWUIT extends javax.microedition.midlet.MIDlet {

    public void startApp() {
//init the LWUIT Display
        Display.init(this);
// Setting the application theme is discussed
// later in the theme chapter and the resources chapter
        try {
            Resources r = Resources.open("/javaTheme.res");
            UIManager.getInstance().setThemeProps(
                    r.getTheme(r.getThemeResourceNames()[0]));
        } catch (java.io.IOException e) {
        }
        Form f = new Form();
        f.setTitle("Hello World");
        //f.setLayout(new BorderLayout());
        //f.addComponent("Center", new Label("I am a Label"));
        f.addCommand(new Command("Run", 2));
        f.show();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
