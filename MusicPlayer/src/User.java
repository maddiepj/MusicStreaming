import java.util.ArrayList;

public class User {
    ArrayList<Playlist> playlists;
    private String username;
    private String password;

    /**
     * Default user constructor
     */
    public User() {
        this.username = null;
        this.password = null;
    }

    /**
     * User constructor that takes in a name and password.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.playlists = new ArrayList<Playlist>();
    }

    //  ------------- START getters section -------------
    public static String getPassword(User u) {
        return u.password;
    }


    public static String getUsername(User u) {
        return u.username;
    }
    
    public String getUsername() {
        return this.username;
    }

    public static ArrayList<Playlist> getPlaylists(User user) {
        return user.playlists;
    }

    public static Playlist getPlaylist(User u, String playlistName) {
        for (Playlist playlist : u.playlists)
            if (playlistName.equals(Playlist.getPlaylistName(playlist)))
                return playlist;
        return null;
    }

    public ArrayList<Playlist> getPlaylists() {
        return this.playlists;
    }

//  ------------- END getters section -------------

    public User login(String username, String password) {
        ArrayList<User> users = JsonHelperMethods.readUsersJSON();

        for (User u : users) {
            if (User.getUsername(u).compareTo(username) == 0) {
                if (BCrypt.checkpw(password, User.getPassword(u))) {
                    return u;
                }
            }
        }
        System.out.println("User can't be found, printed from login in user class");
        return null;
    }

    public Integer singup(String username, String password) {
        ArrayList<User> users = JsonHelperMethods.readUsersJSON();

        User u = new User(username, BCrypt.hashpw(password, BCrypt.gensalt()));

        if (!users.contains(u)) {
            users.add(u);
            JsonHelperMethods.writeUsersJSON(users);
            return 1;
        }
        return 0;
    }

    /**
     * Adds a Playlist to the current user.
     *
     * @param playlist
     */
    public void addPlaylist(Playlist playlist) {
        if (this.playlists == null)
            this.playlists = new ArrayList<Playlist>();
        if (this.playlists.contains(playlist)) {
            for (Playlist p : this.playlists) {
                if (p.equals(playlist)) {
                    for (Song song : Playlist.getSongs(playlist))
                        Playlist.addSongToPlaylist(p, song);
                    break;
                }
            }
        } else {
            this.playlists.add(playlist);
        }

    }

    /**
     * Updates the user by loading the latest user data into the main user list.
     */
    public void updateUser() {
        ArrayList<User> users = JsonHelperMethods.readUsersJSON();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(this)) {
                users.set(i, this);
                JsonHelperMethods.writeUsersJSON(users);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User toCompare = (User) obj;
            return this.username.toLowerCase().equals(toCompare.username.toLowerCase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    public String toString() {
        return this.username + " " + this.password;
    }
}
