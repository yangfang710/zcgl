package com.cqupt.statistics;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class FindAction extends ActionSupport {
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;

	public String execute() {
		logger.info("FindAction:");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;

		/**
		 * 传过来的统计维度 1.代表按领用单位各使用方向的设备数 2.按领用单位设备现状数 3.按"存放地"分类统计 4.按领用单位设备金额
		 * 5.分户增减变动统计
		 */
		String condition = request.getParameter("condition");
		logger.info("condition是：" + condition);
		String type = request.getParameter("type");
		logger.info("type是：" + type);
		String departmentId = request.getParameter("departmentId");
		logger.info("departmentId是：" + departmentId);
		if (departmentId == "") {
			departmentId = "01";
		}
		String str = "";
		if (condition.equals("1") && type.equals("column")) {// 代表按领用单位各使用方向的设备数
			find1(departmentId);
			str = "find1";
		} else if (condition.equals("1") && type.equals("pie1")) {// 按领用单位各使用方向统计--饼图
			findPie1(departmentId);
			str = "pie1";
		} else if (condition.equals("2") && type.equals("pie2")) {// 按领用单位各使用方向统计--饼图
			findPie2(departmentId);
			str = "pie2";
		} else if (condition.equals("2") && type.equals("column")) {// 按领用单位设备现状数
			find2(departmentId);
			str = "find2";
		} else if (condition.equals("3") && type.equals("column")) {// 按"存放地"分类统计
			find3(departmentId);
			str = "find3";
		} else if (condition.equals("3") && type.equals("pie3")) {// 按"存放地"分类统计--饼图
			find3(departmentId);
			str = "pie3";
		} else if (condition.equals("4") && type.equals("column")) {// 按领用单位设备金额
			find4(departmentId);
			str = "find4";
		} else if (condition.equals("4") && type.equals("pie4")) {// 按领用单位设备金额--饼图
			find4(departmentId);
			str = "pie4";
		} else if (condition.equals("5") && type.equals("column")) {// 分户增减变动统计
			String txBeginDate = request.getParameter("txBeginDate");
			find5(txBeginDate, departmentId);
			str = "find5";
		} else if (condition.equals("5") && type.equals("pie5")) {// 分户增减变动统计__饼图
			String txBeginDate = request.getParameter("txBeginDate");
			findPie5(txBeginDate, departmentId);
			str = "pie5";
		}
		return str;
	}

	public void find1(String departmentId) { // 代表按领用单位各使用方向的设备数
		String sql = "";
		boolean flag = false;

		DataStormSession session = null;
		try {
			session = DataStormSession.getInstance();
			if ((departmentId + "").length() == 2) {
				sql = "SELECT\n" + "	a.*, b.dept_name department_name \n"
						+ "FROM\n" + "	(\n" + "		SELECT\n"
						+ "			c.product_use,\n" + "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n" + "		FROM\n"
						+ "			(\n" + "				SELECT\n" + "					a.product_use,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n" + "				FROM\n"
						+ "					(\n" + "						SELECT\n"
						+ "							a.product_use,\n"
						+ "							SUBSTR(a.department_id,1,4) department_id,\n"
						+ "							COUNT(product_use) count\n" + "						FROM\n"
						+ "							product a\n" + "						WHERE\n"
						+ "							department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_use\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_use\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_use\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						SUBSTR(department_id,1,4) department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						product_use\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "				) b\n"
						+ "		) c ON b.product_use = c.product_use\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_use,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						SUBSTR(a.department_id,1,4) department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			} else {
				sql = "SELECT\n"
						+ "	a.*, b.dept_name department_name \n"
						+ "FROM\n"
						+ "	(\n"
						+ "		SELECT\n"
						+ "			c.product_use,\n"
						+ "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n"
						+ "		FROM\n"
						+ "			(\n"
						+ "				SELECT\n"
						+ "					a.product_use,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n"
						+ "				FROM\n"
						+ "					(\n"
						+ "						SELECT\n"
						+ "							a.product_use,\n"
						+ "							a.department_id,\n"
						+ "							COUNT(product_use) count\n"
						+ "						FROM\n"
						+ "							product a\n"
						+ "						WHERE\n"
						+ "							department_id LIKE '010Y%'\n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_use\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_use\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_use\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '010Y%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						product_use\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "				) b\n"
						+ "		) c ON b.product_use = c.product_use\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_use,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '010Y%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			}
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList1:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			Set<String> departmentNameSet = new HashSet<String>();
			Set<String> productUseSet = new HashSet<String>();
			for (Map map : resultList) {
				// 得到所有的部门
				departmentNameSet.add(map.get("departmentName").toString());
				// 得到所有的使用方向
				productUseSet.add(map.get("productUse").toString());
			}
			Map<String, List<Double>> data = new HashMap<String, List<Double>>();
			for (String productUse : productUseSet) {
				List<Double> list = new ArrayList<Double>();
				for (String departmentName : departmentNameSet) {
					for (Map map : resultList) {
						if (departmentName.equals(map.get("departmentName"))
								&& productUse.equals(map.get("productUse"))) {
							list.add(Double.parseDouble(map.get("count") + ""));

						}
					}
				}
				data.put(productUse, list);
			}
			logger.info("departmentNameSet:" + departmentNameSet);
			logger.info("productUseSet:" + productUseSet);
			logger.info("data:" + data);

			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + yLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			for (String s : strArr) {
				String[] arr = s.split(":");
				String result = "{name:" + arr[0] + ",data:" + arr[1] + "}";
				sb.append(result).append(",");
			}
			String dd = sb.substring(0, sb.length() - 1);
			dd = dd + "]";

			logger.info("dd:" + dd);

			request.setAttribute("record", "yes");
			request.setAttribute("xLay", departmentNameSet);
			request.setAttribute("yLay", dd);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void findPie1(String departmentId) { // 代表按领用单位各使用方向的设备数_饼状图
		String sql = "";

		DataStormSession session = null;
		try {
			session = DataStormSession.getInstance();
			if ((departmentId + "").length() == 2) {
				sql = "SELECT\n"
						+ "	a.*, b.dept_name department_name\n"
						+ "FROM\n"
						+ "	(\n"
						+ "		SELECT\n"
						+ "			c.product_use,\n"
						+ "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n"
						+ "		FROM\n"
						+ "			(\n"
						+ "				SELECT\n"
						+ "					a.product_use,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n"
						+ "				FROM\n"
						+ "					(\n"
						+ "						SELECT\n"
						+ "							a.product_use,\n"
						+ "							SUBSTR(a.department_id, 1, 4) department_id,\n"
						+ "							COUNT(product_use) count\n" + "						FROM\n"
						+ "							product a\n" + "						WHERE\n"
						+ "							department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_use\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_use\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_use\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						SUBSTR(department_id, 1, 4) department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						product_use\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "				) b\n"
						+ "		) c ON b.product_use = c.product_use\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_use,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						SUBSTR(a.department_id, 1, 4) department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			} else {

				sql = "select a.* from (SELECT\n" + "	c.department_name,\n"
						+ "	c.product_use,\n" + "	IFNULL(b.count, 0) count\n"
						+ "FROM\n" + "	(\n" + "		SELECT\n"
						+ "			a.department_name,\n" + "			a.product_use,\n"
						+ "			COUNT(product_use) count\n" + "		FROM\n"
						+ "			product a WHERE department_id LIKE '"
						+ departmentId
						+ "%'\n"
						+ "		GROUP BY\n"
						+ "			department_name,\n"
						+ "			product_use\n"
						+ "	) b\n"
						+ "RIGHT JOIN (\n"
						+ "	SELECT\n"
						+ "		a.department_name,\n"
						+ "		b.product_use\n"
						+ "	FROM\n"
						+ "		(\n"
						+ "			SELECT DISTINCT\n"
						+ "				department_name\n"
						+ "			FROM\n"
						+ "				product WHERE department_id LIKE '"
						+ departmentId
						+ "%'\n"
						+ "		) a,\n"
						+ "		(\n"
						+ "			SELECT DISTINCT\n"
						+ "				product_use\n"
						+ "			FROM\n"
						+ "				product \n"
						+ "		) b\n"
						+ ") c ON b.product_use = c.product_use\n"
						+ "AND b.department_name = c.department_name\n"
						+ "UNION\n"
						+ "select p.department_name,'总数' product_use , count(p.department_id) count from product p  where p.department_id LIKE '"
						+ departmentId
						+ "%' GROUP BY p.department_id) a order by a.department_name";
			}
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList1:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			Set<String> departmentNameSet = new HashSet<String>();
			Set<String> productUseSet = new HashSet<String>();
			for (Map map : resultList) {
				// 得到所有的部门
				departmentNameSet.add(map.get("departmentName").toString());
				// 得到所有的使用方向
				productUseSet.add(map.get("productUse").toString());
			}
			Map<String, List<String>> data = new HashMap<String, List<String>>();
			for (String productUse : productUseSet) {
				List<String> list = new ArrayList<String>();
				for (String departmentName : departmentNameSet) {
					for (Map map : resultList) {
						if (departmentName.equals(map.get("departmentName"))
								&& productUse.equals(map.get("productUse"))) {
							list.add("['" + map.get("departmentName") + "',"
									+ map.get("count") + "]");

						}
					}
				}
				data.put(productUse, list);
			}
			logger.info("departmentNameSet:" + departmentNameSet);
			logger.info("productUseSet:" + productUseSet);
			logger.info("data:" + data);

			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + yLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("]],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			logger.info("strArr:" + strArr);
			for (int i = 0; i < strArr.length; i++) {
				String[] arr = strArr[i].split(":");
				String result = "";
				if (i == strArr.length - 1) {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "}";
				} else {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "]}";
				}
				sb.append(result).append(",");
			}
			String dd = sb.substring(0, sb.length() - 1);
			dd = dd + "]";

			logger.info("dd:" + dd);
			String[] arr = dd.split("}");
			logger.info("arr0:" + arr[0]);
			arr[1] = arr[1].substring(1, arr[1].length());
			logger.info("arr1:" + arr[1]);
			arr[2] = arr[2].substring(1, arr[2].length());
			logger.info("arr2:" + arr[2]);
			arr[3] = arr[3].substring(1, arr[3].length());
			logger.info("arr3:" + arr[3]);
			request.setAttribute("xingzheng", arr[0] + "}]");
			request.setAttribute("keyan", "[" + arr[1] + "}]");
			request.setAttribute("zongshu", "[" + arr[2] + "}]");
			request.setAttribute("jiaoxue", "[" + arr[3] + "}]");
			request.setAttribute("record", "yes");
			request.setAttribute("yLay", dd);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void find2(String departmentId) { // 2.按领用单位设备现状数
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();
			if ((departmentId + "").length() == 2) {
				sql = "SELECT\n" + "	a.*, b.dept_name department_name \n"
						+ "FROM\n" + "	(\n" + "		SELECT\n"
						+ "			c.product_status,\n" + "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n" + "		FROM\n"
						+ "			(\n" + "				SELECT\n"
						+ "					a.product_status,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n" + "				FROM\n"
						+ "					(\n" + "						SELECT\n"
						+ "							a.product_status,\n"
						+ "							SUBSTR(a.department_id,1,4) department_id,\n"
						+ "							COUNT(product_status) count\n"
						+ "						FROM\n" + "							product a\n"
						+ "						WHERE\n" + "							department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_status\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_status\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_status\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						SUBSTR(department_id,1,4) department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						select_val product_status\n"
						+ "					FROM\n"
						+ "						select_item where select_name='当前状态'\n"
						+ "				) b\n"
						+ "		) c ON b.product_status = c.product_status\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_status,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						SUBSTR(a.department_id,1,4) department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			} else {

				sql = "SELECT\n" + "	a.*, b.dept_name department_name \n"
						+ "FROM\n" + "	(\n" + "		SELECT\n"
						+ "			c.product_status,\n" + "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n" + "		FROM\n"
						+ "			(\n" + "				SELECT\n"
						+ "					a.product_status,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n" + "				FROM\n"
						+ "					(\n" + "						SELECT\n"
						+ "							a.product_status,\n"
						+ "							a.department_id,\n"
						+ "							COUNT(product_status) count\n"
						+ "						FROM\n" + "							product a\n"
						+ "						WHERE\n" + "							department_id LIKE '"
						+ departmentId
						+ "%'\n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_status\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_status\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_status\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						select_val product_status\n"
						+ "					FROM\n"
						+ "						select_item where select_name='当前状态'\n"
						+ "				) b\n"
						+ "		) c ON b.product_status = c.product_status\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_status,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			}
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			Set<String> departmentNameSet = new HashSet<String>();
			Set<String> productUseSet = new HashSet<String>();
			for (Map map : resultList) {
				// 得到所有的部门
				departmentNameSet.add(map.get("departmentName").toString());
				// 得到所有的部门所属类型
				productUseSet.add(map.get("productStatus").toString());
			}
			Map<String, List<Double>> data = new HashMap<String, List<Double>>();
			for (String productUse : productUseSet) {
				List<Double> list = new ArrayList<Double>();
				data.put(productUse, list);
				for (String departmentName : departmentNameSet) {
					for (Map map : resultList) {
						if (productUse.equals(map.get("productStatus"))
								&& departmentName.equals(map
										.get("departmentName"))) {
							list.add(Double.parseDouble(map.get("count") + ""));
						}
					}
				}
			}
			logger.info("1:" + departmentNameSet);
			logger.info("1:" + data);

			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + yLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			for (String s : strArr) {
				String[] arr = s.split(":");
				String result = "{name:" + arr[0] + ",data:" + arr[1] + "}";
				sb.append(result).append(",");
			}
			String dd = sb.substring(0, sb.length() - 1);
			dd = dd + "]";

			logger.info("dd:" + dd);
			request.setAttribute("record", "yes");
			request.setAttribute("xLay", departmentNameSet);
			request.setAttribute("yLay", dd);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void findPie2(String departmentId) { // 2.按领用单位设备现状数__饼图
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();
			if ((departmentId + "").length() == 2) {
				sql = "SELECT\n" + "	a.*, b.dept_name department_name \n"
						+ "FROM\n" + "	(\n" + "		SELECT\n"
						+ "			c.product_status,\n" + "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n" + "		FROM\n"
						+ "			(\n" + "				SELECT\n"
						+ "					a.product_status,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n" + "				FROM\n"
						+ "					(\n" + "						SELECT\n"
						+ "							a.product_status,\n"
						+ "							SUBSTR(a.department_id,1,4) department_id,\n"
						+ "							COUNT(product_status) count\n"
						+ "						FROM\n" + "							product a\n"
						+ "						WHERE\n" + "							department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_status\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_status\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_status\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						SUBSTR(department_id,1,4) department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						select_val product_status\n"
						+ "					FROM\n"
						+ "						select_item where select_name='当前状态' \n"
						+ "				) b\n"
						+ "		) c ON b.product_status = c.product_status\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_status,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						SUBSTR(a.department_id,1,4) department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			} else {

				sql = "SELECT\n"
						+ "	a.*, b.dept_name department_name\n"
						+ "FROM\n"
						+ "	(\n"
						+ "		SELECT\n"
						+ "			c.product_status,\n"
						+ "			c.department_id,\n"
						+ "			IFNULL(b.count, 0) count\n"
						+ "		FROM\n"
						+ "			(\n"
						+ "				SELECT\n"
						+ "					a.product_status,\n"
						+ "					a.department_id,\n"
						+ "					sum(a.count) count\n"
						+ "				FROM\n"
						+ "					(\n"
						+ "						SELECT\n"
						+ "							a.product_status,\n"
						+ "							SUBSTR(a.department_id, 1, 4) department_id,\n"
						+ "							COUNT(product_status) count\n"
						+ "						FROM\n" + "							product a\n"
						+ "						WHERE\n" + "							department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "						GROUP BY\n"
						+ "							department_id,\n"
						+ "							product_status\n"
						+ "					) a\n"
						+ "				GROUP BY\n"
						+ "					a.department_id,\n"
						+ "					a.product_status\n"
						+ "			) b\n"
						+ "		RIGHT JOIN (\n"
						+ "			SELECT\n"
						+ "				a.department_id,\n"
						+ "				b.product_status\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						SUBSTR(department_id, 1, 4) department_id\n"
						+ "					FROM\n"
						+ "						product\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "				) a,\n"
						+ "				(\n"
						+ "					SELECT DISTINCT\n"
						+ "						select_val product_status\n"
						+ "					FROM\n"
						+ "						select_item where select_name='当前状态'\n"
						+ "				) b\n"
						+ "		) c ON b.product_status = c.product_status\n"
						+ "		AND b.department_id = c.department_id\n"
						+ "		UNION\n"
						+ "			SELECT\n"
						+ "				'总数' product_status,\n"
						+ "				a.department_id,\n"
						+ "				sum(a.count) count\n"
						+ "			FROM\n"
						+ "				(\n"
						+ "					SELECT\n"
						+ "						SUBSTR(a.department_id, 1, 4) department_id,\n"
						+ "						COUNT(department_id) count\n"
						+ "					FROM\n"
						+ "						product a\n"
						+ "					WHERE\n"
						+ "						department_id LIKE '"
						+ departmentId
						+ "%' \n"
						+ "					GROUP BY\n"
						+ "						department_id\n"
						+ "				) a\n"
						+ "			GROUP BY\n"
						+ "				a.department_id\n"
						+ "	) a\n"
						+ "LEFT JOIN sys_dept b ON a.department_id = b.dept_id\n"
						+ "ORDER BY\n" + "	b.dept_name";
			}
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			Set<String> departmentNameSet = new HashSet<String>();
			Set<String> productUseSet = new HashSet<String>();
			for (Map map : resultList) {
				// 得到所有的部门
				departmentNameSet.add(map.get("departmentName").toString());
				// 得到所有的部门所属类型
				productUseSet.add(map.get("productStatus").toString());
			}
			Map<String, List<String>> data = new HashMap<String, List<String>>();
			for (String productUse : productUseSet) {
				List<String> list = new ArrayList<String>();
				data.put(productUse, list);
				for (String departmentName : departmentNameSet) {
					for (Map map : resultList) {
						if (productUse.equals(map.get("productStatus"))
								&& departmentName.equals(map
										.get("departmentName"))) {
							list.add("['" + map.get("departmentName") + "',"
									+ map.get("count") + "]");
						}
					}
				}
			}
			logger.info("1:" + departmentNameSet);
			logger.info("1:" + data);

			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + yLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("]],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			for (int i = 0; i < strArr.length; i++) {
				String[] arr = strArr[i].split(":");
				String result = "";
				if (i == strArr.length - 1) {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "}";
				} else {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "]}";
				}
				sb.append(result).append(",");
			}
			String dd = sb.substring(0, sb.length() - 1);
			dd = dd + "]";

			logger.info("dd:" + dd);
			String[] arr = dd.split("}");
			logger.info("arr0:" + arr[0]);// 待报废
			arr[1] = arr[1].substring(1, arr[1].length());
			logger.info("arr1:" + arr[1]);// 校内转入
			arr[2] = arr[2].substring(1, arr[2].length());
			logger.info("arr2:" + arr[2]);// 总数
			arr[3] = arr[3].substring(1, arr[3].length());
			logger.info("arr3:" + arr[3]);// 报废
			arr[4] = arr[4].substring(1, arr[4].length());
			logger.info("arr4:" + arr[4]);// 故障
			arr[5] = arr[5].substring(1, arr[5].length());
			logger.info("arr5:" + arr[5]);// 在用
			arr[6] = arr[6].substring(1, arr[6].length());
			logger.info("arr6:" + arr[6]);// 库存
			request.setAttribute("daibaofei", arr[0] + "}]");
			request.setAttribute("xiaoneizhuanru", "[" + arr[1] + "}]");
			request.setAttribute("zongshu", "[" + arr[2] + "}]");
			request.setAttribute("baofei", "[" + arr[3] + "}]");
			request.setAttribute("guzhang", "[" + arr[4] + "}]");
			request.setAttribute("zaiyong", "[" + arr[5] + "}]");
			request.setAttribute("kucun", "[" + arr[6] + "}]");
			request.setAttribute("record", "yes");
			request.setAttribute("yLay", dd);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void find3(String departmentId) { // 3.按"存放地"分类统计
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();

			sql = "select address,count(product_code) sum from zcgl.product group by address";

			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> rows = session.findSql(sql);
			logger.info("rows：" + rows);
			if (rows.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			StringBuilder xLay = new StringBuilder();
			StringBuilder yLay = new StringBuilder();

			for (Map<String, Object> m : rows) {
				xLay.append(m.get("address") + ",");
				yLay.append(m.get("sum") + ",");
			}
			// 去掉最后一个逗号
			if (0 != yLay.length()) {
				xLay.deleteCharAt(xLay.length() - 1);
				yLay.deleteCharAt(yLay.length() - 1);
			}
			logger.info("X轴：" + xLay.toString());
			logger.info("Y轴：" + yLay.toString());
			request.setAttribute("record", "yes");
			request.setAttribute("xLay", xLay.toString());
			request.setAttribute("yLay", yLay.toString());

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void find4(String departmentId) { // 4.按领用单位设备金额
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();
			if ((departmentId + "").length() == 2) {
				sql = "SELECT\n"
						+ "	SUBSTR(a.department_id, 1, 4) department_id,\n"
						+ "	a.department_name,\n" + "	SUM(a.price) money\n"
						+ "FROM\n" + "	product a\n" + "WHERE\n"
						+ "	a.department_id LIKE '" + departmentId + "%'\n"
						+ "GROUP BY\n" + "	department_name";
			} else {
				sql = "SELECT\n" + "	a.department_name,\n"
						+ "	SUM(a.price) money\n" + "FROM\n"
						+ "	zcgl.product a\n" + "WHERE\n"
						+ "	a.department_id LIKE '" + departmentId + "%'\n"
						+ "GROUP BY\n" + "	department_name";
			}

			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> rows = session.findSql(sql);
			logger.info("rows：" + rows);
			if (rows.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			StringBuilder xLay = new StringBuilder();
			StringBuilder yLay = new StringBuilder();

			for (Map<String, Object> m : rows) {
				xLay.append(m.get("departmentName") + ",");
				yLay.append(m.get("money") + ",");
			}
			// 去掉最后一个逗号
			if (0 != yLay.length()) {
				xLay.deleteCharAt(xLay.length() - 1);
				yLay.deleteCharAt(yLay.length() - 1);
			}
			logger.info("X轴：" + xLay.toString());
			logger.info("Y轴：" + yLay.toString());
			request.setAttribute("record", "yes");
			request.setAttribute("xLay", xLay.toString());
			request.setAttribute("yLay", yLay.toString());

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void find5(String txBeginDate, String departmentId) { // 5.分户增减变动统计
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();
			sql = "SELECT\n" + "	(\n" + "		CASE\n"
					+ "		WHEN (a.y_quarter = '1') THEN\n" + "			'第一季度'\n"
					+ "		WHEN (a.y_quarter = '2') THEN\n" + "			'第二季度'\n"
					+ "		WHEN (a.y_quarter = '3') THEN\n" + "			'第三季度'\n"
					+ "		WHEN (a.y_quarter = '4') THEN\n" + "			'第四季度'\n"
					+ "		END\n" + "	) y_quarter,\n" + "	a.in_count,\n"
					+ "	ifnull(b.out_count, 0) out_count\n" + "FROM\n" + "	(\n"
					+ "		SELECT\n" + "			b.*, ifnull(a.in_count, 0) in_count\n"
					+ "		FROM\n" + "			(\n" + "				SELECT\n"
					+ "					a.department_name,\n" + "					a.y_quarter,\n"
					+ "					COUNT(a.department_id) in_count\n" + "				FROM\n"
					+ "					(\n" + "						SELECT\n" + "							department_id,\n"
					+ "							department_name,\n" + "							product_code,\n"
					+ "							substring(in_time, 1, 4) y_year,\n"
					+ "							QUARTER (in_time) y_quarter\n" + "						FROM\n"
					+ "							zcgl.product\n" + "						WHERE\n"
					+ "							department_id LIKE '"
					+ departmentId
					+ "%'\n"
					+ "					) a\n"
					+ "				WHERE\n"
					+ "					a.y_year = '"
					+ txBeginDate
					+ "'\n"
					+ "				GROUP BY\n"
					+ "					a.y_quarter\n"
					+ "			) a\n"
					+ "		RIGHT JOIN (\n"
					+ "			SELECT\n"
					+ "				select_val y_quarter\n"
					+ "			FROM\n"
					+ "				select_item\n"
					+ "			WHERE\n"
					+ "				select_name = '季度'\n"
					+ "		) b ON a.y_quarter = b.y_quarter\n"
					+ "	) a\n"
					+ "LEFT JOIN (\n"
					+ "	SELECT\n"
					+ "		a.department_id,\n"
					+ "		a.department_name,\n"
					+ "		y_quarter,\n"
					+ "		COUNT(a.department_id) out_count\n"
					+ "	FROM\n"
					+ "		(\n"
					+ "			SELECT\n"
					+ "				department_id,\n"
					+ "				department_name,\n"
					+ "				product_code,\n"
					+ "				substring(last_out_time, 1, 4) y_year,\n"
					+ "				QUARTER (in_time) y_quarter\n"
					+ "			FROM\n"
					+ "				zcgl.product\n"
					+ "			WHERE\n"
					+ "				product_status = '报废'\n"
					+ "			AND department_id LIKE '"
					+ departmentId
					+ "%'\n"
					+ "		) a\n"
					+ "	WHERE\n"
					+ "		a.y_year = '"
					+ txBeginDate
					+ "'\n"
					+ "	GROUP BY\n"
					+ "		a.y_quarter\n"
					+ ") b ON a.y_quarter = b.y_quarter";
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			List<String> yQuarterSet = new ArrayList<String>();
			Set<String> countSet = new HashSet<String>();
			countSet.add("inCount");
			countSet.add("outCount");
			Map map1 = null;
			for (int i = 0; i < resultList.size(); i++) {
				// 得到所有的部门
				map1 = (Map) resultList.get(i);
				yQuarterSet.add(map1.get("yQuarter").toString());
			}
			Map<String, List<Double>> data = new HashMap<String, List<Double>>();
			List<Double> inCountList = new ArrayList<Double>();
			List<Double> outCountList = new ArrayList<Double>();
			logger.info("countSet:" + countSet);

			data.put("入库", inCountList);
			data.put("报废", outCountList);
			for (int i = 0; i < yQuarterSet.size(); i++) {
				for (Map map : resultList) {
					if (yQuarterSet.get(i).equals(map.get("yQuarter"))) {
						inCountList.add(Double.parseDouble(map.get("inCount")
								+ ""));
						outCountList.add(Double.parseDouble(map.get("outCount")
								+ ""));
					}
				}
			}
			logger.info("1:" + yQuarterSet);
			logger.info("1:" + data);

			String xLay = JSONArray.fromObject(yQuarterSet).toString();
			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + xLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			for (String s : strArr) {
				String[] arr = s.split(":");
				String result = "{name:" + arr[0] + ",data:" + arr[1] + "}";
				sb.append(result).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]");

			logger.info("yQuarterSet:" + yQuarterSet);
			logger.info("sb:" + sb);
			request.setAttribute("record", "yes");
			request.setAttribute("xLay", yQuarterSet);
			request.setAttribute("yLay", sb);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void findPie5(String txBeginDate, String departmentId) { // 5.分户增减变动统计__饼图
		String sql = "";
		DataStormSession session = null;

		try {
			session = DataStormSession.getInstance();
			sql = "SELECT\n" + "	(\n" + "		CASE\n"
					+ "		WHEN (a.y_quarter = '1') THEN\n" + "			'第一季度'\n"
					+ "		WHEN (a.y_quarter = '2') THEN\n" + "			'第二季度'\n"
					+ "		WHEN (a.y_quarter = '3') THEN\n" + "			'第三季度'\n"
					+ "		WHEN (a.y_quarter = '4') THEN\n" + "			'第四季度'\n"
					+ "		END\n" + "	) y_quarter,\n" + "	a.in_count,\n"
					+ "	ifnull(b.out_count, 0) out_count\n" + "FROM\n" + "	(\n"
					+ "		SELECT\n" + "			b.*, ifnull(a.in_count, 0) in_count\n"
					+ "		FROM\n" + "			(\n" + "				SELECT\n"
					+ "					a.department_name,\n" + "					a.y_quarter,\n"
					+ "					COUNT(a.department_id) in_count\n" + "				FROM\n"
					+ "					(\n" + "						SELECT\n" + "							department_id,\n"
					+ "							department_name,\n" + "							product_code,\n"
					+ "							substring(in_time, 1, 4) y_year,\n"
					+ "							QUARTER (in_time) y_quarter\n" + "						FROM\n"
					+ "							zcgl.product\n" + "						WHERE\n"
					+ "							department_id LIKE '"
					+ departmentId
					+ "%'\n"
					+ "					) a\n"
					+ "				WHERE\n"
					+ "					a.y_year = '"
					+ txBeginDate
					+ "'\n"
					+ "				GROUP BY\n"
					+ "					a.y_quarter\n"
					+ "			) a\n"
					+ "		RIGHT JOIN (\n"
					+ "			SELECT\n"
					+ "				select_val y_quarter\n"
					+ "			FROM\n"
					+ "				select_item\n"
					+ "			WHERE\n"
					+ "				select_name = '季度'\n"
					+ "		) b ON a.y_quarter = b.y_quarter\n"
					+ "	) a\n"
					+ "LEFT JOIN (\n"
					+ "	SELECT\n"
					+ "		a.department_id,\n"
					+ "		a.department_name,\n"
					+ "		y_quarter,\n"
					+ "		COUNT(a.department_id) out_count\n"
					+ "	FROM\n"
					+ "		(\n"
					+ "			SELECT\n"
					+ "				department_id,\n"
					+ "				department_name,\n"
					+ "				product_code,\n"
					+ "				substring(last_out_time, 1, 4) y_year,\n"
					+ "				QUARTER (in_time) y_quarter\n"
					+ "			FROM\n"
					+ "				zcgl.product\n"
					+ "			WHERE\n"
					+ "				product_status = '报废'\n"
					+ "			AND department_id LIKE '"
					+ departmentId
					+ "%'\n"
					+ "		) a\n"
					+ "	WHERE\n"
					+ "		a.y_year = '"
					+ txBeginDate
					+ "'\n"
					+ "	GROUP BY\n"
					+ "		a.y_quarter\n"
					+ ") b ON a.y_quarter = b.y_quarter";
			logger.info("统计的sql:" + sql);
			List<Map<String, Object>> resultList = session.findSql(sql);
			logger.info("resultList:" + resultList);
			if (resultList.size() == 0) {
				request.setAttribute("record", "no");
				return;
			}
			List<String> yQuarterSet = new ArrayList<String>();
			Set<String> countSet = new HashSet<String>();
			countSet.add("inCount");
			countSet.add("outCount");
			Map map1 = null;
			for (int i = 0; i < resultList.size(); i++) {
				// 得到所有的部门
				map1 = (Map) resultList.get(i);
				yQuarterSet.add(map1.get("yQuarter").toString());
			}
			Map<String, List<String>> data = new HashMap<String, List<String>>();
			List<String> inCountList = new ArrayList<String>();
			List<String> outCountList = new ArrayList<String>();
			logger.info("countSet:" + countSet);

			data.put("入库", inCountList);
			data.put("报废", outCountList);
			for (int i = 0; i < yQuarterSet.size(); i++) {
				for (Map map : resultList) {
					if (yQuarterSet.get(i).equals(map.get("yQuarter"))) {
						inCountList.add("['" + map.get("yQuarter") + "',"
								+ map.get("inCount") + "]");
						outCountList.add("['" + map.get("yQuarter") + "',"
								+ map.get("outCount") + "]");
					}
				}
			}
			logger.info("1:" + yQuarterSet);
			logger.info("1:" + data);

			String xLay = JSONArray.fromObject(yQuarterSet).toString();
			String yLay = JSONObject.fromObject(data).toString();
			logger.info("2:" + xLay);
			yLay = yLay.substring(1, yLay.length() - 1);
			logger.info("3:" + yLay);
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String[] strArr = yLay.split("]],");
			for (int i = 0; i < strArr.length - 1; i++) {
				strArr[i] = strArr[i] + "]";
			}
			logger.info("strArr" + strArr);
			for (int i = 0; i < strArr.length; i++) {
				String[] arr = strArr[i].split(":");
				String result = "";
				if (i == strArr.length - 1) {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "}";
				} else {
					result = "{type:'pie',name:" + arr[0] + ",data:" + arr[1]
							+ "]}";
				}
				sb.append(result).append(",");
			}
			String dd = sb.substring(0, sb.length() - 1);
			dd = dd + "]";

			logger.info("dd:" + dd);
			String[] arr = dd.split("}");
			logger.info("arr0:" + arr[0]);// 入库
			arr[1] = arr[1].substring(1, arr[1].length());
			logger.info("arr1:" + arr[1]);// 报废
			request.setAttribute("ruku", arr[0] + "}]");
			request.setAttribute("baofei", "[" + arr[1] + "}]");
			request.setAttribute("record", "yes");
			request.setAttribute("yLay", sb);

			session.closeSession();
		} catch (CquptException ce) {
			ce.printStackTrace();
			if (session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
