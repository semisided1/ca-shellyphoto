package info.berryworks.photoorder;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import info.berryworks.photoorder.dao.Dao;


@SuppressWarnings("serial")
public class ServletCreateOrder extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean lock = true;

		if (user == null) {
			resp.sendRedirect("/index.html");
			return;
		}

		if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
		if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

		if (lock) {
			resp.sendRedirect("/index.html");
			return;
		}
	
	
		String photourl = checkNull(req.getParameter("photourl")) ;
		String type = checkNull(req.getParameter("type"));
		String quantity =  checkNull(req.getParameter("quantity"));
		String x = checkNull(req.getParameter("albumid"));
		String orderdate = checkNull(req.getParameter("orderdate"));
		
	
		Dao.INSTANCE.addOrder(user.getEmail(), photourl, type, quantity, x,orderdate);
		resp.sendRedirect("/OrderApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
