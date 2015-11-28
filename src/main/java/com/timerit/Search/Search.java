package com.timerit.Search;

import com.timerit.device.Device;
import com.timerit.keyword.Keyword;
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
public class Search {
    @Id
    @GeneratedValue
    private Long id;

    private String keyworddata; // 검색결과
    private String url;
    private Integer rank;
    private Date searched;
    private Integer battery;
    private String comments;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="keywordid")
    private Keyword keyword;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="deviceid")
    private Device device;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registed;
}
