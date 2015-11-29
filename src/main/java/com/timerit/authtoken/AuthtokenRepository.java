package com.timerit.authtoken;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by trpgm on 2015-11-29.
 */
public interface AuthtokenRepository extends JpaRepository<Authtoken,Long> {
    Authtoken findByTokenvalue(String tokenvalue);
}
