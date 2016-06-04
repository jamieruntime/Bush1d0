/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language.script;

/**
 *
 * @author Jamie
 */
public abstract class ScriptAbstract
{
    private final String script, file;
    private final int line;

    public ScriptAbstract(String script, String file, int line)
    {
        this.script = script;
        this.file = file;
        this.line = line;
    }
    
    public abstract void execute();

}