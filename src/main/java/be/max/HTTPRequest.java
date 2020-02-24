package be.max;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {

    private URL url;
    private HttpURLConnection conn;
    private Map<String, String> parameters = new HashMap<>();

    @Override
    public String toString() {
        return "HTTPRequest{" +
                "url=" + url +
                ", con=" + conn +
                ", parameters=" + parameters +
                '}';
    }

    public HTTPRequest(String url) throws IOException {
        //Creating a Request
        this.url = new URL(url);
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
    }

    public String readResponse() throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}


