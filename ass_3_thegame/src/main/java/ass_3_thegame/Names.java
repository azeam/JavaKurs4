package ass_3_thegame;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class Names {
    // get random name from online API
    public List<String> getRandomNames(int number) { 
        List<String> names = new ArrayList<String>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://namey.muffinlabs.com/name.json?count=" + number + "&frequency=rare"))).GET().build();

        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(json);
            for (int i = 0; i < jsonArray.size(); i++)
            {
                String name = (String) jsonArray.get(i);
                names.add(name);
            }
        } catch (InterruptedException | IOException e) {
            // enable offline play/backup if API goes down
            names = Arrays.asList("Ola", "Dennis", "Malek", "Anton", "Alexander", "Andreas", "Christer", "Rosita", "Mohammed", "Yousef");
            return names;
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return names;
    }
    
}
