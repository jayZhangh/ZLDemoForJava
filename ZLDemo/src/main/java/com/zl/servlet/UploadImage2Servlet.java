package com.zl.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet implementation class UploadImage2Servlet
 */
@WebServlet("/UploadImage2Servlet")
public class UploadImage2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD = "/Users/jayZhang/Desktop/upload/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage2Servlet() {
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
		java.io.File existsFile = new java.io.File(UPLOAD);
		if (!existsFile.exists()) {
			existsFile.mkdir();
		}
		
		// 创建磁盘项目工厂
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

        // 创建解析类
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("UTF-8");
        try {
            // 获取请求中的属性
            List<FileItem> list = servletFileUpload.parseRequest(request);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
            // 循环遍历属性中的值
            for (FileItem fileItem : list) {
                // 判断是否为文件属性
                if (fileItem.isFormField()) {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");

                    System.out.println(name + "-" + value);
                } else {
                    // 获取文件的文件名
//                    String fileName = fileItem.getName();
                    String fileName = dateFormat.format(new Date()) + ".png";
                    // 获取文件大小
                    long fileSize = fileItem.getSize();

                    System.out.println("文件名为" + fileName + ",文件大小为" + fileSize);

                    // 获取文件的输入流
                    InputStream inputStream = fileItem.getInputStream();

                    // 创建存放文件的路径
                    String path = UPLOAD;
                    System.out.println(path);

                    // 创建输出流
                    OutputStream outputStream = new FileOutputStream(path + fileName);
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    while ((len = inputStream.read(bytes)) != -1) {
                    	// 输出流写入文件
                        outputStream.write(bytes, 0, len);
                    }

                    // 关闭输入输出流
                    inputStream.close();
                    outputStream.close();
//                    response.getWriter().println("SUCCESS");
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
	}

}
