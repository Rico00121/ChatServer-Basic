package programming3.chatsys.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 * Do some change by Rico.
 */
public class HttpClient {
    public static void main(String[] args) throws IOException {
        String address="http://localhost/message/?username=Rico&password=123456cs";
        String POST="POST";
        String query="{message:\"Hello World in JSON3!\"}";
        String address2="http://localhost/user/user_2";
        String GET="GET";
        String query2="{\"username\":\"user_2\",\"fullname\":\"Full Name\",\"password\":\"PassWord\"}";
        httpRequest(address,POST,query);
    }
    public static void httpRequest(String address, String method, String query) throws IOException {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        if (query != null) {
            connection.setDoOutput(true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(query);
            writer.flush();
            writer.close();
        }
        System.out.println("Response: " + connection.getResponseCode() + " " + connection.getResponseMessage());
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//            System.out.println(line);
//        }
    }
}
