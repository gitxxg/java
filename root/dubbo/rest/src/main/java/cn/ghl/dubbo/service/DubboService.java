package cn.ghl.dubbo.service;

import cn.ghl.dubbo.pojo.DubboMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 3/12/2018
 */
@Path("/dubbo")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface DubboService {

    @POST
    @Path("/uplink")
    Response uplink(DubboMessage message);

    @GET
    @Path("{id}")
    Response getMessage(@PathParam("id") String id);

    @GET
    @Path("/status/{vin}")
    Response getStatus(@PathParam("vin") String vin, @QueryParam("userId") String userId,
        @QueryParam("target") String target, @QueryParam("latest") String latest,
        @QueryParam("source") String source, @QueryParam("collectInterval") Long collectInterval,
        @QueryParam("uploadInterval") Long uploadInterval, @QueryParam("startTime") Long startTime,
        @QueryParam("endTime") Long endTime);

}
