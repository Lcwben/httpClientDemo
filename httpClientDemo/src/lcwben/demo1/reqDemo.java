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
		/** �������ӹ���������������ز��� */
		//���ӹ�������ʹ���޲ҹ���
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		/** �������������	*/
		//���������
		connManager.setMaxTotal(200);
		//Ĭ�ϵ�ÿ��·�ɵ����������
		connManager.setDefaultMaxPerRoute(50);
		//���õ�ĳ��·�ɵ�������������Ḳ��defaultMaxPerRoute
		//connManager.setMaxPerRoute(new HttpRoute(new HttpHost("www.baidu.com", 80)), 150);

		/** socket���ã�Ĭ������ �� ĳ��host�����ã�*/
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true) //�Ƿ������������ݣ�����Ϊtrue��ر�Socket���壬Ĭ��Ϊfalse
				.setSoReuseAddress(true) //�Ƿ������һ�����̹ر�Socket�󣬼�ʹ����û���ͷŶ˿ڣ��������̻������������ö˿�
				.setSoTimeout(500) //�������ݵĵȴ���ʱʱ�䣬��λms
				.setSoLinger(60) //�ر�Socketʱ��Ҫô�������������ݣ�Ҫô�ȴ�60s�󣬾͹ر����ӣ���ʱsocket.close()��������
				.setSoKeepAlive(true) //��������TCP�����Ƿ���Ч
				.build();
		connManager.setDefaultSocketConfig(socketConfig);
		//connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);

		/** request����������� */
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(2 * 1000) //���ӳ�ʱʱ��
				.setSocketTimeout(2 * 1000) //����ʱʱ�䣨�ȴ����ݳ�ʱʱ�䣩
				.setConnectionRequestTimeout(500) //�ӳ��л�ȡ���ӳ�ʱʱ��
				.setStaleConnectionCheckEnabled(true) //����Ƿ�Ϊ�¾ɵ����ӣ�Ĭ��Ϊtrue������testOnBorrow
				.build();

		closeableClient = HttpClients.custom().setConnectionManager(connManager) //���ӹ�����
				.setDefaultRequestConfig(defaultRequestConfig) //Ĭ����������
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
     * ���� get���� 
     */  
    public void get() {
        //CloseableHttpClient httpclient = HttpClients.createDefault();
       
            // ����httpGet����
        	
            HttpGet httpGet = new HttpGet("http://www.baidu.com/");

		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setConnectTimeout(2 * 1000) //���ӳ�ʱʱ��
				.setSocketTimeout(2 * 1000) //����ʱʱ�䣨�ȴ����ݳ�ʱʱ�䣩
				.setConnectionRequestTimeout(500) //�ӳ��л�ȡ���ӳ�ʱʱ��
				.setStaleConnectionCheckEnabled(true) //����Ƿ�Ϊ�¾ɵ����ӣ�Ĭ��Ϊtrue������testOnBorrow
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
            	//��ȡresponse����
				response = closeableClient.execute(httpGet);
				//��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				StatusLine status = response.getStatusLine();
				//��Ӧ״̬
//				System.out.println("��Ӧ״̬��" + status.toString());
//				if(status.toString().contains("404")) {
//					System.out.println("url�����ɹ�������");
//				}
//				if(entity != null) {
//					// ��ӡ��Ӧ���ݳ���
//                    System.out.println("Response content length: " + entity.getContentLength());
//                    // ��ӡ��Ӧ����
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
