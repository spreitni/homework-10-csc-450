import java.io.PrintStream;
import java.util.ArrayList;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.*;
public class CORE 
{
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();
    private static byte[] file;
    public static synchronized void addClientThreadPrintStream(PrintStream ps)
    {
        System.out.println("adding client thread");
        CORE.theClientStreams.add(ps);
    }

    public static synchronized void removeClientThreadPrintStream(PrintStream ps)
    {
        CORE.theClientStreams.remove(ps);
    }
    
    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {
            ps.println(message);
        }
    }
    public static void uploadFile(String userFile)
    {
            CORE.broadcastMessage("uploading file ...");
            try{
                FileInputStream fileInput = new FileInputStream(userFile);
                fileInput.read(file);
                CORE.broadcastMessage("uploaded file");
                CORE.broadcastMessage(fileInput);
                
            }
            catch(Exception e)
            {
               System.err.println("no file upload :(");
            }
          
            //
            //
            //     do something with the file and send it
            //
            //
    }
    
}

