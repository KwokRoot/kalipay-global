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
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.kwok.alipay.config.AlipayConfig;

@WebServlet(description = "会员授权信息查询", urlPatterns = { "/Alipay/UserInfoShare" })
public class AlipayUserInfoShare extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getQueryString());
		
		String auth_code = request.getParameter("auth_code");
		String auth_token = null;
		
		/*换取授权访问令牌*/
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
		alipaySystemOauthTokenRequest.setCode(auth_code);
		alipaySystemOauthTokenRequest.setGrantType("authorization_code");
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
		    System.out.println(JSON.toJSON(oauthTokenResponse));
		    auth_token = oauthTokenResponse.getAccessToken();
		} catch (AlipayApiException e) {
		    //处理异常
		    e.printStackTrace();
		}
		/*换取授权访问令牌 END*/
		
		/*支付宝会员授权信息查询*/
		AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();
		AlipayUserInfoShareResponse alipayUserInfoShareResponse;
		try {
			alipayUserInfoShareResponse = alipayClient.execute(alipayUserInfoShareRequest, auth_token);
			if(alipayUserInfoShareResponse.isSuccess()){
				System.out.println(JSON.toJSON(alipayUserInfoShareResponse));
			} else {
				System.out.println("****** 调用失败 ******");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		/*支付宝会员授权信息查询 END*/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
