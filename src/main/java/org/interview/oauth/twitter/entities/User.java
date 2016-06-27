package org.interview.oauth.twitter.entities;

import java.io.Serializable;

/**
 * Created by mike on 2016-06-26.
 */
public class User implements Serializable{
    private Long id;
    private Long created;
    private String name;
    private String screenName;

    public User(){}

    public User(Long id, Long created, String name, String screenName){
        this.id = id;
        this.created = created;
        this.name = name;
        this.screenName = screenName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
