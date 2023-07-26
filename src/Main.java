import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {

        List<String> links = new ArrayList<>();
        links.add("https://www.bbc.com/");
        links.add("https://www.bbc.com/news/world-europe-66299186");
        links.add("https://www.bbc.com/news/world-asia-china-66299379");
        links.add("https://www.bbc.com/news/world-australia-66297783");
        links.add("https://www.bbc.com/future/article/20230725-making-the-blue-flash-how-i-reconstructed-a-fatal-atomic-accident");
        links.add("https://www.bbc.com/travel/article/20230725-the-worlds-most-liveable-cities-for-2023");
        links.add("https://www.bbc.com/news/world-europe-66299186");
        links.add("https://www.bbc.com/news/world-asia-china-66299379");
        links.add("https://www.bbc.com/news/world-australia-66297783");
        links.add("https://www.bbc.com/future/article/20230725-making-the-blue-flash-how-i-reconstructed-a-fatal-atomic-accident");
        links.add("https://www.bbc.com/travel/article/20230725-the-worlds-most-liveable-cities-for-2023");


        long timeStart = System.nanoTime();
        links.forEach(Main::getWebContent);
        long timeEnd = System.nanoTime();
        long diff = timeEnd-timeStart;
        System.out.println("Tiempo sin Paralelismo: " + diff + " ns => " + diff/1000000000 + " s");


        timeStart = System.nanoTime();
        links.stream().parallel().forEach(Main::getWebContent);
        timeEnd = System.nanoTime();
        diff = timeEnd-timeStart;
        System.out.println("Tiempo con Paralelismo: " + diff + " ns => " + diff/1000000000 + " s");


        String link = "https://www.bbc.com/";
        String result = getWebContent(link);

        //imprimo el index de la página
        //System.out.println(result);


    }

    //synchronized bloquea la función para que no se pueda usar
    //hasta que finalice el proceso que la utiliza
    private synchronized static String getWebContent(String link){
        //System.out.println("INIT");
        //System.out.println(link);
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String encoding = conn.getContentEncoding(); //traigo la cabecera de la web

            InputStream input = conn.getInputStream();

            //se junta el resultado y se guarda
            //Las clases BufferReader, InputStreamReader, etc, sirven para leer archivos,
            //procesar flujos de información, etc, pero la función line devuelve un string
            //de strings. En vez del collect, podría haber asignado hasta lines a un array
            //de strings y mostrarlo.
            Stream<String> lines = new BufferedReader(new InputStreamReader(input)).
                    lines();
            //System.out.println("END");
            return lines.collect(Collectors.joining());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return "";
    }
}
