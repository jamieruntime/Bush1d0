/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package language;

import app.Console;
import java.util.ArrayList;
import java.util.HashMap;
import language.command.ScriptCommand;
import language.script.ScriptAbstract;
import server.Server;
import tools.FileManager;

/**
 *
 * @author Jamie
 */
public class Compiler
{
    // Temp
    private final static HashMap<String, Object> VALUES = new HashMap();

    public static String compile(ArrayList<String> content)
    {
        //ArrayList<ScriptAbstract> result = new ArrayList();
        String result = "";
        if(content.size() > 0)
        {
            boolean evaluate = false;
            for(int line = 0; line < content.size(); line ++)
            {
                if(evaluate == false && content.get(line).trim().equalsIgnoreCase("<bush1d0>")) {evaluate = true;}
                else if(evaluate == true && content.get(line).trim().equalsIgnoreCase("</bush1d0>")) {evaluate = false;}
                else
                {
                    if(evaluate == true) {result = result + compileScriptLine(content.get(line).trim(), line);}
                    else {result = result + content.get(line).trim();}
                }
            }
        }
        
        // Temp
        //Console.echo("Result", result.toString());
        //Console.echo("Values", VALUES.toString());        
        
        // Return Data
        return result;
    }
    
    private static void compileException(exception type, String script, int line, String message)
    {
        Console.echo("### " + type.name() + " exception: " + message + " (line " + line + ")", "    " + script);
    }
    
    private static String compileScript(ArrayList<String> script)
    {
        String result = "";
        if(script.size() > 0)
        {
            for(int line = 0; line < script.size(); line ++)
            {
                result = result + compileScriptLine(script.get(line).trim(), line);
            }
        }
        Console.echo("Result", result.toString());
        Console.echo("Values", VALUES.toString());
        return result;
    }
    
    private static String compileScriptLine(String script, int line)
    {
        Console.echo("~~~~~");
        // TBD: need to create new functions for each pattern
        // TBD: these need to recursively work out what resolves to what
        //      (so that functions that return strings can be string arguments of other functions, for example)

        // Blank
        if(script.length() < 1) {return "";}
        
        // Temp
        if(script.startsWith(">"))
        {
            Console.echo("Command processing: " + script);
            if(script.contains("[") && script.contains("]"))
            {
                String tempCommandName = script.substring(1).split("\\[")[0];
                ScriptCommand tempCommandObject = Language.commandGet(tempCommandName);
                if(tempCommandObject != null)
                {
                    Console.echo("Command found: " + tempCommandObject.getName());
                    String tempCommandBody = script.substring(tempCommandName.length() + 1);
                    Console.echo("Command body: " + tempCommandBody);
                    String tempCommandArgs = tempCommandBody.substring(1, tempCommandBody.length() - 1);
                    Console.echo("Command arguments: " + tempCommandArgs);
                }
                else {Console.echo("Command not found: " + tempCommandName);}
            }
            else {Console.echo("Command missing []: " + script);}
        }
        
        // Comment
        if(script.startsWith("~~")) {return "";}
        
        // Assingment
        if(script.startsWith("&"))
        {
            String[] assign = script.substring(1).split(" ");
            if(assign.length < 3 || !assign[2].equals("="))
            {
                compileException(exception.SYNTAX, script, line, "assignment failed");
            }
            else
            {
                if(assign[0].equals("String"))
                {
                    VALUES.put(assign[1], new String(assign[3]).replaceAll("'", ""));
                    // TBD: check for and remove outer quotes of string (there are three types)
                }
                else if(assign[0].equals("int"))
                {
                    VALUES.put(assign[1], Integer.parseInt(assign[3]));
                    // TBD: handle parsing exception
                }
            }
        }
        
        // Command
        /*if(script.startsWith(">"))
        {
            String[] command = script.substring(1).split(" ");
            if(command.length < 2)
            {
                compileException(exception.SYNTAX, script, line, "command failed");
            }
            if(command[0].equals("echo"))
            {
                if(!VALUES.containsKey(command[1]))
                {
                    compileException(exception.RUNTIME, script, line, "variable " + command[1] + " is undefined");
                }
                String echo = "" + VALUES.get(command[1].toString());
                //Console.echo(script, echo);
                return echo;
            }
            else if(command[0].equals("include"))
            {
                final String include = FileManager.directory() + command[1];
                //Console.echo("include " + include);
                return language.Compiler.compile(FileManager.loadFile(include));
            }
            else if(command[0].equals("query"))
            {
                final String queryStatement = command[1];
                // TBD: default datasource and query
            }
            else
            {
                // TBD: decide if we should use the term "command" or "method"
                compileException(exception.RUNTIME, script, line, "command " + command[0] + " is undefined");
            }
        }*/
        
        // Logic
        if(script.startsWith("?"))
        {
            String[] assign = script.substring(1).split(" ");
            if(assign[0].equals("if"))
            {
                //
            }
        }
        
        // Unrecognised?
        return "";
    }

}

enum exception
{
    DATABASE, EXPRESSION, RUNTIME, SYNTAX;
}