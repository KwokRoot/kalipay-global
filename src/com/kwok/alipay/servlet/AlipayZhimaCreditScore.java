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
import com.alipay.api.request.ZhimaCreditScoreGetRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.ZhimaCreditScoreGetResponse;
import com.kwok.alipay.config.AlipayConfig;
import com.kwok.alipay.util.CommonUtil;

@WebServlet(description = "芝麻信用评分", urlPatterns = { "/Alipay/ZhimaCreditScore" })
public class AlipayZhimaCreditScore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getQueryString());
		
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
		alipaySystemOauthTokenRequest.setCode(request.getParameter("auth_code"));
		alipaySystemOauthTokenRequest.setGrantType("authorization_code");
		String accessToken = null;
		
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
		    System.out.println(JSON.toJSON(oauthTokenResponse));
		    accessToken = oauthTokenResponse.getAccessToken();
		} catch (AlipayApiException e) {
		    e.printStackTrace();
		}
		
		ZhimaCreditScoreGetRequest zhimaCreditScoreGetRequest = new ZhimaCreditScoreGetRequest();
		zhimaCreditScoreGetRequest.setBizContent("{" +
		" \"transaction_id\":" + CommonUtil.getOrderId() +"," +
		" \"product_code\":\"w1010100100000000001\"" +
		" }");

		try {
			ZhimaCreditScoreGetResponse zhimaCreditScoreGetResponse = alipayClient.execute(zhimaCreditScoreGetRequest,accessToken);
			if(zhimaCreditScoreGetResponse.isSuccess()){
			    System.out.println("****** 调用成功 ******");
			    System.out.println(JSON.toJSON(zhimaCreditScoreGetResponse));
			    System.out.println("芝麻分=" + zhimaCreditScoreGetResponse.getZmScore());
			    response.getWriter().append("您的芝麻信用分为：" + zhimaCreditScoreGetResponse.getZmScore() + "可以享受免押金租房！");
			} else {
			    System.out.println("****** 调用失败 ******");
			    System.out.println("查询芝麻分错误 code=" + zhimaCreditScoreGetResponse.getCode());
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
