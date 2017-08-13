package org.bj.samples.bookmarks.endpoints;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Bartosz Jedrzejczak on 02/03/2017.
 */

@Entity
public class Bookmark {

    @JsonIgnore
    @ManyToOne
    public Account account;

    @Id
    @GeneratedValue
    public  Long id;

    public String uri;
    public String description;


    Bookmark(){
        //jpa only
    }

    public Bookmark(Account account, String uri, String description) {
        this.account = account;
        this.uri = uri;
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }
}
