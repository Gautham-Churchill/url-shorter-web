package dev.gautham.urlshortener.domain.service;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Slf4j
public class UrlExistenceValidator {

    public static boolean isUrlExists(String urlString) {
        try {
            log.debug("Checking if URL exists: {}", urlString);
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000); // 5 seconds
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400); // 2xx and 3xx are valid
        } catch (Exception e) {
            log.error("Error while checking URL: {}", urlString, e);
            return false; // URL is invalid or not reachable
        }
    }
}
