package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserManagerUpdateAction extends ActionSupport implements
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
		String txtUserId = request.getParameter("txtUserId");
		response.setCharacterEncoding("UTF-8");
		DataStormSession session = null;
		String resultStr = "success";
		String sql = "";

		try {
			session = DataStormSession.getInstance();

				sql = "update zcgl.sys_user set user_name ='" + user.getTxtName()
						+ "',group_id='" + user.getGroupId() + "',group_name = '"
						+ user.getTxtUserGroup() + "',dept_id='" + user.getDeptId()
						+ "',dept_name='" + user.getDeptName() + "'," + "user_state='"
						+ user.getIsUseable() + "',user_email='" + user.getTxtEmail()
						+ "',phone_num='" + user.getTxtPhone() + "',team_name='"+user.getTeamName()+"'" +
								",data_auth_id='"+user.getDataAuthId()+"',data_auth_name='"+user.getDataAuthName()+"' where user_id='"
						+ txtUserId + "'";
				logger.info("更改用户信息：" + sql);
				session.update(sql);			

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



 