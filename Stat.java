/**
 * Klasa obsługująca moduł statystyk
 * Dla każdej nowej statystyki osobny obiekt
 * Ma to uzasadnienie:
 * pozwala zachować statystki,
 * oraz nie zrzuca tego na osobne obiekty,
 * np. performer
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Stat {
	
	private int diffrentWords;///<Liczba różnych słów
	
	///Linked, żeby automatycznie nie sortowało po stringach
	private LinkedHashMap <String, Integer>  topWords;///<Lista top używanych słów
	private Performer performer; ///<"Wskaźnik" do arysty dla którego statystyki
	private List<Filter>  filters;///<Tablica zbiorów zabronionych słów
	private HashSet <String> banned =new HashSet<String>();///<Jednolity zbiór zabronionych słów
	
	/**
	 * Konstruktor
	 * Oprócz inicjalizacji składowych
	 * Tworzy z tablicy zbiorów zabronionych słów
	 * jeden zbiór.
	 * @param [in]performers Artysta
	 * @param [in]filters Zbiór filtrów
	 */
 	public Stat(Performer performers, List<Filter> filtersArray)
	{
		 this.diffrentWords=0;
		 this.topWords=new LinkedHashMap<String, Integer> ();
		 this.performer=performers;
		 this.filters=filtersArray;
		 //Przejrzyj słowa w podanych filtrach i dodaj je bez powtórzen do listy zakazanych słów
		 for(Filter i:this.filters)
		 {
				 banned.addAll(i.getWords());
		 }
	}
	

 	/**
 	 * Moduł liczby różnych słów
 	 * @return int Liczba róznych słów w tekstach danego artysty
 	 */
	public int diffrent()
	{
		HashSet<String> words = new HashSet<String>();//Lista słów
		List<String> songs = new ArrayList<String>();//Lista piosenek
		songs.addAll(performer.getSongs());
		//int countWords=0;
		String[] wordsSplitted;//Dany tekst rozbity na słowa
		for(String split:songs)
		{
			wordsSplitted= split.split("[\\s,.:;?!()-]+");
			//Przejrzyj wszystkie słowa
			for(String array:wordsSplitted)
			{
				//Unikam dodawania słowa pustego i " 
				if((!array.equals("\""))&&(!array.isEmpty()))//&&(countWords<Conts.FIRST_WORDS)
				{
					//countWords++;
					words.add(array.toLowerCase());//Hash set dodaje tylko nowe obiekty	
				}
			}
		}

		//Przechowuj w obiekcie
		this.diffrentWords = words.size();
		//Zwróć liczbę różnych słów
		return words.size();
	}

	
	/**
	 * Moduł top popularnych słów
	 * @return TreeMap<String,Integer> Mapa top słów i liczbą wystąpień
	 */
	public  TreeMap<String,Integer> top()
	{
	    Map <String, Integer> words = new TreeMap<String, Integer>(); 
	    TreeMap <String, Integer> top = new TreeMap<String, Integer>();
		
		List<String> songs = new ArrayList<String>();
		songs.addAll(performer.getSongs());
		String[] wordsSplitted;
		for(String split:songs)
		{
			wordsSplitted= split.split("[\\s,.:;?!()-]+");
			for(String array:wordsSplitted)
			{
					//Unikam dodawania słowa pustego i " oraz filtrowanie
					if((!array.equals("\""))&&(!array.isEmpty())&&(!banned.contains(array.toLowerCase())))
					{
						if(words.isEmpty())//Uwaga na pustą listę
						{
							String insert = new String(array.toLowerCase());
							words.put(insert, 1);
						}
						else
						{
							String insert = new String(array.toLowerCase());
							if(words.containsKey(insert))
								words.put(insert, words.get(insert) + 1);//Zwieksz licznik
							else
								words.put(insert, 1);//Dodaj nowe słowo do mapy
						}				
				}
			}
		}
	
		//Posortuj słowa wg. ilosci wystapien
		List<Entry<String,Integer>> sorted = new ArrayList<Entry<String,Integer>>();
		sorted.addAll( words.entrySet());
		Collections.sort(sorted, new CustomComperator());//Własny comperator
		
		int i=0;
		//Wybierz topX popularnych słów wraz z liczbą do wystąpień
		for(Entry<String, Integer> entry : sorted)
		{
			if(i>=Const.NUMBER_TOP) break;
			else
			{
				i++;
				top.put(entry.getKey(), entry.getValue());
				this.topWords.put(entry.getKey(), entry.getValue());
			}
		}

		return top;
		
	}



	
	/**
	 * Formatuje statystyki dla danego artysty
	 * @Override toString()
	 * @return String Sformatowane do wypisania statystyki
	 */
	@Override
	public String toString()
	{
		String returned=new String();
		returned+="[";
		for(Entry<String, Integer> entry : topWords.entrySet())
			returned+=entry.getKey()+"="+entry.getValue()+", ";
	
		if(returned.endsWith(", ")) returned=returned.substring(0, returned.length()-2);
		returned+="]\n";
		return returned;
	}
	
	/**
	 * Getter liczby róznych słów
	 * @return int Liczba różnych słówa
	 */
	public int getDiffrentWords() {
		return diffrentWords;
	}

	/**
	 * Getter artysty dla którego statystyki
	 * @return Performer  artysta
	 */
	public Performer getPerformer() {
		return performer;
	}

}
