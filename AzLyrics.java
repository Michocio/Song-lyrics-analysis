import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * Klasa obsługuje pobieranie piosenek z azlyrics.com
 * Definiuje metody z danych interfejsów
 * Zadecydowałem, że pobieranie tekstów i linków
 * jest na tyle różne dla azlyrics i dla teksty.org,
 * że nie ma sensu wyodrębniać osobnej metody
 * @author majki
 *
 */

public class AzLyrics extends Web implements WebSource, SourceType {

	@Override
	public List<String> getLinks(String directory, String name) {
		List <String> links= new ArrayList<String>();
			String artist=name.toLowerCase();
			artist=artist.replaceAll(" ","");//Usun spacje
			String www =new String(directory+"/"+
						artist.substring(0, 1)+"/"+artist+".html");//Utworz prawidlowy url

			Document d =null;
			try{
				d = Jsoup.connect(www).get();
			}catch (Exception e) {
				System.out.print("\nCoś poszło nie tak przy próbie połączenia z Azlyrics.com\n");
			}
			if(d!=null)
			{	
				//Wczytaj listę piosenek
				for (Element x : d.select("a[href^=\"../lyrics\"")) 
				{
					String link= new String(x.attr("href"));
					link=link.substring(2);
					String val=directory+link;
					links.add(val);
				}
			}		
		return links;
	}
	@Override
	public List<String> returnTexts(String directory, String name) {
		List <String> songs= new ArrayList<String>();
		HashMap <String,String> save= new HashMap<String,String>();
		List <String> texts= new ArrayList<String>();
		songs.addAll(getLinks(directory,name));
		Integer ile=0;
		if(!songs.isEmpty())
		{
			//Dla każdego linka do piosenki
			for(String song: songs)
			{

					Document d = null;
					try {
						System.out.print(song+"\n");
						d = Jsoup.connect(song).get();
					} catch (IOException e) {
						System.out.print("\nBłąd przy wczytywaniu danych ze strony Azlyrics.com\n");
						//e.printStackTrace();
					}
					String text = d.select("div").get(0).text();
					System.out.print(text);
					texts.add(text);
					String title=Integer.toString(ile); //d.select(".song").get(0).text();
					save.put(title, text);
					ile++;
			}
			/*try {
				Web.makeZip("/home/majki/eclipse/texts", name, save);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return texts;
		}
		else {
			System.out.print("\nBrak tekstów danego artysty na Azlyrics.com\n");
			return null;
			
		}
	}

}
