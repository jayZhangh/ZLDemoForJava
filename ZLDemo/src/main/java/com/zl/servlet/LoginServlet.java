package com.zl.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zl.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("name1"));
		System.out.println(request.getParameter("name2"));
		System.out.println(request.getParameter("name3"));
		
		
		//		List<Object> insertParams = new ArrayList<Object>();
//		insertParams.add("赵柳");
//		insertParams.add("6666");
//		insertParams.add("123.png");
//		new DBDao().execute("insert into tb_user(user_name,user_password,user_portrait) values(?,?,?)", insertParams);
//		
//		List<Object> params = new ArrayList<Object>();
//		params.add("张三");
//		response.getWriter().println(new DBDao().select("select * from tb_user where user_name=?", params));
//		response.getWriter().println(new DBDao().selectMore("select * from tb_user", null));
//		UserInfo user = null;
//		user = DBDao().select("select * from tb_user where user_name=?", params, UserInfo.class);
//		response.getWriter().println(user.getUser_id() + "\t" + user.getUser_name() + "\t" + user.getUser_password() + "\t" + user.getUser_portrait());
//		List<UserInfo> list = new DBDao().selectMore("select * from tb_user", null, UserInfo.class);
//		response.getWriter().println(list);
//		System.out.println(list);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String flag = UserService.login(userName, password) ? "1" : "0";
		response.getWriter().print(flag);
	}

}
