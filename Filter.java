/**
 * Klasa reprezentująca "zabronione" słowa
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;


public class Filter {
	
	///Zbiór zabronionych słów z danego źródła
	private HashSet<String>  words =  new HashSet<String>();
	
	/**
	 * Getter
	 * @return String[] lista zabronionych słówa
	 */
	public HashSet<String> getWords()
	{
		return  words;
	}
	
	/**
	 * Konstruktor
	 * @param [in] filePath ścieżka do pliku z zabronionymi słowami
	 */
	public Filter(String filePath) throws FileNotFoundException
	{
		boolean err=false;
		//Wczytaj z pliku
		byte bytes[] = null;
		try {
			bytes = Files.readAllBytes(new File(filePath).toPath());
		} catch (IOException e) {
			System.out.print("Plik nie istnieje : "+filePath+"\n");//Kontynuuj bez wczytania danego filtra
			err=true;
		//	e.printStackTrace();
		}
		if(!err)
		{
		///Słowa oddzielone "\n"
		String text = new String(bytes, StandardCharsets.UTF_8);

		///Słowa podzielone
		String[] wordsSplitted;
		wordsSplitted= text.split("\n");
		
		///Utwórz zbiór zakazanych słów
		for(String it:wordsSplitted)
			words.add(it);
		}
	}

}
