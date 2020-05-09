import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist() {
    }

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<Song>();
    }

    public static void addSongToPlaylist(Playlist playlist, Song song) {
        playlist.songs.add(song);
    }

    public static String testFun(String s) {
        return "Method returned: " + s + s;
    }

    public static ArrayList<Song> getSongs(Playlist p) {
        return p.songs;
    }

    public static String getPlaylistName(Playlist p) {
        return p.name;
    }

    //	TODO rename method 
    public static boolean addSongToPlaylist_(Playlist p, Song song) {
        if (p.songs == null)
            p.songs = new ArrayList<Song>();
        if (!p.songs.contains(song)) {
            System.out.println("Song added!");
            p.songs.add(song);
            return true;
        } else
            System.out.println("Song already exists!");
        return false;
    }

    public static boolean addSongToPlaylist(Playlist p, String songId) {
        Song song = Song.getSongById(songId);
        return addSongToPlaylist_(p, song);
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    public String getPlaylistName() {
        return this.name;
    }


//	public void addSongToPlaylist(Song song) {
//		this.songs.add(song);
//	}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Playlist) {
            Playlist toCompare = (Playlist) obj;
            return this.name.toLowerCase().equals(toCompare.name.toLowerCase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Playlist{" + "name='" + name + '\'' + '}';
    }
}