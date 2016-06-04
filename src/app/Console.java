/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package app;

/**
 *
 * @author Jamie
 */
public class Console
{
    private static int BREAKPOINT = 0;
    
    public static void breakpoint()
    {
        BREAKPOINT ++;
        breakpointWrite("" + BREAKPOINT);
    }
    
    public static void breakpoint(String label)
    {
        BREAKPOINT ++;
        breakpointWrite(BREAKPOINT + " " + label);
    }
    
    private static void breakpointWrite(String content)
    {
        echo("    < breakpoint " + content + " >", ConsoleStyle.BREAKPOINT);
    }

    //

    public static void echo(String contentString)
    {
        echo(contentString, ConsoleStyle.STANDARD);
    }
    
    public static void echo(String contentString, ConsoleStyle contentStyle)
    {
        String[] contentArray = contentString.split("\\^");
        for(int line = 0; line < contentArray.length; line ++) {echoWrite(contentArray[line], contentStyle);}
        echoWrite();
    }
    
    public static void echo(Object content)
    {
        echo(content, ConsoleStyle.STANDARD);
    }
    
    public static void echo(Object content, ConsoleStyle contentStyle)
    {
        echoWrite(content.toString(), contentStyle);
        echoWrite();
    }
    
    public static void echo(Object content1, Object content2)
    {
        echo(content1, content2, ConsoleStyle.STANDARD);
    }
    
    public static void echo(Object content1, Object content2, ConsoleStyle contentStyle)
    {
        echoWrite(content1.toString(), contentStyle);
        echoWrite(content2.toString(), contentStyle);
        echoWrite();
    }
    
    //
    
    private static String echoStyle(ConsoleStyle style)
    {
        // http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
        if(style == ConsoleStyle.BREAKPOINT) {return "\u001B[37m";}
        if(style == ConsoleStyle.CLIENT) {return "\u001B[34m";}
        if(style == ConsoleStyle.ERROR) {return "\u001B[31m";}
        if(style == ConsoleStyle.SERVER) {return "\u001B[35m";}
        if(style == ConsoleStyle.STANDARD) {return "\u001B[30m";}
        return "\u001B[30m";
    }
    
    private static String echoStyleEnd()
    {
        return "\u001B[0m";
    }
    
    //
    
    private static void echoWrite()
    {
        System.out.println("");
    }
    
    private static void echoWrite(String contentString, ConsoleStyle contentStyle)
    {
        System.out.println(echoStyle(contentStyle) + "    " + contentString + echoStyleEnd());
    }

}