package net.bitnine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.bitnine.service.PropertiesService;

/**
 * 관리자가 사용자의 query limit를 설정하는 컨트롤러
 * @Author  : 김형우
 * @Date	  : 2017. 8. 1.
 *
 */
@RestController
public class ProperitesController {

    @Autowired PropertiesService propertiesService;

    @RequestMapping(value="/api/v1/db/tokenValidTime", method=RequestMethod.POST)
    public String setMax(String tokenTime) {
        String max = propertiesService.getSetMax();
        
        propertiesService.setSetMax(tokenTime);
        
        if ( !tokenTime.equals(propertiesService.getSetMax()) ) {
            return "Set Max Failed";
        }
        return "Set Max Success";
    }
    
}
