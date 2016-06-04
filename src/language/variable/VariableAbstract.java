/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language.variable;

import language.value.ValueAbstract;

/**
 *
 * @author Jamie
 */
public abstract class VariableAbstract
{
    private final String name;
    private ValueAbstract value;
    
    public VariableAbstract(String name, ValueAbstract value)
    {
        this.name = name;
        this.value = value;
    }

}