package cn.ghl.dubbo.filter;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/13/2018
 */
public class ConsumerLogFilter implements ClientResponseFilter {

    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {

    }
}
