package com.kwok.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
	
	// 应用ID
	public static String app_id = "";
	
	// 商户应用私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "";
	
	// 支付宝公钥，对应APPID下的支付宝公钥。
    public static String alipay_public_key = "";
    
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
	
	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "";	
	
	// 支付宝日志目录
	public static String log_path = "D:\\log\\";
	
	
    public static void log(String logStr) {
    	
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(logStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
