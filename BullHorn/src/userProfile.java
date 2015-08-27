

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Blog;
import model.BlogAcct;
import customTools.DBUtil;

/**
 * Servlet implementation class userProfile
 */
@WebServlet("/userProfile")
public class userProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userProfile() 
    {
        super();
     
    }
    public static void insertProfile(BlogAcct acct) 
    {
    	EntityManager em = DBUtil.getEmFactory().createEntityManager();
    	EntityTransaction trans = em.getTransaction();
    	trans.begin(); 
    	System.out.println(acct);
    	try {
    	em.persist(acct);
    	trans.commit();
    	} catch (Exception e) {
    	System.out.println(e);
    	trans.rollback();
    	} finally {
    	em.close();
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username, password, motto, output="";
    	username=request.getParameter("user_name");
    	password= request.getParameter("pwd");
    	motto=request.getParameter("motto");
    	Date date = new Date();
		System.out.println(username + " " + password + " " + motto);
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try 
		{
			model.BlogAcct user = new BlogAcct();
			user.setMotto(motto);
			user.setPwd(password);
			user.setUserName(username);
			user.setJoindate(date);
			insertProfile(user);
		} catch (Exception e){
			System.out.println(e);
		} finally {
			em.close();
			System.out.println("cerrado!");
		}
	}

}
