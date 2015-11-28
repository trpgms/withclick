package com.timerit.keyword;


import com.timerit.device.Device;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by trpgm on 2015-11-28.
 */
@Entity
@Getter
@Setter
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    private String url;

    private Integer priority;

    private Integer termsec;

    private String desc;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="deviceid")
    private Device device;
}
