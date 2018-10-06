package lcwben.demo1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


public class reqDemo {

	private int f1 = 1;

	private static CloseableHttpClient closeableClient;

	static {
		/** 创建连接管理器，并设置相关参数 */
		//连接管理器，使用无惨构造
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		/** 连接数相关设置	*/
		//最大连接数
		connManager.setMaxTotal(200);
		//默认的每个路由的最大连接数
		connManager.setDefaultMaxPerRoute(50);
		//设置到某个路由的最大连接数，会覆盖defaultMaxPerRoute
		//connManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.baidu.com", 80)), 150);

		/** socket配置（默认配置 和 某个host的配置）*/
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true) //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
				.setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
				.setSoTimeout(500) //接收数据的等待超时时间，单位ms
				.setSoLinger(60) //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
				.setSoKeepAlive(true) //开启监视TCP连接是否有效
				.build();
		connManager.setDefaultSocketConfig(socketConfig);
		//connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);

		/** request请求相关配置 */
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(2 * 1000) //连接超时时间
				.setSocketTimeout(2 * 1000) //读超时时间（等待数据超时时间）
				.setConnectionRequestTimeout(500) //从池中获取连接超时时间
				.setStaleConnectionCheckEnabled(true) //检查是否为陈旧的连接，默认为true，类似testOnBorrow
				.build();

		closeableClient = HttpClients.custom().setConnectionManager(connManager) //连接管理器
				.setDefaultRequestConfig(defaultRequestConfig) //默认请求配置
				.build();
		//closeableClient = HttpClients.createDefault();

	}
	
    @Test  
    public void jUnitTest() {

		reqDemo demo = new reqDemo();

		for (int i = 0; i < 100; i++) {
			demo.get();
		}

    }

    /** 
     * 发送 get请求 
     */  
    public void get() {
        //CloseableHttpClient httpclient = HttpClients.createDefault();
       
            // 创建httpGet对象
        	
            HttpGet httpGet = new HttpGet("http://www.baidu.com/");

		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(2 * 1000) //连接超时时间
				.setSocketTimeout(2 * 1000) //读超时时间（等待数据超时时间）
				.setConnectionRequestTimeout(500) //从池中获取连接超时时间
				.setStaleConnectionCheckEnabled(true) //检查是否为陈旧的连接，默认为true，类似testOnBorrow
				.build();
		RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
				.setSocketTimeout(5000)
				.setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.build();
		httpGet.setConfig(requestConfig);


            //System.out.println("executing request " + httpGet.getURI());
            
            CloseableHttpResponse response = null;
			Set<Object> set = new HashSet<Object>();
            try {
            	//获取response对象
				response = closeableClient.execute(httpGet);
				//获取响应实体
				HttpEntity entity = response.getEntity();
				StatusLine status = response.getStatusLine();
				//响应状态
//				System.out.println("响应状态：" + status.toString());
//				if(status.toString().contains("404")) {
//					System.out.println("url反馈成功！！！");
//				}
//				if(entity != null) {
//					// 打印响应内容长度
//                    System.out.println("Response content length: " + entity.getContentLength());
//                    // 打印响应内容
//                    System.out.println("Response content: " + EntityUtils.toString(entity));
//
//				}


				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(httpGet != null) {httpGet.releaseConnection();}
					if(response != null) {response.close();}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            

    }

	public static void main(String[] args) {

		reqDemo demo = new reqDemo();

		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					demo.get();
				}
			}).start();
		}
	}
}
