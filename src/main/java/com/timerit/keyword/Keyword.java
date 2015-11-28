package com.timerit.keyword;


import com.timerit.Search.Search;
import com.timerit.device.Device;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    private String searchword;

    private String url;

    private Integer priority;

    private Integer waitopen;

    private Integer waitsearch;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="deviceid")
    private Device device;

    @OneToMany(mappedBy="keyword")
    private List<Search> searches;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registed;
}
