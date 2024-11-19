package blankmod.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
// import com.google.gson.Gson;
// import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import blankmod.ModInitializer;

public class HttpRequest {

    public static void makeGetRequestForJson(String url) {
        ModInitializer.logger.info("Making request to: " + url);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest()
            .method("GET")
            .url(url)
            .header("Accept", "application/json")
            .header("User-Agent", "sts/" + CardCrawlGame.TRUE_VERSION_NUM)
            .timeout(0)
            .build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                ModInitializer.logger.info("response status: " + httpResponse.getStatus().getStatusCode());

                // Handle json object in response
                // Gson gson = new Gson();
                // JsonObject jobj = gson.fromJson(httpResponse.getResultAsString(), JsonObject.class);

                // Posting a runnable is necessary if you need to do any sort of rendering done after getting the response
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ModInitializer.logger.info("runnable running after response from: " + url);
                    }
                });
            }

            @Override
            public void cancelled() {
                ModInitializer.logger.info("canceling request to " + url);
            }

            @Override
            public void failed(Throwable t) {
                ModInitializer.logger.info("request to " + url + " failed with message: " + t.getMessage());
            }
        });

    }

}
