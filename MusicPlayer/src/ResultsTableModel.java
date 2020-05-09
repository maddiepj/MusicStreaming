import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ResultsTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Song> songs ;
	private String[] columns ; 

	/**
	 * ResultsTableModel Constructor requiring a songList.
	 * @param songsList
	 */
	public ResultsTableModel(ArrayList<Song> songsList)
	{
		super();
		this.songs = songsList;
		columns = new String[]{"Song", "Artist", "Song ID"};
	}
	
	public int getColumnCount() 
	{
		return columns.length ;
	}
	
	@Override
	public int getRowCount()
	{
		return songs.size();
	}
	
	/**
	 * returns the song Name, Artist Name, or ID necessary for GUI table organization.
	 */
	public Object getValueAt(int row, int col)
	{
		Song song = songs.get(row);
		switch(col)
		{
		case 0: return song.getSongName();
		case 1: return song.getSongArtistName();
		case 2: return song.getSongId();
		default: return null;
		}
	}
	
	
	public String getColumnName(int col)
	{
		return columns[col];
	}

	/**
	 * Updates the table model on call
	 * @param songs
	 */
	public void updateTable(ArrayList<Song> songs)
	{
		this.songs = songs;
		fireTableDataChanged();
	}
}