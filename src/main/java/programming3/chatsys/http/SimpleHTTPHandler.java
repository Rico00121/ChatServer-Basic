package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
/**
 * @author Rico00121 (837043207@qq.com)
 */
public class SimpleHTTPHandler implements HttpHandler {
    private final String response;

    public SimpleHTTPHandler(String response) {
        this.response = response;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().getPath() == "/favicon.ico"){
            return;
        }
        System.out.println("Requested URI: " + exchange.getRequestURI());
        System.out.println("Client: " + exchange.getRemoteAddress());
        System.out.println("Request method: " + exchange.getRequestMethod());
        System.out.println("Request headers: " + exchange.getRequestHeaders().entrySet());
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        System.out.println("Request body: ");
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            System.out.println(line);
        }
        System.out.println("Sending response: " + response);
        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
        writer.write(response);
        writer.flush();
        writer.close();
        System.out.println();
    }
}
