import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Klasa obsługuje pobieranie piosenek
 * z teskty.org
 * implementuje dwa iterfejsy
 * @author majki
 *
 */

public class Teksty extends Web implements SourceType, WebSource {

	@Override
	public List<String> returnTexts(String directory, String name)  {
		List <String> songs= new ArrayList<String>();
		//Mapa potrzebna do makeZip. Zawiera tytuł oraz tekst piosenki
		HashMap <String,String> save= new HashMap<String,String>();
		List <String> texts= new ArrayList<String>();
		
		songs.addAll(getLinks(directory,name));
		if(!songs.isEmpty())
		{
			for(String song: songs)
			{
			//	System.out.print("\n"+song+"\n");
				//Uwaga na pułapki na teksty.org
				if((!song.contains("empik"))&&(!song.contains("album")))
				{
					Document d = null;
			
					try {
						d = Jsoup.connect(song).get();
					} catch (IOException e) {
						System.out.print("\nBłąd przy wczytywaniu danych ze strony teksty.org\n");
						//e.printStackTrace();
					}
					//Wyobrębnij tekst
					String text = d.select("div.originalText").get(0).text();
					texts.add(text);
					String title= d.select(".song").get(0).text();
					save.put(title, text);
				}
			}
			/* Możliwy zapis do pliku:
			try {
				Web.makeZip("/home/majki/eclipse/texts", name, save);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return texts;
		}
		else {
			System.out.print("\nBrak tekstów danego artysty na teksty.org\n");
			return null;
			
		}
	}
	
	@Override
	public List<String> getLinks(String directory, String name) {
		List <String> links= new ArrayList<String>();
		try {
			String artist=name.toLowerCase();
			artist=artist.replace(' ','-');
			//System.out.print(artist+"\n");
			String www =new String(directory+"/");
			www+=artist+",teksty-piosenek";
			boolean stop=false;
			Document d =null;
			try{
				d = Jsoup.connect(www).get();
			}catch (Exception e) {
				System.out.print("\nCoś poszło nie tak przy próbie połączenia z teksty.org\n");
			}
			if(d!=null)
			{	
				//int ile=0;
				while((stop!=true))
				{
					//Czy jest kolejna strona z utworami
					Elements next= d.select(".next-site");
					//System.out.print("a"+next.attr("href")+"\n");
				
					for(Element i: d.select(".artist"))
					{
						String url = i.attr("href");
						links.add(url);
					//	ile++;
						//System.out.println(url+"\n");
					}
					if(!next.attr("href").isEmpty())
					{
						//Otwórz kolejną podstronę
						 d = Jsoup.connect("http://teksty.org"+next.attr("href")).get();
					//	ile++;
					}
					else stop=true;
					}
			}
		} catch (IOException e) {
			System.out.print("\nBłąd przy wczytywaniu danych ze strony teksty.org\n");
			//e.printStackTrace();
		}
		
		return links;//Zwróc bezpośrednie linki do wszysktich tekstów autora
	}

}
