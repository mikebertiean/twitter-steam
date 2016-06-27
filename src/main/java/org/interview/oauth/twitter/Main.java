package org.interview.oauth.twitter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.interview.oauth.twitter.entities.Tweet;
import org.interview.oauth.twitter.entities.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mike on 2016-06-25.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, TwitterAuthenticationException {
        System.out.println("Twitter Stream App by Michael Bertiean");

        //initialization of twitter authenticator
        TwitterAuthenticator twitterAuthenticator = new TwitterAuthenticator(
                System.out,
                "vp8qXAMoZzy6jowJdtouPLUUb",
                "IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7");

        getTweets(twitterAuthenticator.getAuthorizedHttpRequestFactory());
    }

    private static void getTweets(HttpRequestFactory authorizedHttpRequestFactory) {
        System.out.println("Opening stream of tweets tracking on bieber ...");
        try {
            GenericUrl url = new GenericUrl("https://stream.twitter.com/1.1/statuses/filter.json?track=bieber");
            HttpRequest request = authorizedHttpRequestFactory.buildGetRequest(url);
            HttpResponse response = request.execute();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent()));
            List<Tweet> tweets = new ArrayList<Tweet>();
            long startTime = System.currentTimeMillis();

            System.out.println("Receiving tweets ...");
            //while loop that runs until we collect 100 tweets from the stream AND for 30 seconds
            while(tweets.size() < 100 && System.currentTimeMillis() - startTime < 30000) {
                String line = reader.readLine();
                if(line != null){
                    Tweet tweet = parseResponseIntoTweetObject(line);
                    //some of the data returned is missing the user object, so we skip those
                    if(tweet!=null) {
                        tweets.add(tweet);
                        System.out.print(tweets.size() + " ");
                    }
                }
            }
            System.out.println();
            sortAndDisplayResults(tweets);
        }
        //exception for reading response
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Tweet parseResponseIntoTweetObject(String response) {
        Tweet tweet = null;
        try {
            JSONObject jsonTweet = new JSONObject(response);
            JSONObject jsonUser = jsonTweet.getJSONObject("user");


            User user = new User(
                    jsonUser.getLong("id"),
                    getTwitterDate(jsonUser.getString("created_at")),
                    jsonUser.getString("name"),
                    jsonUser.getString("screen_name"));

            tweet = new Tweet(
                    jsonTweet.getLong("id"),
                    jsonTweet.getLong("timestamp_ms"),
                    jsonTweet.getString("text"),
                    user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    private static Long getTwitterDate(String date){
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        try {
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void sortAndDisplayResults(List<Tweet> tweets) {
        System.out.println("Sorting ...");
        Collections.sort(tweets);

        for(Tweet tweet : tweets){
            System.out.println(tweet.getUser().getName() + " (@" + tweet.getUser().getScreenName()+") - created: " + tweet.getUser().getCreated() + " id: " + tweet.getUser().getId() + " ");
            System.out.println(tweet.getText() + " created: " + tweet.getCreated() + " id: " + tweet.getId());
            System.out.println();
        }
    }
}