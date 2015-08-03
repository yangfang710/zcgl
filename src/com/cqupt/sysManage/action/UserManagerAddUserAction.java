package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserManagerAddUserAction extends ActionSupport implements
		ModelDriven<User> {
	private static final long serialVersionUID = -6941574699319426537L;

	private User user = new User();

	public User getModel() {
		return user;
	}

	Logger logger = Logger.getLogger(getClass());
	HttpServletRequest request = null;
	private Md md5fun = new Md();

	public String execute() {
		this.request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = (String) request.getSession().getAttribute(
				"userName");
		response.setCharacterEncoding("UTF-8");
		DataStormSession session = null;
		String resultStr = "success";
		String sql = "";

		try {
			session = DataStormSession.getInstance();
			sql = "select * from sys_user t WHERE t.user_id = '"
					+ user.getTxtUserId() + "'";
			logger.info("sql：" + sql);
			List resultList = session.findSql(sql);

			if (resultList.size() > 0) {
				resultStr = "allExist";
			} else {
				sql = "INSERT INTO sys_user (user_id,user_name,user_pwd,dept_id,dept_name,group_id,group_name,user_state,"
						+ "	user_email,phone_num,remark,in_date,oper_user_name,team_name,data_auth_id,data_auth_name)"
						+ " VALUES('"
						+ user.getTxtUserId()
						+ "','"
						+ user.getTxtName()
						+ "','"
						+ md5fun.getMD5ofStr(user.getTxtPsw())
						+ "','"
						+ user.getDeptId()
						+ "','"
						+ user.getDeptName()
						+ "','"
						+ user.getGroupId()
						+ "','"
						+ user.getTxtUserGroup()
						+ "','"
						+ user.getIsUseable()
						+ "','"
						+ user.getTxtEmail()
						+ "','"
						+ user.getTxtPhone()
						+ "','',sysdate(),'"
						+ userName
						+ "','"+user.getTeamName()+"','"+user.getDataAuthId()+"','"+user.getDataAuthName()+"')";

				logger.info("添加用户：" + sql);
				session.add(sql);

			}

			session.closeSession();
		} catch (Exception e) {
			resultStr = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
