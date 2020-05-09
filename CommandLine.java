import java.rmi.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.nio.file.*;

@SuppressWarnings("unused")
public class CommandLine
{
    DFS dfs;
    
    public CommandLine(int p, int portToJoin) throws Exception
    {
        dfs = new DFS(p);
        
        if (portToJoin > 0)
        {
            System.out.println("Joining to port: " + portToJoin);
            dfs.join("127.0.0.1", portToJoin);
        }
        
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        
        printCommands();
        String line = buffer.readLine();
        
        while (!line.equals("quit"))
        {
        	try
        	{	
	            String[] result = line.toLowerCase().split("\\s");
	            if (result[0].equals("join")  && result.length > 1)
	            {
	                dfs.join("127.0.0.1", Integer.parseInt(result[1]));
	            }
	            else if (result[0].equals("print"))
	            {
	                dfs.print();
	            }
	            else if (result[0].equals("touch"))
	            {
	            	dfs.touch(result[1]);
	                System.out.println("Created file " + result[1]);
	            }
	            else if (result[0].equals("list") || result[0].equals("ls"))
	            {
	                System.out.println("Files: ");
	                dfs.list();
	            }
	            else if (result[0].equals("append"))
	            {
	            	RemoteInputFileStream input = new RemoteInputFileStream(result[2]);
	                dfs.append(result[1], input);
	                System.out.println("page added");
	            }
	            else if (result[0].equals("delete"))
	            {
	              dfs.delete(result[1]);
	              System.out.println("Deleted "+result[1]+" from the page");
	            }
	            else if (result[0].equals("read"))
	            {
	                int pageNumber = Integer.parseInt(result[2]);
	                int i;
	                RemoteInputFileStream r = dfs.read(result[1], pageNumber);
	                r.connect();
	                while((i = r.read()) != -1){
	                    System.out.print((char) i);
	                }
	                System.out.println();
	                System.out.println("page read");
	
	            }
	            else if (result[0].equals("head"))
	            {
	            	RemoteInputFileStream head = dfs.read(result[1], 1);
	            	head.connect();
	            	int i;
	            	while((i = head.read()) != -1) {
	            		System.out.print((char)i);
	            	}
	            	System.out.println();
	            	System.out.println("read head");
	            }

	            else if (result[0].equals("tail"))
	            {
	            	RemoteInputFileStream tail = dfs.tail(result[1]);
	            	tail.connect();
	            	int i;
	            	while((i = tail.read()) != -1) {
	            		System.out.print((char)i);
	            	}
	            	System.out.println();
	            	System.out.println("read tail");
	            }
	            else if (result[0].equals("leave"))
	            {
	                dfs.leave();
	            }
	            else if (result[0].equals("move") || result[0].equals("mv"))
	            {
	            	dfs.mv(result[1], result[2]);
	            }
	            
	            line=buffer.readLine();
        	}
        	catch(ArrayIndexOutOfBoundsException e)
        	{
        		System.out.println("Invalid command");
        		line=buffer.readLine();
        	}
            
        	printCommands();
            // join, ls, touch, delete, read, tail, head, append, move
        }
        System.exit(0);
    }
    
    private void printCommands()
    {
    	System.out.println("Please enter one of the following commands:");
    	System.out.println("\t- Print");
    	System.out.println("\t- Join");
    	System.out.println("\t- Touch");
    	System.out.println("\t- List");
    	System.out.println("\t- Append");
    	System.out.println("\t- Delete");
    	System.out.println("\t- Read");
    	System.out.println("\t- Write");
    	System.out.println("\t- Head");
    	System.out.println("\t- Tail");
    	System.out.println("\t- Leave");
    	System.out.println("\t- Move");
	}

	static public void main(String args[]) throws Exception
    {
       if (args.length < 1 )
           throw new IllegalArgumentException("Parameter: <port>");
       
       if (args.length > 1 )
       {
    	   CommandLine commandLine=new CommandLine(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       }
       else
       {
    	   CommandLine commandLine=new CommandLine( Integer.parseInt(args[0]), 0);     
       }
    }
}
