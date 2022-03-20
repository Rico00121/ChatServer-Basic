package programming3.chatsys.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 * Do some change by Rico.
 * Just for testing register and post message.
 */
public class HttpClient {
    public static void main(String[] args) throws IOException {
        //post message.
        String address="http://localhost/message/?username=user1&password=mypassword";
        String POST="POST";
        String GET="GET";
        String query="{message:\"Hello World user1!\"}";
        //register.
        String address2="http://localhost/user/user9";
        String query2="{\"username\":\"user9\",\"fullname\":\"Full Name\",\"password\":\"PassWord\"}";
        httpRequest(address2,"DELETE",query2);
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
