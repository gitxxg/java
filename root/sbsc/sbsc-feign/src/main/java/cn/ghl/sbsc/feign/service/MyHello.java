package cn.ghl.sbsc.feign.service;

import cn.ghl.sbsc.api.ShowTime;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/28/2018
 */

@FeignClient(value = "service-hi")
public interface MyHello extends ShowTime {

}
