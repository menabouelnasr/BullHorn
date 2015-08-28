

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
 * Servlet implementation class BlogInsert
 */
@WebServlet("/BlogInsert")
public class BlogInsert extends HttpServlet 
{
	static Connection conn;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogInsert() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    public static void insert(Blog blog) 
    {
    	EntityManager em = DBUtil.getEmFactory().createEntityManager();
    	EntityTransaction trans = em.getTransaction();
    	trans.begin(); 
    	try {
    	em.persist(blog);
    	trans.commit();
    	} catch (Exception e) {
    	System.out.println(e);
    	trans.rollback();
    	} finally {
    	em.close();
    	}
    }
    
    public List<Blog> getBlogs()
    {
    	EntityManager em = DBUtil.getEmFactory().createEntityManager();
    	String qString = "Select b from Blog b order by b.id desc";
    	TypedQuery<Blog> q = em.createQuery(qString, Blog.class);
    	
    	List<Blog> blogs;
    	try{ blogs= q.getResultList();
    	if(blogs == null || blogs.isEmpty())
    		blogs= null;
    	}
    	finally
    	{
    		em.close();
    	}
    	return blogs;
    	
    }
    
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		long ID;
		String blog, output="";
    	
		String userName = request.getParameter("user_name");
		String pwd = request.getParameter("pwd");
		System.out.println(userName);
		System.out.println(pwd);
		
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		List<model.Blogacct> accts = em.createQuery("Select b from Blogacct b where b.userName =:userName",model.Blogacct.class)
		.setParameter("userName", userName)
	    .setMaxResults(20)
	    .getResultList();
		
		
		if (accts.size()==0)
		{
			output = "Incorrect user name or password";
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
			
		}
		else 
		{
			
			System.out.println("account found");
			output+="<table class= \"table table-striped\">";
			output+="<tr><th>Recent Comments</th><th>User Id</th></tr> ";
			List<Blog> a = getBlogs();
			
			
			
			
			String username,comment = "";
			long UserID = 0;
			Date postDate = null;
			
			UserID= accts.get(0).getUserId();
			System.out.println("userID: " + UserID);
				
		
			
			
			
			HttpSession session = request.getSession();
			session.setAttribute("UserID", UserID);
	    	System.out.println(UserID);
			
			
			for(Blog b : a)
			{
				output+= "<tr><td>"+ b.getComments()+"</td><td>" + b.getUserid()+"</td></tr>";
		    	
			}
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
		    output="";
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		long UsersID;
		HttpSession session= request.getSession(true);
		UsersID= (long) session.getAttribute("UserID");
		System.out.println("It got it " +UsersID);
		
		//System.out.println(request.getParameter("guest"));
		if (request.getParameter("guest")==null)
		{
		session.setAttribute("LoggedIn", true);
		String blog, output="";
    	blog=request.getParameter("blog");
    	Date date = new Date();
		try 
		{
			model.Blog user = new Blog();
			user.setComments(blog);
			user.setDates(date);
			user.setUserid(UsersID);
			insert(user);
		} catch (Exception e){
			System.out.println(e);
		} finally {
			em.close();
			System.out.println("cerrado!");
		}
		output+="<table class= \"table table-striped\">";
		output+="<tr><th>Recent Comments</th><th> UserID</th></tr> ";
		List<Blog> a = getBlogs();
		for(Blog b : a)
		{
			output+= "<tr><td>"+ b.getComments()+"</td><td>"+ b.getUserid()+"</td></tr>";
		}
		request.setAttribute("message", output);
	    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
		output="";
		}
		else
		{
			session.setAttribute("LoggedIn", false);
			String blog, output="";
			
			output+="<table class= \"table table-striped\">";
			output+="<tr><th>Recent Comments</th><th> User ID<th></tr> ";
			List<Blog> a = getBlogs();
			for(Blog b : a)
			{
				output+= "<tr><td>"+ b.getComments()+"</td><td>"+ b.getUserid()+"</td></tr>";
				System.out.println("Test");
			}
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
			output="";
		}
	}

}
