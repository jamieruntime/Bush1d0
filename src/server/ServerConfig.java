/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package server;

import java.util.ArrayList;
import tools.FileManager;

/**
 *
 * @author Jamie
 */
public class ServerConfig
{
    // Config File
    private String configPath;
    
    // Settings
    private boolean settingDebug = false;
    
    public ServerConfig(String path)
    {
        // Config File
        this.configPath = path;
        
        // Settings
        this.configLoad();
    }
    
    private void configLoad()
    {
        // Load file content
        ArrayList<String> content = FileManager.loadFile(this.configPath);
        
        // Debug
        if(content.get(1).equals("true")) {this.settingDebug = true;}
        else {this.settingDebug = false;}
    }
    
    public void configSave()
    {
        // Define content array
        ArrayList<String> content = new ArrayList();
        
        // Header
        content.add("BUSH1D0 SERVER CONFIG");
        
        // Debug
        content.add(Boolean.toString(this.settingDebug));
        
        // Save config file
        FileManager.saveFile(content, this.configPath, false);
    }
    
    public boolean settingGetDebug()
    {
        return this.settingDebug;
    }
    
    public void settingSetDebug(boolean debug)
    {
        this.settingDebug = debug;
    }

}