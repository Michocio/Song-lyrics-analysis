import java.util.List;
/**
 * Interfejs obłsługi źródła webowe
 * Narzuca konieczność implementacji:
 * -# List<String> getLinks - z gotową listą linków do wszystkich piosenek autora
 * @author majki
 *
 */

public interface WebSource {
	public  List<String> getLinks(String directory, String name);
}
