/**
 * Klasa obsługująca parsowanie parametrów wejściowych programu
 * Po sparsowaniu wejścia uruchamia dalsze funkcjonalności programu
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Parse {

	//Wzory napisów do parametrów
	static String sourceType = new String("--source-type");
	static String source = new String("--source");
	static String processors = new String("--processors");
	static String filters = new String("--filters");
	
	//Wzory argumentów do stat
	static CharSequence top5 = "top5";
	static CharSequence count = "count";

	
	static String sourceTypeParsed;
	static String sourceParsed;
	static String processorsParsed ;
	static String filtersParsed;
	
	static List<String> perfomersNames = new ArrayList<String>();
	static List <Performer> performers;
	static List <Stat> stats;
	static List<Filter> filtersArray= new ArrayList<Filter>();;
	
	static Sources kind;
	
	static public void ParseIt(String[] args)
	{
		//Po wszystkich argumentach przekazanych przy uruchamianiu programu
		for(int i=0;i<args.length;i++)
		{//Sprawdzaj za co odpowiada dany argument
			
			if(args[i].startsWith(sourceType))
			{//Typ wejścia
				sourceTypeParsed = new String();
				sourceTypeParsed=
						args[i].substring(args[i].indexOf("=")+1,args[i].length());
					if(sourceTypeParsed.equals("file"))
						kind=Sources.FILE;
					else if(sourceTypeParsed.equals("teksty.org"))
						kind=Sources.TEKSTY;
					else if(sourceTypeParsed.equals("azlyrics.org"))
						kind=Sources.AZLYRICS;

			}
			else if(args[i].startsWith(source))
			{//Ścieżka do źródła
				sourceParsed = new String();
				sourceParsed = 
						args[i].substring(args[i].indexOf("=")+1,args[i].length());
			}
			else if(args[i].startsWith(processors))
			{///Określa, które komponenty statystyki do przetworzenia 
				processorsParsed = new String();
				processorsParsed =
						args[i].substring(args[i].indexOf("=")+1,args[i].length());
				
			}
			else if(args[i].startsWith(filters))
			{//Lista plików z zabronionymi słowami
				
				String [] splitted;
				splitted=args[i].substring(args[i].indexOf("=")+1,args[i].length()).split(File.pathSeparator);
				for(int it=0;it<splitted.length;it++)
					try {
						filtersArray.add(new Filter(splitted[it]));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
			}
			else
			{
				//Nazwa artysty
				String name = new String();
				name = args[i].substring(0,args[i].length());//Bez "	
				perfomersNames.add(name);
			}
			
		}
		
	
		
	//	System.out.print(processorsParsed);
	}
	
	
	static public void interprate()
	{//Interpretuj i przekaż dalej informację 
		int z=0;
		performers= new ArrayList<Performer>();
		stats= new ArrayList<Stat>();
		for(String it: perfomersNames)//Utwórz każdego artyste z listy
		{
			Performer add=null;
			add = new Performer(kind, sourceParsed, it);
			if(add!=null)
				performers.add(add);
			//Twórz od razu obiekty obsługujące statystyki
			stats.add(new Stat(performers.get(z),filtersArray));
			z++;
		}
	
		//Jakie rodzaje statystyk:
		
		if((processorsParsed.contains(top5))&&(processorsParsed.contains(count)))
		{
			for (int i = 0; i < stats.size(); i++) {
				
				stats.get(i).top();
				System.out.print(stats.get(i).getPerformer().getName()+
						"\nCounts: "+stats.get(i).diffrent()+"\n"+stats.get(i)+"\n***\n");
				//toString przedefiniowany w stats, wyswietla top
			}
		}
		else if((processorsParsed.contains(top5))&&(!processorsParsed.contains(count)))
		{
			for (int i = 0; i < stats.size(); i++) {
				stats.get(i).top();
				System.out.print(stats.get(i).getPerformer().getName()+"\n"+stats.get(i)+"\n***\n");
			}
		}
		else if((!processorsParsed.contains(top5))&&(processorsParsed.contains(count)))
		{
			for (int i = 0; i < stats.size(); i++) {
				System.out.print(stats.get(i).getPerformer().getName()+
						"\nCounts: "+stats.get(i).diffrent()+"\n"+stats.get(i)+"\n***\n");
			}
			
		}
		else
		{//Brak statystyk do obliczenia
			System.out.print("Hej! Serio nie chcesz nic wiedzieć?");
		}
		
	}
	
	
}
