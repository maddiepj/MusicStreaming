import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHelperMethods {

	/**
	 * Read's in all songs from the json file and adds them to an array list.
	 * @return
	 */
	static ArrayList<Song> readSongsJSON() {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader("music.json"));

			ArrayList<Song> songs = new ArrayList<Song>();

			reader.beginArray();

			while (reader.hasNext())
				songs.add(gson.fromJson(reader, Song.class));

			reader.close();
			MergeSort mergeSorter = new MergeSort(songs);
			mergeSorter.sort();
			return songs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Reads in all users from the JSON file and adds them to an array list.
	 */
	static ArrayList<User> readUsersJSON() {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader("users.json"));

			ArrayList<User> users = new ArrayList<User>();

			reader.beginArray();

			while (reader.hasNext())
				users.add(gson.fromJson(reader, User.class));

			reader.close();

			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/*
	 * Adds users to the JSON file
	 */
	static void writeUsersJSON(ArrayList<User> users) {
		try (final FileWriter writer = new FileWriter("users.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(users, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes a user from the user list and rewrites the user JSON file.
	 * @param deleteMe
	 * @return
	 */
	static boolean deleteAccount(User deleteMe) {
		ArrayList<User> users = readUsersJSON();
		for (User tempUser : users) {
			System.out.println(tempUser.getUsername());
			if (tempUser.equals(deleteMe)) {
				users.remove(deleteMe);
				writeUsersJSON(users);
				return true;
			}
		}
		return false;
	}
}
