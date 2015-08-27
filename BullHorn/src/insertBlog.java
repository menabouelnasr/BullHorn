import java.util.Date;

import javax.persistence.EntityManager;

import model.Blog;
import customTools.DBUtil;

public class insertBlog 
{
	public static void main(String[] args) 
	{
		Date date = new Date();
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		try {
			model.Blog user = new Blog();
			user.setComments("Comment Number 2");
			user.setDates(date);
			BlogInsert.insert(user);
			System.out.println(user.getId());
		} catch (Exception e){
			System.out.println(e);
		} finally {
			em.close();
			System.out.println("cerrado!");
		}
	}
}
