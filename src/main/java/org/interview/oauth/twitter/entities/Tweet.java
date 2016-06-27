package org.interview.oauth.twitter.entities;

import java.io.Serializable;

/**
 * Created by mike on 2016-06-26.
 */
public class Tweet implements Serializable, Comparable<Tweet> {
    private Long id;
    private Long created;
    private String text;
    private User user;

    public Tweet(){}

    public Tweet(Long id, Long created, String text, User user){
        this.id = id;
        this.created = created;
        this.text = text;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    @Override
    public int compareTo(Tweet compareTweet) {
        if(compareTweet.getUser().getCreated() > this.getUser().getCreated()) return -1;
        if(compareTweet.getUser().getCreated() < this.getUser().getCreated()) return 1;
        if(compareTweet.getCreated() > this.getCreated()) return -1;
        if(compareTweet.getCreated() < this.getCreated()) return 1;
        return 0;
    }
}
