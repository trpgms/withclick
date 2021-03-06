package com.timerit.device;

import com.timerit.accounts.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by trpgm on 2015-11-28.
 */
public interface DeviceRepository extends JpaRepository<Device,Long> {
    Device findByOwnerAndLicencekey(Account owner, String licencekey);
    Page<Device> findByOwnerOrderByExpiredDesc(Account account, Pageable pageable);
}
