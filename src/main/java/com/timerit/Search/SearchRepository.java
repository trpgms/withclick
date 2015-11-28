package com.timerit.Search;

import com.timerit.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by trpgm on 2015-11-29.
 */
public interface SearchRepository  extends JpaRepository<Search,Long> {
    Search findTopByDeviceOrderByRegistedDesc(Device device);
}
