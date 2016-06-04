/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language;

import java.util.ArrayList;
import language.command.ScriptCommand;
import language.element.ScriptElement;

/**
 *
 * @author Jamie
 */
public class Language
{
    public static ArrayList<ScriptCommand> COMMAND;
    
    public Language()
    {
        COMMAND = new ArrayList();
    }
    
    public static void __init()
    {
        // Commands
        COMMAND = new ArrayList();

        // Native Commands
        ScriptCommand commandEcho = new ScriptCommand("echo");
        commandEcho.argumentAdd("object", ScriptElement.STRING);
        COMMAND.add(commandEcho);
    }
    
    public static ScriptCommand commandGet(String name)
    {
        if(COMMAND.size() > 0)
        {
            for(int c = 0; c < COMMAND.size(); c ++)
            {
                if(name.equals(COMMAND.get(c).getName())) {return COMMAND.get(c);}
            }
        }
        return null;
    }

}