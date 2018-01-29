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
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.kwok.alipay.config.AlipayConfig;

/**
 * 商户对开发者进行应用授权后，开发者可以帮助商户完成相应的业务逻辑。
 * @author Kwok
 */
@WebServlet(urlPatterns = "/Alipay/OAuth", description="第三方应用授权")
public class AlipayOAuth extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getQueryString());
		
		String app_auth_code = request.getParameter("app_auth_code");
		String app_auth_token = null;
		
		/*用户授权*/
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		AlipayOpenAuthTokenAppRequest alipayReq = new AlipayOpenAuthTokenAppRequest();
		
		alipayReq.setBizContent("{" +
		"    \"grant_type\":\"authorization_code\"," +
		"    \"code\": \"" + app_auth_code + "\" " +
		"  }");
		
		try {
			AlipayOpenAuthTokenAppResponse alipayRes = alipayClient.execute(alipayReq);
			System.out.println(JSON.toJSON(alipayRes));
			app_auth_token = alipayRes.getAppAuthToken();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		/*用户授权 END*/
		
		
		/*查询授权信息*/
		AlipayOpenAuthTokenAppQueryRequest alipayOpenAuthTokenAppQueryRequest = new AlipayOpenAuthTokenAppQueryRequest();
		alipayOpenAuthTokenAppQueryRequest.setBizContent("{" +
		"    \"app_auth_token\":" + app_auth_token +
		"  }");
		try {
			AlipayOpenAuthTokenAppQueryResponse alipayOpenAuthTokenAppQueryResponse = alipayClient.execute(alipayOpenAuthTokenAppQueryRequest);
			System.out.println(JSON.toJSON(alipayOpenAuthTokenAppQueryResponse));
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		/*查询授权信息 END*/
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
