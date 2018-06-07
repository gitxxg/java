package main.java.cn.ghl.tools.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CodingErrorAction;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步的HTTP请求对象，支持post与get方法以及可设置代理
 *
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/11/2018
 */
public class HttpSyncClient {

    private Logger LOG = LoggerFactory.getLogger(HttpSyncClient.class);

    private static int socketTimeout = 1000;// 设置等待数据超时时间5秒钟 根据业务调整

    private static int connectTimeout = 2000;// 连接超时

    private static int poolSize = 3000;// 连接池最大连接数

    private static int maxPerRoute = 1500;// 每个主机的并发最多只有1500

    // proxy代理相关配置
    private String host = "";

    private int port = 0;

    private String username = "";

    private String password = "";

    private CloseableHttpClient httpClient;

    private CloseableHttpClient proxyHttpClient;

    // 应用启动的时候就应该执行的方法
    public HttpSyncClient() {

        this.httpClient = createClient(false);

        this.proxyHttpClient = createClient(true);
    }

    public CloseableHttpClient createClient(boolean proxy) {

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(connectTimeout)
            .setSocketTimeout(socketTimeout).build();

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
            username, password);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
            RegistryBuilder.<ConnectionSocketFactory>
                create().register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager(
            socketFactoryRegistry);

        if (poolSize > 0) {
            conMgr.setMaxTotal(poolSize);
        }

        if (maxPerRoute > 0) {
            conMgr.setDefaultMaxPerRoute(maxPerRoute);
        } else {
            conMgr.setDefaultMaxPerRoute(10);
        }

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8).build();

        Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder
            .<AuthSchemeProvider>create()
            .register(AuthSchemes.BASIC, new BasicSchemeFactory())
            .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
            .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
            .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
            .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
            .build();
        conMgr.setDefaultConnectionConfig(connectionConfig);

        if (proxy) {
            return HttpClients.custom().setConnectionManager(conMgr)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setProxy(new HttpHost(host, port))
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultRequestConfig(requestConfig).build();
        } else {
            return HttpClients.custom().setConnectionManager(conMgr)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setDefaultCookieStore(new BasicCookieStore()).build();
        }

    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public CloseableHttpClient getProxyClient() {
        return proxyHttpClient;
    }

    public String httpGet(String url, List<BasicNameValuePair> parameters) {

        CloseableHttpClient client = getHttpClient();// 默认会到池中查询可用的连接，如果没有就新建
        HttpGet getMethod = null;
        String returnValue = "";
        try {
            getMethod = new HttpGet(url);

            if (null != parameters) {
                String params = EntityUtils.toString(new UrlEncodedFormEntity(
                    parameters));
                getMethod.setURI(new URI(getMethod.getURI().toString() + "?"
                    + params));
                LOG.debug("httpGet-getUrl:{}", getMethod.getURI());
            }

            HttpResponse response = client.execute(getMethod);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity he = response.getEntity();
                returnValue = new String(EntityUtils.toByteArray(he));
                return returnValue;
            }

        } catch (UnsupportedEncodingException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpGet Send Error,Code error:" + e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpGet Send Error,Protocol error:" + e.getMessage());
        } catch (IOException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpGet Send Error,IO error:" + e.getMessage());
        } catch (URISyntaxException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpGet Send Error,IO error:" + e.getMessage());
        } finally {// 释放连接,将连接放回到连接池
            getMethod.releaseConnection();

        }
        return returnValue;

    }

    public String httpPost(String url, List<BasicNameValuePair> parameters,
        String requestBody) {

        CloseableHttpClient client = getHttpClient();// 默认会到池中查询可用的连接，如果没有就新建
        HttpPost postMethod = null;
        String returnValue = "";
        try {
            postMethod = new HttpPost(url);

            if (null != parameters) {
                String params = EntityUtils.toString(new UrlEncodedFormEntity(
                    parameters));
                postMethod.setURI(new URI(postMethod.getURI().toString() + "?"
                    + params));
                LOG.debug("httpPost-getUrl:{}", postMethod.getURI());
            }

            if (StringUtils.isNotBlank(requestBody)) {
                StringEntity se = new StringEntity(requestBody);
                postMethod.setEntity(se);
            }

            HttpResponse response = client.execute(postMethod);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity he = response.getEntity();
                returnValue = new String(EntityUtils.toByteArray(he));
                return returnValue;
            }

        } catch (UnsupportedEncodingException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpPost Send Error,Code error:" + e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpPost Send Error,Protocol error:" + e.getMessage());
        } catch (IOException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpPost Send Error,IO error:" + e.getMessage());
        } catch (URISyntaxException e) {
            LOG.error(Thread.currentThread().getName()
                + "httpPost Send Error,IO error:" + e.getMessage());
        } finally {// 释放连接,将连接放回到连接池
            postMethod.releaseConnection();
            // 释放池子中的空闲连接
            // client.getConnectionManager().closeIdleConnections(30L,
            // TimeUnit.MILLISECONDS);
        }
        return returnValue;

    }
}
