package com.timerit.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author trpgms
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
