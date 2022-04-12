import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.*;
public class ChatWorkerThread extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;
    private String theFile;
    public ChatWorkerThread(Socket theClientSocket)
    {
        try 
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            //System.out.println("About to add a printstream");
            CORE.addClientThreadPrintStream(this.clientOutput);

            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        } 
        catch (Exception e) 
        {
            System.err.println("Bad things happened in thread!!!!!");
            e.printStackTrace();
        }
        
    }

    public void run()
    {
        //this is what the thread does
        this.clientOutput.println("What is your name?");
        String name = clientInput.nextLine();
        CORE.broadcastMessage(name + " has joined!");
        
        String message;
        while(true)
        {
            message = clientInput.nextLine();
            if(message.equals("/quit"))
            {
                CORE.broadcastMessage(name + " has left the server!");
                CORE.removeClientThreadPrintStream(this.clientOutput);
                break;
            }
            if(message.equals("/upload"))
            {
                CORE.broadcastMessage(name + "please select a file to upload");
                theFile = clientInput.nextLine();
                CORE.uploadFile(theFile);
                this.clientOutput.println("file uploaded");

            }
            
        }
    }
}
