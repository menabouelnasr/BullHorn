package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BLOGACCT database table.
 * 
 */
@Entity
@Table (name="Blogacct", schema= "TESTDB")
@NamedQuery(name="Blogacct.findAll", query="SELECT b FROM Blogacct b")
public class Blogacct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private long userId;

	@Temporal(TemporalType.DATE)
	private Date joindate;

	private String motto;

	private String pwd;

	@Column(name="USER_NAME")
	private String userName;

	public Blogacct() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getJoindate() {
		return this.joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public String getMotto() {
		return this.motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
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