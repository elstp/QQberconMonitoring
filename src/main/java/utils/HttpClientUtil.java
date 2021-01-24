package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author hp
 * 通过httpclient 请求地址页面
 *
 */
public class HttpClientUtil{
    private static final String osName = System.getProperty("os.name").toLowerCase();
    private static final String DEFAULT_WINDOWS_REQUESTER_HEADER_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8";
    private static final String DEFAULT_LINUX_REQUESTER_HEADER_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17";
    private static final Logger logger=LoggerFactory.getLogger(HttpClientUtil.class);
    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
    private RequestBuilder requestBuilder=null;
    private Charset charset=Charset.forName("utf-8");
    private String authorization=null;
    private String contentType=null;

    public HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        return new HttpClientUtil();
    }

    public HttpClientRequest get(String url) {
        requestBuilder = RequestBuilder.get().setUri(url).setConfig(requestConfig);
        return new HttpClientRequest();
    }

    public HttpClientRequest delete(String url) {
        requestBuilder = RequestBuilder.delete().setUri(url).setConfig(requestConfig);
        return new HttpClientRequest();
    }

    public HttpClientRequest put(String url) {
        requestBuilder = RequestBuilder.put().setUri(url).setConfig(requestConfig);
        return new HttpClientRequest();
    }

    public HttpClientUtil setAuthorization(String userName, String password) {
        this.authorization="Basic "+Base64.getEncoder().encodeToString(new String(userName+":"+password).getBytes(charset));
        return this;
    }

    public HttpClientUtil setContentType(String contentType) {
        this.contentType=contentType;
        return this;
    }

    public HttpClientUtil setCharset(Charset charset) {
        this.charset=charset;
        return this;
    }

    public class HttpClientRequest {
        private Integer retry = 2;
        private String reponseContent;
        private BasicCookieStore cookieStore = new BasicCookieStore();

        public HttpClientRequest() {
            request();
        }

        private void request() {
            String url = requestBuilder.getUri().toString();
            logger.info("请求地址url-->" + url);
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setRedirectStrategy(new LaxRedirectStrategy()).build();
            try {
                requestBuilder.setCharset(charset);
                HttpUriRequest request = requestBuilder.build();
                if (osName.indexOf("window") >= 0) {
                    request.setHeader("User-Agent", DEFAULT_WINDOWS_REQUESTER_HEADER_USER_AGENT);
                } else {
                    request.setHeader("User-Agent", DEFAULT_LINUX_REQUESTER_HEADER_USER_AGENT);
                }
                if (authorization != null) {
                    request.setHeader("Authorization", authorization);
                }
                if (contentType != null) {
                    request.setHeader("Content-type", contentType);
                }
                CloseableHttpResponse httpResponse = httpClient.execute(request);
                reponseContent = EntityUtils.toString(httpResponse.getEntity(), charset);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                logger.info("请求地址[ " + url + " ]异常");
            } catch (SocketTimeoutException e) { // 如果出现超时的异常就重试
                if (--retry > 0) {
                    logger.error("请求地址[ " + url + " ]异常--进行重试次:" + retry, e);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    request();
                }
            } catch (IOException e) {
                logger.error("请求地址[ " + url + " ]异常", e);
            } finally {
                if (httpClient != null) {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        logger.error("httpClient.close() 异常", e);
                    }
                }
            }
        }


        public String getReponseContent() {
            return reponseContent;
        }

        public List<Cookie> getCookies() {
            return cookieStore.getCookies();
        }

    }

    public HttpClientRequest post(String url,String requestBody) {
        return post(url, null, new StringEntity(requestBody,charset));
    }


    public HttpClientRequest post(String url,Map<String, String> formData) {
        return post(url, formData, null);
    }

    private HttpClientRequest post(String url, Map<String, String> formData,HttpEntity httpEntity) {
        requestBuilder = RequestBuilder.post().setUri(url).setConfig(requestConfig);
        if (formData != null && !formData.isEmpty()) {
            Set<String> ks = formData.keySet();
            for (String k : ks) {
                requestBuilder.addParameter(k, formData.get(k));
            }
        }
        if(httpEntity!=null) {
            requestBuilder.setEntity(httpEntity);
        }
        return new HttpClientRequest();
    }
}