package com.timerit.app;

import com.timerit.Search.Search;
import com.timerit.Search.SearchRepository;
import com.timerit.accounts.Account;
import com.timerit.accounts.AccountNotFoundException;
import com.timerit.accounts.AccountRepository;
import com.timerit.device.Device;
import com.timerit.device.DeviceNotFoundException;
import com.timerit.device.DeviceRepository;
import com.timerit.keyword.Keyword;
import com.timerit.keyword.KeywordNotFoundException;
import com.timerit.keyword.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by trpgm on 2015-11-28.
 */
@Service
@Transactional
@Slf4j
public class AppService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AppDto.Result open(AppDto.Open dto) {
        Account account = accountRepository.findByUsername(dto.getUsername());
        if (account == null) {
            throw new AccountNotFoundException(dto.getUsername());
        }
        Device device = deviceRepository.findByOwnerAndLicencekey(account,dto.getLicencekey());
        if(device == null) {
            throw new DeviceNotFoundException(dto.getLicencekey());
        }
        Date now = new Date();
        if(now.before(device.getExpired())) {
            throw new LicenceExpiredException(device.getExpired(),dto.getLicencekey());
        }
        // 결과저장
        // 검색결과
        if(dto.getSearched() != null || dto.getRank() != null) {
            Keyword searchedkeyword = keywordRepository.findOne(dto.getKeywordid());
            Search search = new Search();
            search.setKeyworddata(dto.getSearchword());
            search.setUrl(dto.getUrl());
            search.setRank(dto.getRank());
            search.setSearched(dto.getSearched());
            search.setBattery(dto.getBattery());
            search.setComments(dto.getComments());
            search.setKeyword(searchedkeyword);
            search.setDevice(device);
            search.setRegisted(new Date());
            searchRepository.save(search);
        }

        // 검색 키워드 가져오기
        Keyword keyword = null;
        Search lastSearch = searchRepository.findTopByDeviceOrderByRegistedDesc(device);
        if(lastSearch == null) {
            keyword = keywordRepository.findTopByDeviceOrderByPriorityAsc(device);
        } else {
            keyword = keywordRepository.findTopByDeviceAndPriorityOrderByPriorityAsc(device,lastSearch.getKeyword().getPriority()+1);
        }
        if(keyword == null) {
            keyword = keywordRepository.findTopByDeviceOrderByPriorityAsc(device);
        }
        if(keyword == null) {
            throw new KeywordNotFoundException(device.getId());
        }

        AppDto.Result result = new AppDto.Result();

        result.setUsername(account.getUsername());
        result.setStatus(account.getStatus());
        result.setEmail(account.getEmail());

        result.setExpired(device.getExpired());
        result.setLicencekey(device.getLicencekey());


        result.setSearchword(keyword.getSearchword());
        result.setUrl(keyword.getUrl());
        result.setWaitopen(keyword.getWaitopen());
        result.setWaitsearch(keyword.getWaitsearch());
        result.setDescription(keyword.getDescription());
        return result;
    }

}
