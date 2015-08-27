package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BLOG_ACCT database table.
 * 
 */
@Entity
@Table(name="BLOG_ACCT")
@NamedQuery(name="BlogAcct.findAll", query="SELECT b FROM BlogAcct b")
public class BlogAcct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private long userId;

	private String pwd;

	@Column(name="USER_NAME")
	private String userName;

	public BlogAcct() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}