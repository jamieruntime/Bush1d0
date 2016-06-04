/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language.command;

import java.util.ArrayList;
import language.element.ScriptElement;

/**
 *
 * @author Jamie
 */
public class ScriptCommand
{
    private final String name;
    private final ArrayList<ScriptCommandArgument> arguments;
    private final ScriptElement returns;
    
    public ScriptCommand(String name)
    {
        this.name = name;
        this.arguments = new ArrayList();
        this.returns = null;
    }
    
    public void argumentAdd(String name, ScriptElement element)
    {
        this.arguments.add(new ScriptCommandArgument(this, name, element));
    }
    
    public String getName()
    {
        return this.name;
    }

}