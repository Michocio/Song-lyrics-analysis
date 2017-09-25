/**
 * Własny comperator używany przez moduł Statystyk 
 * do sortowania par: Słowo - liczba wystąpień
 */

import java.util.Comparator;
import java.util.Map.Entry;


public class CustomComperator implements Comparator<Entry<String, Integer>> {

	/**
	 * @return 1 arg0>arg1, -1 arg0<arg1, 0 arg0=arg1
	 */
	@Override
	public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
		
		int x0,x1;
		x0=arg0.getValue();
		x1=arg1.getValue();
		if(x0>x1) return -1;
		else if(x0<x1) return 1;
		else//Jeżeli równa liczba wystąpień to sortuj alfabetycznie
		{
			if(arg0.getKey().compareTo(arg1.getKey())<0) return -1;
			else if(arg0.getKey().compareTo(arg1.getKey())>0) return 1;
			else return 0;
		}

	}

}
