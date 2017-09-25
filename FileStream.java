/**
 * Klasa obsługująca wczytywanie piosenek z pliku
 * Musi wywiązywać się z kontraktu "nałożonego" 
 * przez interfejs SourceType
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStream implements SourceType {

	
	/**Metoda z interfejsu \ref SourceType
	 * Zwraca liste piosenek dla danego autora
	 * [in] String directory Ścieżka do piosenek
	 * [in] String name Autor
	 * @return List<String> Lista tekstów piosenek
	 */
	@Override
	public List<String> returnTexts(String directory, String name) {
		
		List<String> texts = new ArrayList<String>();
		File[] paths= getFiles(directory, name);
		if((paths==null)||(paths.length==0))
		{
			System.out.print("Brak piosenek "+name +" !\n");
			return null;
		}
		else
		{
			for (File file : paths) {//Iteracja po liście plików 
		     if (file.isFile()) {
		    	 
		    	 if (!file.isDirectory())//Jeżeli nie folder
		    	 {
		    		 if(file.getName().endsWith(".txt"))//To sprawdź czy rozszerzenie .txt
		    		 {
		    			 Path text = Paths.get(file.getParent(),file.getName());//Pobierz teskt piosenki

		    			 try {
		    				 byte[] textArray = Files.readAllBytes(text);
		    				 //Przepisz nazwę pisoenki do stringa
		    				 String textString = new String(textArray, StandardCharsets.UTF_8);
		    				 //Dodaj tekst do listy tekstów
		    				 texts.add(textString);
		    			 	} catch (IOException e) {//Łap wyjątek
		    				 System.out.println(e);//Wypisz go
		    			 		}
		    		 }
		    	 }
		     }
		 }
		
		return texts;//Zwróc listę tekstów piosenek
		}
		
	}

	
	/**
	 * Metoda zwraca listę plików w folderze danego artysty
	 * lub null jeżeli folder nie istnieje
	 * [in] String directory Ścieżka do piosenek
	 * [in] String name Autor
	 * @return File[] Lista plików
	 */
	public File[] getFiles(String directory, String name) {
		File f;
	    File[] paths = null;
	    File[] dirPaths = null;
	  
	    try{
	    	
	    f= new File(directory);
	    paths= f.listFiles();

		File dir = null;

		//Szukaj folderu danego artysty
		 for(int i=0;i<paths.length;i++)
         {
			 if (paths[i].isDirectory())
			 {
				 if(name.equals(paths[i].getName()))
				 {
					 dir= new File(paths[i].getAbsolutePath());
					 dirPaths= dir.listFiles();
					 i=paths.length;//Zakończ pętle
				 }
			 }
			 
        }
	    }catch(Exception e){
	    	 System.out.print("\nZła ścieżka do tekstów piosenek\n");
	    	// e.printStackTrace();
	    	 return null;
	    }
	    
	    if( dirPaths==null)	System.out.print("Brak folderu z piosenkami "+ name+" !\n");
	 	
	    return dirPaths;//Zwróc listę "adresów" do plików w folderze danego artysty
	}

}
