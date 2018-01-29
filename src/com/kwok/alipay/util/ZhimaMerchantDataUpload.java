package com.kwok.alipay.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaMerchantDataUploadInitializeRequest;
import com.alipay.api.response.ZhimaMerchantDataUploadInitializeResponse;
import com.kwok.alipay.config.AlipayConfig;

/**
 * 芝麻数据传入初始化：获取数据反馈行业模板
 * @author Kwok
 */
public class ZhimaMerchantDataUpload {

	public static void main(String[] args) {
		
		// [1] 构建支付宝客户端。
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, "RSA2");
		
		// [2] 构建数据传入初始化请求。
        ZhimaMerchantDataUploadInitializeRequest zhimaMerchantDataUploadInitializeRequest = new ZhimaMerchantDataUploadInitializeRequest();
        zhimaMerchantDataUploadInitializeRequest.setBizContent("{" + "    \"scene_code\":\"3\"" + "  }");
        
        // [3] 数据传入初始化。
        try {
            ZhimaMerchantDataUploadInitializeResponse response = alipayClient.execute(zhimaMerchantDataUploadInitializeRequest);
            if (response.isSuccess()) {
            	System.out.println(JSON.toJSON(response));
            	System.out.println(response.getTemplateUrl());
                System.out.println("调用成功!");
            } else {
                System.out.println("调用失败!");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
		
	}

}
