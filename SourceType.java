import java.util.List;

/**
 * Interfejs obłsługi źródła danych
 * Narzuca konieczność implementacji:
 * -# List<String> returnTexts - z gotową listą plików folderze
 * od getFiles, zwraca listę piosenek w danym folderze
 * @author majki
 *
 */
public interface SourceType {
	public  List<String> returnTexts(String directory, String name);
}
