package de.radjaguar2005.rangaddon.api;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketAddress;
import java.net.URL;

public class DiscordWebhook {
    public static void sendWebhook(String webhookUrl, SocketAddress message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = "{\"content\": \"" + message + "\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            // Hier kannst du die Antwort überprüfen, um sicherzustellen, dass die Nachricht gesendet wurde

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
