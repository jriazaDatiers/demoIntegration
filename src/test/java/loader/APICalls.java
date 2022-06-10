package loader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APICalls {

    public HttpClient client;
    public HttpRequest request;
    public HttpResponse<String> response;
    public String responseBody;
    public int responseStatusCode;
    public Article article;


    public void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
        article = new Article();
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("https://staging.unidata.msf.org/msf-mdm-unidata/rest/ud-api/v1/articles?login=DASE&password=D@t@S3rv&page=1&filter=code='ADAPADAP2I-'&mode=2"))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        responseStatusCode = response.statusCode();

        responseBody = response.body();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);

        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            json = (JSONObject) parser.parse(responseBody);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert json != null;
        JSONArray articleList = (JSONArray) json.get("rows");
        JSONObject articleJson = (JSONObject) articleList.get(0);
        System.out.println(articleJson.get("type"));

        //article.setType(articleList.);

        System.out.println(json.size());

    }


    public APICalls() {

    }



}
