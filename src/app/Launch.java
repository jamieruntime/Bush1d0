/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package app;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import language.Compiler;
import language.Language;
import server.Server;
import server.db.Datasource;
import tools.FileManager;

/**
 *
 * @author Jamie
 */
public class Launch
{

    public static void main(String[] args)
    {
        Console.echo("");
        
        //
        /*BufferedImage image;
        try
        {
            image = ImageIO.read(new File("C:\\Users\\Jamie\\BUSH1D0\\root\\test\\resources\\images\\logo2a.jpg"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try
            {
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                Console.echo(byteArrayOutputStream.toString());
            }
            catch (IOException ex) {Console.echo(ex, ConsoleStyle.ERROR);}
        }
        catch (IOException ex) {Console.echo(ex, ConsoleStyle.ERROR);}*/

        // Run Server
        //testServer();
        
        // Database and Queries
        //testDatabase();
        
        // Compiler
        testCompiler();
        
        // NOTE: variables in various different scopes need to exist in either the application or request (for the most part)
        //  these need to be collections of type OBJECT - not just strings!
    }
    
    private static void testCompiler()
    {
        Language.__init();
        ArrayList<String> script = new ArrayList();
        script.add("<bush1d0>");
        //
        /*script.add("~~ Test ~~");
        script.add("");
        script.add("&String something");
        script.add("");
        script.add("&String name = 'JAMIE'");
        script.add(">echo name");
        script.add("");
        script.add(">echo name2");
        script.add("");
        script.add("&int name2 = 1");
        script.add(">echo name2");
        script.add("");
        script.add(">oogie boogie");
        script.add("");*/
        script.add(">echo[]");
        script.add(">echo['hello']");
        //
        script.add("</bush1d0>");
        Console.echo(language.Compiler.compile(script));
    }
    
    private static void testDatabase()
    {
        Datasource testDatasource = new Datasource("test", "Bush1d0", "JAMIE-HP\\SQLEXPRESS", "Bush1d0Login", "abcdef");
        ArrayList<HashMap<String, String>> test = testDatasource.query("SELECT * FROM account");
        Console.echo(test);
    }
    
    private static void testServer()
    {
        try {Server.construct(7000);}
        catch (Exception ex) {Console.echo(ex);}
    }

}