package com.kwok.alipay.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Alipay")
public class Alipay extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.getRequestDispatcher("/Alipay/OAuth").forward(request, response);
		//request.getRequestDispatcher("/Alipay/UserInfoShare").forward(request, response);
		//request.getRequestDispatcher("/Alipay/ZhimaCreditScore").forward(request, response);
		request.getRequestDispatcher("/Alipay/ZhimaCreditScoreBrief").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
