import java.rmi.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.*;
import java.math.BigInteger;
import java.security.*;
import java.time.LocalDateTime;


/* JSON Format

 {
    "metadata" :
    {
        file :
        {
            name  : "File1"
            numberOfPages : "3"
            pageSize : "1024"
            size : "2291"
            page :
            {
                number : "1"
                guid   : "22412"
                size   : "1024"
            }
            page :
            {
                number : "2"
                guid   : "46312"
                size   : "1024"
            }
            page :
            {
                number : "3"
                guid   : "93719"
                size   : "243"
            }
        }
    }
}


 */


@SuppressWarnings("unused")
public class DFS
{
    int port;
    Chord  chord;
    JFiles filesJson;

    private long md5(String objectName)
    {
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(objectName.getBytes());
            BigInteger bigInt = new BigInteger(1,m.digest());
            return Math.abs(bigInt.longValue());
        }
        catch(NoSuchAlgorithmException e)
        {
                e.printStackTrace();
        }
        return 0;
    }

    public DFS(int port) throws Exception
    {
        this.port = port;
        long guid = md5("" + port);
        chord = new Chord(port, guid);
        Files.createDirectories(Paths.get(guid+"/repository"));
    }

    public  void join(String Ip, int port) throws Exception
    {
        chord.joinRing(Ip, port);
        chord.print();
    }

    public String ls() throws Exception
    {
        String listOfFiles = "";
        return listOfFiles;
    }

    public void touch(String fileName) throws Exception
    {
    	filesJson = readMetaData();
		JFile fileJson = new JFile();
		fileJson.setName(fileName);
		filesJson.addFile(fileJson);
		writeMetaData(filesJson);
    }
    public void delete(String fileName) throws Exception
    {
		filesJson = readMetaData();
		for (int i = 0; i < filesJson.getSize(); i++) {

			if (filesJson.getFileJson(i).getName().equalsIgnoreCase(fileName)) {

				long guidPort = md5("" + port);

				ArrayList<JPages> pagesList = filesJson.getFileJson(i).getPages();

				for (int k = 0; k < pagesList.size(); k++)
				{
					JPages pagesRead = pagesList.get(k);
					long pageGuid = pagesRead.getGuid();


					String userDir = System.getProperty("user.dir");
					File file = new File(userDir + "/" + guidPort + "/repository/" + pageGuid);
					file.delete();

					ChordMessageInterface peer = chord.locateSuccessor(pageGuid);
					peer.delete(pageGuid);
				}
				
				filesJson.file.remove(filesJson.getFileJson(i));
				writeMetaData(filesJson);
			}
		}
    }

    public RemoteInputFileStream read(String fileName, int pageNumber) throws Exception
    {
		filesJson = readMetaData();
		pageNumber--;
		RemoteInputFileStream rifs = null;
		for (int i = 0; i < filesJson.getSize(); i++) {
			if (filesJson.getFileJson(i).getName().equalsIgnoreCase(fileName)) {
				ArrayList<JPages> pagesList = filesJson.getFileJson(i).getPages();
				for (int k = 0; k < pagesList.size(); k++) {
					if (k == pageNumber) {
						JPages pageToRead = pagesList.get(k);
						String timeOfRead = LocalDateTime.now().toString();
						pageToRead.setReadTS(timeOfRead);
						filesJson.getFileJson(i).setReadTS(timeOfRead);
						Long pageGUID = md5(fileName + pageToRead.getCreationTS());
						ChordMessageInterface peer = chord.locateSuccessor(pageGUID);
						rifs = peer.get(pageGUID);
					}
				}
				writeMetaData(filesJson);
			}
		}
		return rifs;
    }

    public RemoteInputFileStream tail(String fileName) throws Exception
    {
		filesJson = readMetaData();
		RemoteInputFileStream tail = null;
		for (int i = 0; i < filesJson.getSize(); i++) {
			if (filesJson.getFileJson(i).getName().equalsIgnoreCase(fileName)) {
				ArrayList<JPages> pagesList = filesJson.getFileJson(i).getPages();
				int last = pagesList.size() - 1;
				JPages pageToRead = pagesList.get(last);
				String timeOfRead = LocalDateTime.now().toString();
				pageToRead.setReadTS(timeOfRead);
				filesJson.getFileJson(i).setReadTS(timeOfRead);
				Long pageGUID = md5(fileName + pageToRead.getCreationTS());
				ChordMessageInterface peer = chord.locateSuccessor(pageGUID);
				tail = peer.get(pageGUID);
				writeMetaData(filesJson);
			}
		}
		return tail;
    }

    public void print() throws Exception
    {
		chord.print();
	}

	public JFiles readMetaData() throws Exception {
		JFiles filesJson = null;
		try {
			Gson gson = new Gson();
			long guid = md5("Metadata");
			ChordMessageInterface peer = chord.locateSuccessor(guid);
			RemoteInputFileStream metadataraw = peer.get(guid);
			metadataraw.connect();
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(metadataraw);
			scan.useDelimiter("\\A");
			String strMetaData = scan.next();
			filesJson = gson.fromJson(strMetaData, JFiles.class);
		} catch (NoSuchElementException ex) {
			filesJson = new JFiles();
		}
		return filesJson;
	}

	public void writeMetaData(JFiles filesJson) throws Exception {

		long guid = md5("Metadata");
		ChordMessageInterface peer = chord.locateSuccessor(guid);

		Gson gson = new Gson();
		peer.put(guid, gson.toJson(filesJson));
	}

	public String list() throws Exception {
		filesJson = readMetaData();
		String listOfFiles = "";
		for (int i = 0; i < filesJson.getSize(); i++)
		{
			String filename = filesJson.getFileJson(i).getName();
			listOfFiles = listOfFiles + " " + filename + "\n";
		}
		System.out.println(listOfFiles);
		return listOfFiles;
	}

	public void append(String filename, RemoteInputFileStream data) throws Exception {
		filesJson = readMetaData();
		for (int i = 0; i < filesJson.getSize(); i++) {
			if (filesJson.getFileJson(i).getName().equalsIgnoreCase(filename)) {

				Long sizeOfFile = new Long(data.available());
				String timeOfAppend = LocalDateTime.now().toString();
				filesJson.getFileJson(i).setWriteTS(timeOfAppend);
				filesJson.getFileJson(i).addNumOfPages(1);
				filesJson.getFileJson(i).addSize(sizeOfFile);

				String objectName = filename + timeOfAppend;
				Long guid = md5(objectName);

				ChordMessageInterface peer = chord.locateSuccessor(guid);
				peer.put(guid, data);

				@SuppressWarnings("unused")
				Long defaultZero = new Long(0);
				filesJson.getFileJson(i).addPageInfo(guid, sizeOfFile, timeOfAppend, "0", "0", 0);
                // break;
			}
		}
		writeMetaData(filesJson);
	}

	public void leave() throws Exception {
		chord.leave();
	}

	public void mv(String oldName, String newName) throws Exception {
		filesJson = readMetaData();
		boolean found = false;
		for (int i = 0; i < filesJson.getSize(); i++) {
			if (filesJson.getFileJson(i).getName().equalsIgnoreCase(oldName)) {
				filesJson.getFileJson(i).setName(newName);
				String timeOfWrite = LocalDateTime.now().toString();
				filesJson.getFileJson(i).setWriteTS(timeOfWrite);
				writeMetaData(filesJson);
				found = true;
			}
		}
		if (found)
			System.out.println("Renamed " + oldName + " to " + newName);
		else
			System.out.println(oldName + " does not exist");
	}
}
