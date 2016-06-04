/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language.script;

import language.value.ValueAbstract;
import language.variable.VariableAbstract;

/**
 *
 * @author Jamie
 */
public class ScriptAssignment extends ScriptAbstract
{
    private final VariableAbstract variable;
    private final ValueAbstract value;
    
    public ScriptAssignment(String script, String file, int line, VariableAbstract variable, ValueAbstract value)
    {
        super(script, file, line);
        this.variable = variable;
        this.value = value;
    }
    
    public void execute()
    {
        //
    }

}