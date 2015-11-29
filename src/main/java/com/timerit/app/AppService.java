package com.timerit.app;

import com.timerit.Search.Search;
import com.timerit.Search.SearchRepository;
import com.timerit.accounts.Account;
import com.timerit.accounts.AccountNotFoundException;
import com.timerit.accounts.AccountRepository;
import com.timerit.authtoken.AuthTokenExpiredExcetion;
import com.timerit.authtoken.AuthTokenNotFoundExcetion;
import com.timerit.authtoken.Authtoken;
import com.timerit.authtoken.AuthtokenRepository;
import com.timerit.device.Device;
import com.timerit.device.DeviceNotFoundException;
import com.timerit.device.DeviceRepository;
import com.timerit.keyword.Keyword;
import com.timerit.keyword.KeywordNotFoundException;
import com.timerit.keyword.KeywordRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

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
    private AuthtokenRepository authtokenRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AppDto.AuthResult auth(AppDto.Auth dto) {
        Account account = accountRepository.findByUsername(dto.getUsername());
        if (account == null) {
            throw new AccountNotFoundException(dto.getUsername());
        }
        Device device = deviceRepository.findByOwnerAndLicencekey(account,dto.getLicencekey());
        if(device == null) {
            throw new DeviceNotFoundException(dto.getLicencekey());
        }
        Date now = new Date();
        if(now.after(device.getExpired())) {
            throw new LicenceExpiredException(device.getExpired(),dto.getLicencekey());
        }

        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        Authtoken authtoken = new Authtoken();
        authtoken.setTokenvalue(Base64.getEncoder().encodeToString(bb.array()));
        authtoken.setRegisted(now);
        authtoken.setDevice(device);
        DateTime expired = new DateTime(now);
        authtoken.setExpired(expired.plusHours(1).toDate());
        Authtoken result = authtokenRepository.save(authtoken);

        AppDto.AuthResult authResult = new AppDto.AuthResult();
        authResult.setTokenvalue(result.getTokenvalue());
        authResult.setExpired(result.getExpired());

        return authResult;
    }
    public AppDto.Result open(AppDto.Open dto) {
        Authtoken authtoken = authtokenRepository.findByTokenvalue(dto.getTokenvalue());
        if(authtoken == null) {
            throw new AuthTokenNotFoundExcetion(dto.getTokenvalue());
        }
        Device device = authtoken.getDevice();
        if(device == null) {
            throw new DeviceNotFoundException(device.getLicencekey());
        }
        Date now = new Date();
        if(now.after(device.getExpired())) {
            throw new LicenceExpiredException(device.getExpired(),device.getLicencekey());
        }
        if(now.after(authtoken.getExpired())) {
            throw new AuthTokenExpiredExcetion(dto.getTokenvalue());
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

        result.setKeywordid(keyword.getId());
        result.setSearchword(keyword.getSearchword());
        result.setUrl(keyword.getUrl());
        result.setWaitopen(keyword.getWaitopen());
        result.setWaitsearch(keyword.getWaitsearch());
        result.setDescription(keyword.getDescription());
        return result;
    }

}
