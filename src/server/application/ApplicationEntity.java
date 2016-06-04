/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package server.application;

import java.util.ArrayList;

/**
 *
 * @author Jamie
 */
public class ApplicationEntity
{
    private final String appRoot;
    private String appName = null;
    private ArrayList<Object> appStore = new ArrayList();
    
    public ApplicationEntity(String root)
    {
        // Root
        this.appRoot = root;
        
        // Data
        this.init();
    }
    
    private void init()
    {
        // Name
        this.appName = "Temp";
        
        // Data
        this.appStore = new ArrayList();
    }

}