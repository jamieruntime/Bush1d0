/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package tools;

import app.Console;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Jamie
 */
public class FileManager
{

    public static String directory()
    {
        return System.getProperty("user.home") + "\\BUSH1D0\\";
    }
    
    public static String extension(String path)
    {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }
        
    public static ArrayList<String> loadFile(String path)
    {
        ArrayList<String> data = new ArrayList();
        boolean active = true;
        BufferedReader reader;
        String line;
        try
        {
            reader = new BufferedReader(new FileReader(path));
            try
            {
                while(active)
                {
                    line = reader.readLine();
                    if(line != null) {data.add(line);}
                    else {active = false;}
                }
                reader.close();
            }
            catch(IOException ex) {Console.echo(ex);}
        }
        catch(FileNotFoundException ex) {Console.echo(ex);}
        return data;
    }
    
    public static void saveFile(String data, String path)
    {
        saveFile(data, path, true);
    }
    
    public static void saveFile(String data, String path, boolean expand)
    {
        if(expand) {path = directory() + path;}
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(new FileWriter(path, false));
            writer.printf("%s" + "%n", data);
            writer.close();
        }
        catch (IOException ex) {Console.echo(ex);}
    }
    
    public static void saveFile(ArrayList<String> data, String path)
    {
        saveFile(data, path, true);
    }
    
    public static void saveFile(ArrayList<String> data, String path, boolean expand)
    {
        String condense = "";
        for(int x = 0; x < data.size(); x++)
        {
            condense += data.get(x);
            if(x < data.size() - 1) {condense += System.getProperty("line.separator");}
        }
        saveFile(condense, path, expand);
    }
    
    public static String streamPath()
    {
        return directory() + "stream.bush1d0";
    }
    
    public static void streamWrite(String content)
    {
        saveFile(content, streamPath(), false);
    }

}
