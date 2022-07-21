package com.zl.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadImage2Servlet
 */
@WebServlet("/DownloadImage2Servlet")
public class DownloadImage2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD = "/Users/jayZhang/Desktop/upload";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadImage2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!new java.io.File(UPLOAD).exists()) {
			new java.io.File(UPLOAD).mkdir();
		}
		
		String fileName = request.getParameter("file");
		String path = UPLOAD + File.separator + fileName;
		if (fileName.trim().length() <= 0 || !new java.io.File(path).exists()) {
			System.out.println("Not Found!");
//			response.getWriter().write("0");
			return;
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//		response.setContentType("image/jpeg");
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
		ServletOutputStream outputStream = response.getOutputStream();
		byte[] arr = new byte[1024];
		int len;
		while ((len = inputStream.read(arr)) != -1) {
			outputStream.write(arr, 0, len);
		}
		
		inputStream.close();
		outputStream.close();
	}

}
