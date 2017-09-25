/**
 * Kilka spostrzeżeń:
 * 1."pobieranie tekstów z innych źródeł, 
 * w szczególności z serwisów internetowych 
 * (ten punkt odnosi się głównie do rozwiązań,
 *  które nie implementują wymagań [opcjonalnych])"
 * -> Łatwa rozszerszalność mojego programu dla innych źródeł,
 *  poprzez interfejs SourceType i interfejs WebSource
 *
 * 2. "zapis pobranych z Internetu 
 * tekstów do plików na dysku w takiej 
 * formie jak w przygotowanym archiwum 
 * texts.zip"
 * -> Metoda MakeZip z Web
 * 
 * 3. "znalezienie 5 najczęściej występujących 
 * wyrazów w tekstach wszystkich artystów 
 * (łącznie, bez podziału na konkretnych artystów)"
 * -> Nie bardzo rozumiem jak ma to wyglądać?
 * Wszyscy artyści w podanym folderze? Skąd w takim razie będzie wiadomo
 * czy folder rzeczywiście reprezentuję artystę,
 * a nie treści ustaw? Ewentualnie jeżeli, jest to bardzo ważne
 * kryterium oceny to prosze o doprecyzowanie i z chęcia poprawię :)
 *
 * 4. "uwzględnianie jedynie pierwszego 1000 wyrazów w
 *  tekstach każdego artysty przy obliczaniu 
 *  liczby wszystkich różnych wyrazów tego artysty"
 *  ->Stała w Const i zastosowanie jej w Stat
 */


public class main {
	
	public static void main(String[] args) {
	
		Parse.ParseIt(args);
		Parse.interprate();
	}

} 
