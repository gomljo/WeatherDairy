package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zerobase.weather.exception.HttpConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static zerobase.weather.type.ErrorCode.*;
import static zerobase.weather.type.OpenApiConstants.REQUEST_METHOD;
import static zerobase.weather.type.OpenApiConstants.VALID_HTTP_STATUS_CODE;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    @Value(value = "${openweathermap.key}")
    private String apikey;

    private URL requestURL;
    private HttpURLConnection requestURLConnection;
    private int responseHttpStatusCode;
    private String response;




    private void makeUrl(String url) {
        try {
            this.requestURL = new URL(url);
        } catch (MalformedURLException malformedURLException) {
            throw new RuntimeException(malformedURLException);
        }
    }

    public boolean isValidHttpStatusCode() {
        return VALID_HTTP_STATUS_CODE == responseHttpStatusCode;
    }


    private void makeHttpUrlConnection() {
        try {
            this.requestURLConnection =
                    (HttpURLConnection) this.requestURL.openConnection();
        } catch (IOException ioException) {
            throw new HttpConnectionException(CAN_NOT_MAKE_CONNECTION);
        }
    }

    private void getHttpResponseCode() {
        try {
            responseHttpStatusCode = requestURLConnection.getResponseCode();
        } catch (IOException e) {
            throw new HttpConnectionException(CAN_NOT_READ_RESPONSE_CODE);
        }
    }

    private void setHttpRequestMethod() {
        try {
            this.requestURLConnection.setRequestMethod(REQUEST_METHOD);
        } catch (ProtocolException e) {
            throw new HttpConnectionException(PROTOCOL_TYPE_INVALID);
        }
    }

    private void makeErrorResponseToString() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(this.requestURLConnection.getErrorStream())
            );
            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            bufferedReader.close();
            this.response = response.toString();

        } catch (IOException e) {
            throw new HttpConnectionException(RESPONSE_CONTENT_INVALID);
        }
    }

    private void makeResponseToString() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(this.requestURLConnection.getInputStream())
            );
            String responseLine;
            StringBuilder response = new StringBuilder();
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine);
            }
            bufferedReader.close();
            this.response = response.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void request(String openApiSiteUrl) {
        makeUrl(openApiSiteUrl + apikey);
        makeHttpUrlConnection();
        setHttpRequestMethod();

        getHttpResponseCode();

        if (isValidHttpStatusCode()) {
            makeResponseToString();
        } else {
            makeErrorResponseToString();
        }


    }
}
