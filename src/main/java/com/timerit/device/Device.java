package com.timerit.device;

import com.timerit.Search.Search;
import com.timerit.accounts.Account;

import com.timerit.keyword.Keyword;
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
public class Device {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String licencekey;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ownerid")
    private Account owner;

    @OneToMany(mappedBy="device")
    private List<Keyword> keywords;

    @OneToMany(mappedBy="device")
    private List<Search> searches;
}
