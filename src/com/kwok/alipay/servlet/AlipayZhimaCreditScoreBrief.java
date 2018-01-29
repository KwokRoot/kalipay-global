package com.kwok.alipay.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.ZhimaCreditScoreBriefGetRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.ZhimaCreditScoreBriefGetResponse;
import com.kwok.alipay.config.AlipayConfig;
import com.kwok.alipay.util.CommonUtil;

@WebServlet(description = "芝麻信用评分普惠版", urlPatterns = { "/Alipay/ZhimaCreditScoreBrief" })
public class AlipayZhimaCreditScoreBrief extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getQueryString());
		String userId = null;
		
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
		alipaySystemOauthTokenRequest.setCode(request.getParameter("auth_code"));
		alipaySystemOauthTokenRequest.setGrantType("authorization_code");
		
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
		    System.out.println(JSON.toJSON(oauthTokenResponse));
		    userId = oauthTokenResponse.getUserId();
		} catch (AlipayApiException e) {
		    e.printStackTrace();
		}
		
		ZhimaCreditScoreBriefGetRequest zhimaCreditScoreBriefGetRequest = new ZhimaCreditScoreBriefGetRequest();
		zhimaCreditScoreBriefGetRequest.setBizContent("{" +
		"\"transaction_id\":" + CommonUtil.getOrderId() +"," + 
		"\"product_code\":\"w1010100000000002733\"," +
		"\"cert_type\":\"ALIPAY_USER_ID\"," +
		"\"cert_no\":" + userId +"," +
		"\"admittance_score\":700" +
		"  }");
		
		try {
			ZhimaCreditScoreBriefGetResponse zhimaCreditScoreBriefGetResponse = alipayClient.execute(zhimaCreditScoreBriefGetRequest);
			if(zhimaCreditScoreBriefGetResponse.isSuccess()){
				System.out.println(JSON.toJSON(zhimaCreditScoreBriefGetResponse));
				System.out.println("****** 调用成功 ******");
				if("Y".equals(zhimaCreditScoreBriefGetResponse.getIsAdmittance())){
					request.setAttribute("returnMsg", "你的芝麻信用分高于700分，可以享受免押金服务！");
					request.getRequestDispatcher("/WEB-INF/jsp/ReturnZhiMa.jsp").forward(request, response);
				}else{
					request.setAttribute("returnMsg", "你的芝麻信用分低于700分，不可以享受免押金服务！");
					request.getRequestDispatcher("/WEB-INF/jsp/ReturnZhiMa.jsp").forward(request, response);
				}
				System.out.println("------------------------------------");
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
