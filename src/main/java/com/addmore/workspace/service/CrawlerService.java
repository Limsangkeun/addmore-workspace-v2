package com.addmore.workspace.service;

import com.addmore.workspace.entity.Company;
import com.addmore.workspace.repository.CompanyRepository;
import com.addmore.workspace.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CrawlerService {

    private final WebDriver webDriver;
    private final CompanyRepository companyRepository;

    private final String NAVER_MAP_HEADER_ADDR = "https://map.naver.com/v5/api/search?query=";
    private final String NAVER_MAP_TAIL_ADDR = "&page=1&displayCount=300";

    public List<Map<String, Object>> getDataFromWeb(String keyword) {
        String fullNaverAddress = NAVER_MAP_HEADER_ADDR+keyword+NAVER_MAP_TAIL_ADDR;
        List<Map<String, Object>> list = null;
        try {
            webDriver.get(fullNaverAddress);
            String org_pageSource = webDriver.findElement(By.tagName("pre")).getText();
            Map<String, Object> result_pageSource = (Map<String,Object>) Util.fromJson(org_pageSource).get("result");
            log.info(org_pageSource);
            Map<String, Object> placeInfo = (Map<String, Object>) result_pageSource.get("place");
            if(placeInfo == null) {
                return new ArrayList<>();
            }
            list = (List<Map<String, Object>>) placeInfo.get("list");

            for(Map<String, Object> data : list) {
                String tel = (String)data.get("tel");
                if(tel.indexOf("070") != -1) continue;
                Thread.sleep(1000);
                String id = (String)data.get("id");
                webDriver.get("https://m.map.naver.com/search2/site.nhn?style=v5&code="+id);
                boolean is114 = webDriver.getPageSource().indexOf("02-114") == -1 ? false : true;
                data.put("is114", is114);
                int result = insertToNcd(data);
                if(result != 1) {
                    log.error(data.get("id").toString() + " : 위 아이디의 네이버 데이터 삽입/수정에 실패하였습니다.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertToNcd(Map<String, Object> data) {
        int result = 0;
        try {
            Optional<Company> companyOpt = companyRepository.findAllByCodeEquals((String)data.get("id"));
            Company companyEntity = null;
            if(companyOpt.isEmpty()) {

            } else {

            }
            /*c.setCode((String)data.get("id"));
            c.setName((String)data.get("name"));
            c.setAddr((String)data.get("address"));
            c.setRoadAddr((String)data.get("roadAddress"));
            c.setTel((String)data.get("tel"));
            c.setDetailUrl("https://map.naver.com/v5/entry/place/"+(String)data.get("id"));
            c.setMapType(EMapType.NAVER_MAP);*/

            List<String> category = (List<String>)data.get("category");
            /*c.setCategory(String.join(",", category));
            if(data.get("homePage") != null) {
                c.setHomePage((String)data.get("homePage"));
            }*/
            if(data.get("thumUrl") != null && data.get("thumUrl") !="") {
                String thumUrl = (String)data.get("thumUrl");
                String[] thumUrlArr = new String[1];

                if(thumUrl.indexOf("ldb.phinf.naver.net") != -1) {
                    thumUrlArr = thumUrl.split("http://ldb.phinf.naver.net/");
                } else if(thumUrl.indexOf("ldb-phinf.pstatic.net") != -1) {
                    thumUrlArr = thumUrl.split("https://ldb-phinf.pstatic.net/");
                } else if(thumUrl.indexOf("blogfiles.naver.net") != -1) {
                    thumUrlArr = thumUrl.split("http://blogfiles.naver.net/");
                }

                String regDate = thumUrlArr[1].substring(0, 8);
                /*c.setRegDate(LocalDate.of(Integer.parseInt(regDate.substring(0, 4)), Integer.parseInt(regDate.substring(4, 6)), Integer.parseInt(regDate.substring(6, 8))));*/
            }/*
            c.set114((boolean)data.get("is114"));
            crawlerRepository.save(c);*/
            result = 1;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
