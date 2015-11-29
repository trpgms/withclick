package com.timerit.authtoken;

import com.timerit.device.Device;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by trpgm on 2015-11-29.
 */

@Entity
@Getter
@Setter
public class Authtoken {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String tokenvalue;

    @OneToOne
    @JoinColumn(name = "deviceid")
    private Device device;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registed;
}
