import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class Proxy
{
	JsonArray jsonFileContent;
	ClientCM cm;
	Gson gson;
	
	
	private void ReadCatalog() throws IOException {
		File file = new File("catalog.json");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		String temp = "";
		while ((st = br.readLine()) != null) {
			temp += st;
		}
		br.close();
		this.jsonFileContent = JsonParser.parseString(temp).getAsJsonArray();		
	}
	
	public Proxy() throws IOException
	{
//		TODO: Fix reading catalog every time, read it only once
		this.ReadCatalog();
		String addr = InetAddress.getLocalHost().getHostAddress().trim();
//		addr = "172.113.110.190";
		int port = 8082;
		this.cm = new ClientCM(addr, port);
		this.gson = new Gson();
	}


	public Object execute(String methodName, Object ...args)
	{
		JsonObject jsonObj = null;
		for (JsonElement j: this.jsonFileContent)
		{
			if (j.getAsJsonObject().get("remoteMethod").getAsString().equals(methodName))
			{
				
				jsonObj = j.getAsJsonObject();
				break;
			}
		}

		int i = 0;
		
		Entry<String, JsonElement> temp = null;
		
		
		for (Map.Entry<String, JsonElement>  entry  :  jsonObj.get("param").getAsJsonObject().entrySet())
		{
			
			switch (entry.getValue().getAsString().toLowerCase())
			{
			case "long":
				jsonObj.get("param").getAsJsonObject().addProperty(entry.getKey(), Long.parseLong(args[i++].toString()));
				break;
			case "integer":
				jsonObj.get("param").getAsJsonObject().addProperty(entry.getKey(), Integer.parseInt(args[i++].toString()));
				break;
			case "int":
				jsonObj.get("param").getAsJsonObject().addProperty(entry.getKey(), Integer.parseInt(args[i++].toString()));
				break;
			case "string":
				jsonObj.get("param").getAsJsonObject().addProperty(entry.getKey(), new String(args[i++].toString()));
				break;
			default:
				jsonObj.get("param").getAsJsonObject().addProperty(entry.getKey(), new String(this.gson.toJson(args[i++]).toString()));
				break;
			}
		}

		String ret;
		try {
			System.out.println("Sending: " + jsonObj.toString());
			ret = this.cm.send(jsonObj.toString());
			
			this.ReadCatalog();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return ret;

	}
}

