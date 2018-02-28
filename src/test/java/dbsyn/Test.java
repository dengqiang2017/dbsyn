package dbsyn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

public class Test {

//	public static void main(String[] args) {
//		//transient
//		//什么是序列化
//		List<Map<String, Object>> list=new ArrayList<>();
//		LinkedList<Map<String, Object>> link=new LinkedList<>();
//		//Collections.sort(list);
//		Hashtable<String, Object> table=new Hashtable<>();// put是value不能为null
//		Map<String, Object> map=new HashMap<String, Object>();// 
//		//锁分段技术,读是否要加锁,迭代器是 强一致性的迭代器的迭代器 还是 弱一致性的迭代器
//		ConcurrentHashMap<String, Object> con=new ConcurrentHashMap<>();// put是value不能为null
//		HashSet<String> set=new HashSet<>();
//		CopyOnWriteArrayList<String> coal=new CopyOnWriteArrayList<>();
//		Queue<String> q;//=new Queue<String>() { };
//		//
//		String s="";
//		s.hashCode();//将字符串分解为char[] val,循环val, h = 31 * h + val[i];
//		s.equals("");//将字符串分解为char[]单个字符进行比较
//		//多线程的40个问题
//		synchronized (s) {}
//		ReentrantLock lock=new ReentrantLock();
//		AtomicInteger atomicInteger=new AtomicInteger();//CAS机制,
//		//线程池的实现原理
//		Object obj=new Object();//方法以及方法的作用
//		//union和union all的区别
//		//left join
//		//几种索引的区别
//		//sql优化
//		//二叉查找数
//		//AVL树,红黑树,平衡树
//		//session的几种实现和实现原理
//		//session和cookie的区别和联系
//		//
//		Filter filter=new Filter() {
//			@Override
//			public void init(FilterConfig filterConfig) throws ServletException {}
//			@Override
//			public void doFilter(ServletRequest request, ServletResponse response,
//					FilterChain chain) throws IOException, ServletException {}
//			@Override
//			public void destroy() {}
//		};
//		Servlet servlet=new Servlet() {
//			@Override
//			public void service(ServletRequest req, ServletResponse res)
//					throws ServletException, IOException {}
//			@Override
//			public void init(ServletConfig config) throws ServletException {}
//			@Override
//			public String getServletInfo() {return null;}
//			@Override
//			public ServletConfig getServletConfig() {return null;}
//			@Override
//			public void destroy() {}
//		};
//		
//		Listener listener;//=new Listener() {};
//		//get/post的区别
//		//forward/重定向的区别
//		//SOP和RPC
//	}
	
	public static void main(String[] args) {
//		Test test=new Test();
		String url="http://10.18.2.76:8015/SPDJ/PutMeetingInfoToDB";
		JSONObject json=new JSONObject();
		json.put("WJID", "test_123456");
		json.put("HYID", "test_456123");
		json.put("KSSJ", "2018-01-16 12:12:12");
		json.put("JSSJ", "2018-01-16 14:12:12");
		json.put("HYMC", "中石化会议");
		json.put("CHRYIDLB", "[123,123,234]");
		json.put("HYFQR", "hubin.xnyq");
		json.put("HYYPDZ", "http://10.18.2.76/test/audio");
		json.put("HYSPDZ", "http://10.18.2.76/test/video");
		json.put("YPMD5", "YPMD5");
		json.put("SPMD5", "SPMD5");
		json.put("SPWJSFCZ", false);
		json.put("YPWJSFCZ", true);
		json.put("SPWJSFRK", false);
		json.put("YPWJSFRK", true);
		json.put("BZ", "test_备注");
//		test.postData(url, json);
		try {
			System.out.println(url+"?szInfo="+json.toString());
			SendGet(url+"?szInfo="+URLEncoder.encode(json.toString(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//string WJID;                                    //文件ID
        //float HYID;                                     //会议ID
        //DateTime KSSJ;                                  //开始时间
        //DateTime JSSJ;                                  //会议结束时间
        //string HYMC;                                    //会议名称
        //List<string> CHRYIDLB;                          //参会人员ID
        //string HYFQR;                                   //发起会议人员
        //string HYSPDZ;                                  //会议视频地址   没有就为空
        //string HYYPDZ;                                  //会议音频地址   没有就为空
        //string SPMD5;                                   //视频MD5码   没有就为空
        //string YPMD5;                                   //音频MD5码   没有就为空
        //bool SPWJSFCZ;                                  //视频文件是否入库
        //bool YPWJSFCZ;                                  //音频文件是否入库
        //bool SPWNSFRK;                                  //音频文件是否入库
        //bool YPWNSFRK;                                  //视频文件是否入库
        //string BZ;                                      //备注
	}
	public static String SendGet(String url) {
		String result = "";
		try {
			System.out.println(url);
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection();
			httpConn.setDoInput(true);
			httpConn.setReadTimeout(30*1000);  
			httpConn.setRequestMethod("GET");
			BufferedReader readin = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			String CurLine;
			while ((CurLine = readin.readLine()) != null) {
				result += CurLine;
			}
			readin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
//	public static void main(String[] args) {
	/*	//队列 先进先出
		Queue<String> queue=new LinkedList<>();
		//添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("poll="+queue.poll()); //返回第一个元素，并在队列中删除
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("element="+queue.element()); //返回第一个元素 
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("peek="+queue.peek()); //返回第一个元素 
        for(String q : queue){
            System.out.println(q);
        }
        
        AtomicInteger count = new AtomicInteger();
        count.incrementAndGet();//自增长
        */
//	}
	
	public String postData(String url, JSONObject json) {
		try {
			// 创建连接
//			SSLContext sc = SSLContext.getInstance("TLSv1");
//			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
//					new java.security.SecureRandom());
			URL httpurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpurl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			// connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "UTF-8");
//			connection.setSSLSocketFactory(sc.getSocketFactory());
//			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			System.out.println("szInfo="+json.toString());
			out.write(("szInfo="+json.toString()).getBytes("UTF-8"));
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String lines;
			StringBuffer result = new StringBuffer();
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
			
			System.out.println(result);
			 
			reader.close();
			// 断开连接
			connection.disconnect();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public JSONObject httppost( String requestUrl,Map<String, Object> requestParamsMap) {
		PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;
        // BufferedReader bufferedReader = null;  
        StringBuffer responseResult = new StringBuffer();  
        StringBuffer params = new StringBuffer();  
        HttpURLConnection httpURLConnection = null;  
        // 组织请求参数  
        Iterator<Entry<String, Object>> it = requestParamsMap.entrySet().iterator();  
        while (it.hasNext()) {
            Entry<String, Object> element =  it.next();  
            params.append(element.getKey());  
            params.append("=");
            params.append(element.getValue());  
            params.append("&");  
        }  
        if (params.length() > 0) {  
            params.deleteCharAt(params.length() - 1);  
        }  
        try {  
            URL realUrl = new URL(requestUrl);  
            // 打开和URL之间的连接  
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();  
            // 设置通用的请求属性  
            httpURLConnection.setRequestProperty("accept", "*/*");  
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");  
            httpURLConnection.setRequestProperty("Content-Length", String  
                    .valueOf(params.length()));
            // 发送POST请求必须设置如下两行  
            httpURLConnection.setDoOutput(true);  
            httpURLConnection.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());  
            // 发送请求参数  
            printWriter.write(params.toString());  
            // flush输出流的缓冲  
            printWriter.flush();  
            // 根据ResponseCode判断连接是否成功  
            int responseCode = httpURLConnection.getResponseCode();  
            if (responseCode != 200) {  
            }
            // 定义BufferedReader输入流来读取URL的ResponseData  
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    httpURLConnection.getInputStream()));  
            String line;  
            while ((line = bufferedReader.readLine()) != null) {  
                responseResult.append(line);  
            }  
            System.out.println(line);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
            try {  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        System.out.println(responseResult);
        return JSONObject.fromObject(responseResult.toString());
	}
	
}
