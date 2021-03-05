package Sign.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author 邝明山
 * on 2021/3/5 9:15
 */
@Service
public class HttpClient {
    public String client(String url, HttpMethod method, MultiValueMap<String,String> params){
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }
}
