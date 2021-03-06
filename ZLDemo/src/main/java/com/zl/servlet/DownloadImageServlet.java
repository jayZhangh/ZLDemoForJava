package com.zl.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class DownloadImageServlet
 */
@WebServlet("/DownloadImageServlet")
public class DownloadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD = "/Users/jayZhang/Desktop/upload";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadImageServlet() {
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
		String url = UPLOAD + File.separator + fileName;
		if (fileName.trim().length() <= 0 || !new java.io.File(url).exists()) {
//			response.getWriter().print("0");
			new java.io.File(UPLOAD).mkdir();
			return;
		}
		
		SmartUpload smartUpload = new SmartUpload();
		smartUpload.initialize(getServletConfig(), request, response);
		smartUpload.setContentDisposition(null);
		
		try {
			
			smartUpload.downloadFile(url);
		} catch (ServletException | IOException | SmartUploadException e) {
			e.printStackTrace();
		}
	}

}
