/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package server;

import app.Console;
import app.ConsoleStyle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import server.application.ApplicationEntity;
import tools.FileManager;

/**
 *
 * @author Jamie
 */
public class Server extends Thread
{
    // Debug
    private static boolean OUTPUT_SIMPLE = true;
    private static boolean OUTPUT_ADVANCED = false;

    // Server
    private static ServerConfig SERVER_CONFIG;
    private static ArrayList<ApplicationEntity> SERVER_APPLICATION;
    private static ServerSocket SERVER_SOCKET;
    
    // Request Data
    private static HashMap<String, String> REQUEST_GET = new HashMap();
    
    //

    // to be removed
    static final String HTML_START =
    "<html>" +
    "<title>HTTP Server in java</title>" +
    "<body>";

    static final String HTML_END =
    "</body>" +
    "</html>";

    // Components
    Socket connectedClient = null;
    BufferedReader inFromClient = null;
    DataOutputStream outToClient = null;

    public Server(Socket client)
    {
        connectedClient = client;
    }
    
    public static void construct(int port) throws Exception
    {
        // Server
        SERVER_CONFIG = new ServerConfig(FileManager.directory() + "config\\config");
        SERVER_APPLICATION = new ArrayList();
        SERVER_SOCKET = socketConstruct(port);
        
        // Run
        socketRun();
    }

    public void run()
    {
        Console.echo("---------------------------------------");
        try
        {
            // Debug
            if(OUTPUT_ADVANCED) {Console.echo( "The Client " + connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");}
            
            // Client Input/Output
            inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));
            outToClient = new DataOutputStream(connectedClient.getOutputStream());

            // Client Data
            String requestString = inFromClient.readLine();
            String headerLine = requestString;
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();
            
            // Debug
            if(OUTPUT_ADVANCED)
            {
                Console.echo("<b> This is the HTTP Server Home Page.... </b><BR>");
                Console.echo("The HTTP Client request is ....<BR>");
                Console.echo("The HTTP request string is ....");
            }
            
            // Read HTTP Query
            while(inFromClient.ready())
            {
                // Debug
                if(OUTPUT_ADVANCED)
                {
                    Console.echo(requestString + "<BR>");
                    Console.echo(requestString);
                }
                requestString = inFromClient.readLine();
            }
            
            // Debug
            if(OUTPUT_ADVANCED) {Console.echo("HTTP QUERY STRING = " + httpQueryString);}
            
            // Create GET variables
            REQUEST_GET = new HashMap();
            if(httpQueryString.contains("?"))
            {
                String[] httpGetString = httpQueryString.split("\\?")[1].split("\\&");
                if(httpGetString.length > 0)
                {
                    String getKey, getValue;
                    for(int get = 0; get < httpGetString.length; get ++)
                    {
                        getKey = httpGetString[get];
                        getValue = "";
                        if(httpGetString[get].contains("="))
                        {
                            getKey = httpGetString[get].split("\\=")[0];
                            getValue = httpGetString[get].split("\\=")[1];
                        }
                        REQUEST_GET.put(getKey, getValue);
                    }
                }
                httpQueryString = httpQueryString.split("\\?")[0];
            }
            
            // Process GET
            if(httpMethod.equals("GET"))
            {
                if(httpQueryString.equals("/"))
                {
                    // The default home page
                    //sendResponse(200, responseBuffer.toString(), false);
                    sendResponse(200, FileManager.directory() + "admin/index.bush1d0", true, requestString);
                }
                else
                {
                    // File Name
                    String fileName = httpQueryString.replaceFirst("/", "");
                    fileName = URLDecoder.decode(fileName);
                    String fileApp = fileName; // ??
                    String filePath = "";
                    boolean fileProvide = false;
                    String fileDest = FileManager.directory() + "root/" + fileApp + "/index.bush1d0";
                    
                    // Split
                    if(fileName.contains("/"))
                    {
                        fileApp = fileName.split("\\/")[0];
                        fileProvide = true;
                        filePath = fileName.substring(fileName.indexOf("/")).replaceFirst("/", "");
                        fileDest = FileManager.directory() + "root/" + fileApp + "/" + filePath;
                    }

                    // Debug
                    if(OUTPUT_SIMPLE)
                    {
                        Console.echo("FILE STRING = " + fileName);
                        Console.echo("FILE APP = " + fileApp);
                        Console.echo("FILE PATH = " + filePath);
                        Console.echo("FILE DEST = " + fileDest);
                    }

                    // File Exists?
                    if(new File(fileDest).isFile()) {sendResponse(200, fileDest, true, requestString, fileDest);}
                    else {sendResponse(404, "<b>The Requested resource not found...</b>", false, requestString);}
                }
            }
            
            // ??
            else {sendResponse(404, "<b>The Requested resource not found...</b>", false, requestString);}
        }
        
        // Error Handling
        catch(Exception ex) {Console.echo(ex, ConsoleStyle.ERROR);}
    }
    
    public void sendResponse(int statusCode, String responseString, boolean isFile, String requestString) throws Exception
    {
        sendResponse(statusCode, responseString, isFile, requestString, "");
    }

    public void sendResponse(int statusCode, String responseString, boolean isFile, String requestString, String fileDest) throws Exception
    {
        // Debug Info
        long debugTime = System.nanoTime();
        
        //
        String statusLine = null;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine = null;
        String fileName = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        FileInputStream fin = null;
        //
        if(statusCode == 200) {statusLine = "HTTP/1.1 200 OK" + "\r\n";}
        else {statusLine = "HTTP/1.1 404 Not Found" + "\r\n";}
        //
        if(isFile)
        {
            // Response Data
            boolean responsePage = true;
            final String responseType = FileManager.extension(responseString);
            
            // File: Bush1d0
            if(responseType.equals("bush1d0"))
            {
                Console.echo(" - compiling", ConsoleStyle.CLIENT);
                responseString = language.Compiler.compile(FileManager.loadFile(responseString));
            }
            
            // File: Image
            if(responseType.equals("png"))
            {
                // Breakpoint
                Console.breakpoint("IMAGE START");

                responsePage = false;
                Console.echo(" - image", ConsoleStyle.CLIENT);
                
                //BufferedImage image = ImageIO.read(new File(fileDest));
                BufferedImage image = ImageIO.read(new File("C:\\Users\\Jamie\\BUSH1D0\\root\\test\\resources\\images\\logo2a.jpg"));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                responseString = byteArrayOutputStream.toString();
                
                // Breakpoint
                Console.breakpoint("IMAGE DONE");
                
                /*outToClient.writeBytes(statusLine);
                outToClient.writeBytes(serverdetails);
                outToClient.writeBytes(contentTypeLine);
                outToClient.writeBytes(contentLengthLine);
                outToClient.writeBytes("Connection: close\r\n");
                outToClient.writeBytes("\r\n");*/
                //
                /*outToClient.write(byteArrayOutputStream.toByteArray());
                //
                outToClient.close();
                //
                return;*/
            }
            
            // Breakpoint
            Console.breakpoint();
        
            // Debug Info
            debugTime = System.nanoTime() - debugTime;
            /*if(responsePage && SERVER_CONFIG.settingGetDebug() == true)
            {
                // Temp
                String debug = "<br><br><br>";
                debug += "<div>";
                debug += "<table>";
                debug += "<tr>";
                debug += "<td colspan = '2'><h2>Debug Info</h2></td>";
                debug += "</tr>";
                debug += "<tr>";
                debug += "<td>Request</td>";
                debug += "<td>" + requestString + "</td>";
                debug += "</tr>";
                debug += "<tr>";
                debug += "<td>URL Variables</td>";
                debug += "<td>";
                // TBD: fix this (find a better way of iterating through and echoing the keys and values)
                Object[] debugInfoGetValues = REQUEST_GET.values().toArray();
                if(debugInfoGetValues.length > 0)
                {
                    for(int debugInfoGet = 0; debugInfoGet < debugInfoGetValues.length; debugInfoGet ++)
                    {
                        debug += debugInfoGetValues[debugInfoGet].toString();
                    }
                }
                else {debug += "-";}
                debug += "</td>";
                debug += "</tr>";
                debug += "<tr>";
                debug += "<td>Execution Time</td>";
                debug += "<td>" + debugTime + "</td>";
                debug += "</tr>";
                debug += "</table>";
                debug += "</div>";

                // Append Debug Info
                responseString += debug;
            }*/
            
            // Stream Write
            FileManager.streamWrite(responseString);
            
            // Stream Read
            fin = new FileInputStream(FileManager.streamPath());
            
            // Breakpoint
            Console.breakpoint();
            
            // Content Data
            contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
            
            // Breakpoint
            Console.breakpoint();

            //if(!fileName.endsWith(".htm") && !fileName.endsWith(".html")) {contentTypeLine = "Content-Type: \r\n";}
            
            // Breakpoint
            Console.breakpoint();
        }
        
        //
        else
        {
            responseString = Server.HTML_START + responseString + Server.HTML_END;
            contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
        }

        // Breakpoint
        Console.breakpoint("BYTE START");
        
        //
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");
        //
        if(isFile) {sendFile(fin, outToClient);}
        else {outToClient.writeBytes(responseString);}
        //
        outToClient.close();

        // Breakpoint
        Console.breakpoint("BYTE DONE");
    }

    public void sendFile(FileInputStream fin, DataOutputStream out) throws Exception
    {
        byte[] buffer = new byte[1024];
        int bytesRead;
        //
        while((bytesRead = fin.read(buffer)) != -1) {out.write(buffer, 0, bytesRead);}
        fin.close();
    }
    
    private static ServerSocket socketConstruct(int port)
    {
        try
        {
            ServerSocket server = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
            Console.echo("Bush1d0 Server Constructed^ - listening on port " + port, ConsoleStyle.SERVER);
            return server;
        }
        catch (UnknownHostException ex) {Console.echo(ex, ConsoleStyle.ERROR);}
        catch (IOException ex) {Console.echo(ex, ConsoleStyle.ERROR);}
        return null;
    }
    
    private static void socketRun()
    {
        while(true)
        {
            try
            {
                Socket connected = SERVER_SOCKET.accept();
                (new Server(connected)).start();
            }
            catch (IOException ex) {Console.echo(ex, ConsoleStyle.ERROR);}
        }
    }

}