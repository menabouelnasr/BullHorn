

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Blog;
import model.Blogacct;
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
    public static void insertProfile(Blogacct acct) 
    {
    	EntityManager em = DBUtil.getEmFactory().createEntityManager();
    	EntityTransaction trans = em.getTransaction();
    	trans.begin(); 
    	try {
    	em.persist(acct);
    	trans.commit();
    	} catch (Exception e) {
    	System.out.println(e);
    	trans.rollback();
    	} finally {
    
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
		
		String username, password, motto, output="", output2="";
		long ID = 0;
    	username=request.getParameter("user_name");
    	password= request.getParameter("pwd");
    	motto=request.getParameter("motto");
    	Date date = new Date();
		System.out.println(username + " " + password + " " + motto);
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try 
		{
			model.Blogacct user = new Blogacct();
			user.setMotto(motto);
			user.setPwd(password);
			user.setUserName(username);
			user.setJoindate(date);
			insertProfile(user);
			
			
	    	String qString = "Select max(b.userId) from Blogacct b";
	    	TypedQuery<Long> q = em.createQuery(qString, Long.class);
	    	ID= q.getSingleResult();
	    	System.out.println(ID);
	    	
			
		} catch (Exception e){
			System.out.println(e);
		} finally {
		
		}
		
		HttpSession session = request.getSession();
    	session.setAttribute("UserID", ID);
    	System.out.println(session.getAttribute("UserID"));
    	
    	List<Blogacct> profile = em.createQuery("Select b from Blogacct b where b.userId =:userID")
    			.setParameter("userID", ID)
    		    .setMaxResults(20)
    		    .getResultList();

    	
		output+="<table class= \"table table-striped\">";
		output+="<tr><th>Profile Information</th></tr> ";
		for(Blogacct b: profile)
		{
			output+= "<tr><td>"+b.getUserName() +"</td><td>" +b.getJoindate()+ "</td><td>"+ b.getMotto()+"</td></tr>";
		}
		
		List<Blog> blog = em.createQuery("Select b from Blog b where b.userid =:userID")
    			.setParameter("userID", ID)
    		    .setMaxResults(20)
    		    .getResultList();
   
		output2+="<table class= \"table table-striped\">";
		output2+="<tr><th>Recent Comments</th></tr> ";
		
		if (blog == null){
			
		}
		else{
		for(Blog c: blog)
		{
			output2+= "<tr><td>"+ c.getComments() +"</td></tr>";
		}
    	
    	
		request.setAttribute("message", output);
		request.setAttribute("message2", output2);
	    getServletContext().getRequestDispatcher("/profile.jsp").forward(request,response);
		output="";
		output2="";
		}
		
		
	}

}
