import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
/**
 * Nadklasa dla klas obsługująych źródła webowe
 * Dostarcza definicje metody zapisującej piosenki
 * do pliku
 * @author majki
 *
 */

public class Web {

	public static void makeZip(String directory, String name, HashMap<String,String> Songs) throws FileNotFoundException {
		String where= directory+"/"+name;
		File dir= new File(where);
		dir.mkdir();///<Utwórz folder
		String fileName= where+"/";
	
		List<Entry<String,String>> entry=new ArrayList<Entry<String,String>>();
		entry.addAll(Songs.entrySet());
		
		for(Entry<String, String> song: entry)
		{
			//System.out.print(fileName+song+".txt \n");
			try {
				PrintWriter writer = new PrintWriter(fileName+song.getKey()+".txt", "UTF-8");
				writer.print(song.getValue());///<Zapisz tekst
				writer.close();///<Zamknij strumień
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
