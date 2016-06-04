/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language.command;

import language.element.ScriptElement;

/**
 *
 * @author Jamie
 */
public class ScriptCommandArgument
{
    private final ScriptCommand command;
    private final String name;
    private final ScriptElement element;
    
    public ScriptCommandArgument(ScriptCommand command, String name, ScriptElement element)
    {
        this.command = command;
        this.name = name;
        this.element = element;
    }

}