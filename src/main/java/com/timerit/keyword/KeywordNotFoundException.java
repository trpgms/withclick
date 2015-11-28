package com.timerit.keyword;

/**
 * Created by trpgm on 2015-11-28.
 */
public class KeywordNotFoundException extends RuntimeException {
    Long id;
    public KeywordNotFoundException(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
