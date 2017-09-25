import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Michał Jaroń
 * @version 1.7
 *Klasa reprezetnujaca "byt" atrystyczny
 *Obiekt tej klasy może być zespołem,
 *piosenkarzem itp.	 
 */

public class Performer {
	
	private String name;///<Nazwa zaspołu/piosenkarza
	int songsNumber=0;
	int wordsNumber=0;
	
	private List<String> songs;///<Lista tekstów piosenek danego artysty
	
	/**
	 * Getter lista tekstów piosenek
	 * @return
	 */
	public List<String> getSongs()
	{
		return this.songs;
	}

	
	/**
	 * Konstruktor performer
	 * @param source typ Źródła wczytywania danych
	 * @param directory Ścieżka do źródła danych
	 * @param name Nazwa performera
	 */
	public Performer(Sources source, String directory, String name)
	{
		List<String> help= new ArrayList<String> ();
		songs= new ArrayList<String>();
		this.name=name;
		//Jakie źródła danych
		switch(source)
		{
			case AZLYRICS:
				AzLyrics test3= new AzLyrics();
				help=test3.returnTexts(directory,name);
				if(help!=null)
					songs.addAll(help);
				break;
			case TEKSTY:
				Teksty test2= new Teksty();
				help=test2.returnTexts(directory,name);
				if(help!=null)
					songs.addAll(help);
				break;
			case FILE:
				FileStream test = new FileStream();
				help=test.returnTexts(directory,name);
				if(help!=null)
					songs.addAll(help);
				break;
		}
		songsNumber=songs.size();
	}

	/**
	 * Getter nazwa permorfer
	 * @return String nazwa permorfer
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter nazwa performer
	 * @param name performer
	 */
	public void setName(String name) {
		this.name = name;
	}

}
