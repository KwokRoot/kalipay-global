package com.kwok.alipay.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaMerchantSingleDataUploadRequest;
import com.alipay.api.response.ZhimaMerchantSingleDataUploadResponse;
import com.kwok.alipay.config.AlipayConfig;

@WebServlet(urlPatterns = "/Alipay/ZhimaMerchantDataUpload",description = "单条数据传入")
public class ZhimaMerchantDataUpload extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		
		ZhimaMerchantSingleDataUploadRequest zhimaMerchantSingleDataUploadRequest = new ZhimaMerchantSingleDataUploadRequest();
		zhimaMerchantSingleDataUploadRequest.setBizContent("{" +
		"\"biz_ext_params\":\"{'extparam1':'value1'}\"," +
		"\"data\":\"{'field1':'value1','field2':'value2'}\"," +
		"\"primary_keys\":\"order_no,pay_month\"," +
		"\"scene_code\":\"8\"" +
		"  }");
		
		try {
			ZhimaMerchantSingleDataUploadResponse zhimaMerchantSingleDataUploadResponse = alipayClient.execute(zhimaMerchantSingleDataUploadRequest);
			if (zhimaMerchantSingleDataUploadResponse.isSuccess()) {
				System.out.println("****** 调用成功 ******");
			} else {
				System.out.println("****** 调用失败 ******");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}
	
}
