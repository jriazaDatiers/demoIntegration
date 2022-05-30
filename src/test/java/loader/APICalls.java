package loader;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class APICalls {

    public HttpRequest request;

    public APICalls() {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://staging.unidata.msf.org/msf-mdm-unidata/rest/ud-api/v1"))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
