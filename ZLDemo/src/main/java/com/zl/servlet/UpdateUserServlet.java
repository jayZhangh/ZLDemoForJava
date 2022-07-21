package com.zl.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.zl.service.UserService;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD = "/Users/jayZhang/Desktop/upload";   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File existsFile = new File(UPLOAD);
		if (!existsFile.exists()) {
			existsFile.mkdir();
		}
		
		SmartUpload smartUpload = new SmartUpload();
		smartUpload.initialize(this.getServletConfig(), request, response);
		// 10MB
		smartUpload.setMaxFileSize(1024 * 1024 * 10);
		// 50MB
		smartUpload.setTotalMaxFileSize(50 * 1024 * 1024);
		// 设定允许上传的文件格式
		smartUpload.setAllowedFilesList("png,jpg,jpeg");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
		
		try {
			smartUpload.upload();
			
			String userId = smartUpload.getRequest().getParameter("userId");
			String userName = smartUpload.getRequest().getParameter("userName");
			String password = smartUpload.getRequest().getParameter("password");
			
			if (userId == null || userId.trim().length() <= 0) {
				return;
			}
			
			String portrait = UserService.getPortrait(userId);
			for (int i = 0; i < smartUpload.getFiles().getCount(); i++) {
				// 获取上传文件的扩展名
				String ext = smartUpload.getFiles().getFile(i).getFileExt();
				if (ext == null || ext.length() <= 0) {
					ext = "png";
				}
				
				String fileName = dateFormat.format(new Date()) + "." + ext;
				String path = UPLOAD + File.separator + fileName;
				if (smartUpload.getFiles().getFile(i).isMissing() == false) {
					smartUpload.getFiles().getFile(i).saveAs(path);
					UserService.updateUser(userId, userName, password, fileName);
					File file = new File(UPLOAD + File.separator + portrait);
					if (file.exists()) {
						file.delete();
					}
					
				} else {
					UserService.updateUser(userId, userName, password, portrait);
				}
			}

			response.sendRedirect("http://localhost:8080/ZLDemo/users");
			
		} catch (IOException | SmartUploadException e) {
			e.printStackTrace();
		}
	}

}
