package com.timerit.keyword;

import com.timerit.device.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by trpgm on 2015-11-28.
 */
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findTopByDeviceOrderByPriorityDesc(Device device);
    Keyword findTopByDeviceOrderByPriorityAsc(Device device);
    Keyword findTopByDeviceAndPriorityOrderByPriorityAsc(Device device, Integer priority);
    Page<Keyword> findByDeviceOrderByPriorityAsc(Device device, Pageable pageable);
}
