

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

import model.Blog;
import model.BlogAcct;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("user_name");
		String pwd = request.getParameter("pwd");
		System.out.println(userName);
		System.out.println(pwd);
		
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		List accts = em.createQuery("Select b from BlogAcct b where b.userName = :userName")
		.setParameter("userName", userName)
	    .setMaxResults(20)
	    .getResultList();
		
		if (accts == null)
		{
			String output = "Incorrect user name or password";
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
			
		}
		else {

			String blog, output="";
			
			output+="<table class= \"table table-striped\">";
			output+="<tr><th>Recent Comments</th></tr> ";
			List<Blog> a = getBlogs();
			for(Blog b : a)
			{
				output+= "<tr><td>"+ b.getComments()+"</td></tr>";
				System.out.println("Test");
			}
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println(request.getParameter("guest"));
		if (request.getParameter("guest")==null)
		{
		
		String blog, output="";
    	blog=request.getParameter("blog");
    	Date date = new Date();
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try 
		{
			model.Blog user = new Blog();
			user.setComments(blog);
			user.setDates(date);
			insert(user);
		} catch (Exception e){
			System.out.println(e);
		} finally {
			em.close();
			System.out.println("cerrado!");
		}
		output+="<table class= \"table table-striped\">";
		output+="<tr><th>Recent Comments</th></tr> ";
		List<Blog> a = getBlogs();
		for(Blog b : a)
		{
			output+= "<tr><td>"+ b.getComments()+"</td></tr>";
		}
		request.setAttribute("message", output);
	    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
		output="";
		}
		else
		{
			String blog, output="";
			
			output+="<table class= \"table table-striped\">";
			output+="<tr><th>Recent Comments</th></tr> ";
			List<Blog> a = getBlogs();
			for(Blog b : a)
			{
				output+= "<tr><td>"+ b.getComments()+"</td></tr>";
				System.out.println("Test");
			}
			request.setAttribute("message", output);
		    getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);
			output="";
		}
	}

}
