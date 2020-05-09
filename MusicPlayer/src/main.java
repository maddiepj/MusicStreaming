import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



public class main {

	//
	//
	//		public static ArrayList<Song> songsList = JsonHelperMethods.readSongsJSON();
	//
	//
	//		public static ArrayList<Song> findSongs(){
	//			ArrayList<Song> results = JsonHelperMethods.readSongsJSON();
	//			int startIndex = findFirstOccurrence(A, x);
	//			int endIndex = 0;
	//	
	//	
	//			return results;
	//		}
	//

	public static int findFirstOccurrence(ArrayList<Song> A, String x)
	{

		int left = 0;
		int right = A.size() - 1;


		int result = -1;

		String midSub = "";

		while (left <= right)
		{
			int mid = (left + right) / 2;	


			try {
				midSub = A.get(mid).getSongName().substring(0,x.length() );
			}
			catch (StringIndexOutOfBoundsException e) {
				midSub =	A.get(mid).getSongName();
			}



			if (x.equals(midSub)) {
				result = mid;
				right = mid - 1;
			}

			else if (x.compareTo(midSub) < 0) {
				right = mid - 1;
			}

			else {
				left = mid + 1;
			}
		}
		return result;
	}






	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}

	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();

	}

	public static void main(String[] args) throws IOException{

		Proxy p = new Proxy();
		User u = null;
		ArrayList<User> users = JsonHelperMethods.readUsersJSON();
		for (User user : users) {
			if (user.getUsername().equals("test")) {u = user; System.out.println("Found user" + u.getUsername()); break;}
		}

		String currentUSersPlaylistsJsoned = (String) p.execute("lookUpSongBySongName", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		System.out.println("Before: " + currentUSersPlaylistsJsoned.length());
		byte[] compressed = compress(currentUSersPlaylistsJsoned);
		System.out.println("After compress: " + compressed.length);
		System.out.println("After decompress: " + decompress(compressed).length());
				ArrayList<Song> songList = JsonHelperMethods.readSongsJSON();
				ArrayList<Song> resuls1 = new ArrayList<Song>();
				ArrayList<Song> resuls2 = new ArrayList<Song>();
		
				MergeSort merge = new MergeSort(songList);
				
				
				merge.sort();
				long startTime = System.nanoTime();
				int res = findFirstOccurrence(songList, "Zap");
				
				Song s;
				while(res < songList.size()) {
					s = songList.get(res++);
					if (s.getSongName().startsWith("Zap")) resuls1.add(s);
					else break;
				}
				
				
				long endTime = System.nanoTime();
				System.out.println("Total execution time: " + (endTime - startTime));
		
				
				startTime = System.nanoTime();
				for(Song ss : songList)
					if (ss.getSongName().startsWith("Zap")) resuls2.add(ss);
				
				endTime = System.nanoTime();
				System.out.println("Total execution time: " + (endTime - startTime));
	}

}