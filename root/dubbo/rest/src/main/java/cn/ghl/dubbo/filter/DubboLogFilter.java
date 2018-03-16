package cn.ghl.dubbo.filter;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/13/2018
 */
public class DubboLogFilter implements Filter {

    private static Logger LOG = LoggerFactory.getLogger(DubboLogFilter.class);

    private String KEY_NAME = "event";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        LOG.debug("invoker {}", invoker.getInterface().getName());
        LOG.debug("invocation {} {}", invocation.getArguments(), invocation.getMethodName());
        HttpServletRequest request = (HttpServletRequest) RpcContext.getContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) RpcContext.getContext().getResponse();
        LOG.debug("request={}, response={}", request, response);
        LOG.debug("request key={}, response key={}", request == null ? null : request.getHeader(KEY_NAME),
            response == null ? null : response.getHeader(KEY_NAME));

        String event;
        if (request.getHeader(KEY_NAME) == null || request.getHeader(KEY_NAME).length() == 0) {
            event = UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            event = request.getHeader(KEY_NAME);
        }

        MDC.put(KEY_NAME, event);
        response.setHeader(KEY_NAME, event);

        Result result = invoker.invoke(invocation);
        return result;
    }
}
