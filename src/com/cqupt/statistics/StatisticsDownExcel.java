package com.cqupt.statistics;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.ExcelServiceImpl;
import com.cqupt.pub.util.IExcelService;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticsDownExcel extends ActionSupport {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());
	InputStream excelStream;
	HttpServletRequest request = null;

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String execute() {
		String str = "";
		request = ServletActionContext.getRequest();
		String condition = request.getParameter("condition");
		if (condition.equals("1")) { // 1.代表按领用单位各使用方向的设备数
			str = find1();
			return str;
		} else if (condition.equals("2")) { // 2.按领用单位设备现状数
			str = find2();
			return str;
		} else if (condition.equals("3")) { // 3.按"存放地"分类统计
			str = find3();
			return str;
		} else if (condition.equals("4")) { // 4.按领用单位设备金额
			str = find4();
			return str;
		} else if (condition.equals("5")) { // 5.分户增减变动统计
			str = find5();
			return str;
		}
		return null;
	}

	public String find1() { // 代表按领用单位各使用方向的设备数
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		try {
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "按照使用方向资产统计.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "部门", "科研", "教学", "行政", "总数" };// excel工作表的标题
			String[] keys = { "departmentName", "productUse1", "productUse2",
					"productUse3", "total" };

			sql = "SELECT\n"
					+ "	IFNULL(m.department_name, 'total') AS department_name,\n"
					+ "	SUM(\n" + "		IF (\n" + "			m.product_use = '科研',\n"
					+ "			count,\n" + "			0\n" + "		)\n"
					+ "	) AS product_use1,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.product_use = '教学',\n" + "			count,\n" + "			0\n"
					+ "		)\n" + "	) AS product_use2,\n" + "	SUM(\n"
					+ "		IF (\n" + "			m.product_use = '行政',\n" + "			count,\n"
					+ "			0\n" + "		)\n" + "	) AS product_use3,\n" + "	SUM(\n"
					+ "		IF (\n" + "			m.product_use = '总数',\n" + "			count,\n"
					+ "			0\n" + "		)\n" + "	) AS total\n" + "FROM\n" + "	(\n"
					+ "		SELECT\n" + "			a.*\n" + "		FROM\n" + "			(\n"
					+ "				SELECT\n" + "					c.department_name,\n"
					+ "					c.product_use,\n"
					+ "					IFNULL(b.count, 0) count\n" + "				FROM\n"
					+ "					(\n" + "						SELECT\n"
					+ "							a.department_name,\n" + "							a.product_use,\n"
					+ "							COUNT(product_use) count\n" + "						FROM\n"
					+ "							product a\n" + "						GROUP BY\n"
					+ "							department_name,\n" + "							product_use\n"
					+ "					) b\n" + "				RIGHT JOIN (\n" + "					SELECT\n"
					+ "						a.department_name,\n" + "						b.product_use\n"
					+ "					FROM\n" + "						(\n" + "							SELECT DISTINCT\n"
					+ "								department_name\n" + "							FROM\n"
					+ "								product\n" + "						) a,\n" + "						(\n"
					+ "							SELECT DISTINCT\n" + "								product_use\n"
					+ "							FROM\n" + "								product\n" + "						) b\n"
					+ "				) c ON b.product_use = c.product_use\n"
					+ "				AND b.department_name = c.department_name\n"
					+ "				UNION\n" + "					SELECT\n"
					+ "						p.department_name,\n"
					+ "						'总数' product_use,\n"
					+ "						count(p.department_id) count\n" + "					FROM\n"
					+ "						product p\n" + "					GROUP BY\n"
					+ "						p.department_id\n" + "			) a\n" + "		ORDER BY\n"
					+ "			a.department_name\n" + "	) m\n" + "GROUP BY\n"
					+ "	m.department_name";

			logger.info("资产统计导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

	public String find2() { // 2.按领用单位设备现状数
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		try {
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "按照设备现状资产统计.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "部门", "库存", "报废", "在用", "故障", "校内转入", "数量" };// excel工作表的标题
			String[] keys = { "departmentName", "productStatus1",
					"productStatus2", "productStatus3", "productStatus4",
					"productStatus5", "total" };

			sql = "SELECT\n"
					+ "	IFNULL(m.department_name, 'total') AS department_name,\n"
					+ "	SUM(\n" + "		IF (\n" + "			m.product_status = '库存',\n"
					+ "			count,\n" + "			0\n" + "		)\n"
					+ "	) AS product_status1,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.product_status = '报废',\n" + "			count,\n"
					+ "			0\n" + "		)\n" + "	) AS product_status2,\n"
					+ "	SUM(\n" + "		IF (\n" + "			m.product_status = '在用',\n"
					+ "			count,\n" + "			0\n" + "		)\n"
					+ "	) AS product_status3,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.product_status = '故障',\n" + "			count,\n"
					+ "			0\n" + "		)\n" + "	) AS product_status4,\n"
					+ "	SUM(\n" + "		IF (\n"
					+ "			m.product_status = '校内转入',\n" + "			count,\n"
					+ "			0\n" + "		)\n" + "	) AS product_status5,\n"
					+ "	SUM(\n" + "		IF (\n" + "			m.product_status = '总数',\n"
					+ "			count,\n" + "			0\n" + "		)\n" + "	) AS total\n"
					+ "FROM\n" + "	(\n" + "		SELECT\n" + "			a.*\n"
					+ "		FROM\n" + "			(\n" + "				SELECT\n"
					+ "					c.department_name,\n" + "					c.product_status,\n"
					+ "					IFNULL(b.count, 0) count\n" + "				FROM\n"
					+ "					(\n" + "						SELECT\n"
					+ "							a.department_name,\n"
					+ "							a.product_status,\n"
					+ "							COUNT(department_name) count\n" + "						FROM\n"
					+ "							product a\n" + "						GROUP BY\n"
					+ "							department_name,\n" + "							product_status\n"
					+ "					) b\n" + "				RIGHT JOIN (\n" + "					SELECT\n"
					+ "						a.department_name,\n" + "						b.product_status\n"
					+ "					FROM\n" + "						(\n" + "							SELECT DISTINCT\n"
					+ "								department_name\n" + "							FROM\n"
					+ "								product\n" + "						) a,\n" + "						(\n"
					+ "							SELECT DISTINCT\n" + "								product_status\n"
					+ "							FROM\n" + "								product\n" + "						) b\n"
					+ "				) c ON b.product_status = c.product_status\n"
					+ "				AND b.department_name = c.department_name\n"
					+ "				UNION\n" + "					SELECT\n"
					+ "						p.department_name,\n"
					+ "						'总数' product_status,\n"
					+ "						count(p.department_id) count\n" + "					FROM\n"
					+ "						product p\n" + "					GROUP BY\n"
					+ "						p.department_id\n" + "			) a\n" + "		ORDER BY\n"
					+ "			a.department_name\n" + "	) m\n" + "GROUP BY\n"
					+ "	m.department_name";

			logger.info("资产统计导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

	public String find3() { // 3.按"存放地"分类统计
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		try {
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "按照存放地资产统计.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "地址", "数量" };// excel工作表的标题
			String[] keys = { "address", "sum" };

			sql = "SELECT\n" + "	@rownum :=@rownum + 1 AS rownum,\n" + "	t.*\n"
					+ "FROM\n" + "	(SELECT @rownum := 0) r,\n" + "	(\n"
					+ "		SELECT\n" + "			address,\n"
					+ "			count(product_code) sum\n" + "		FROM\n"
					+ "			zcgl.product\n" + "		WHERE\n"
					+ "			department_id IN (" + dataAuthId + ")\n"
					+ "		GROUP BY\n" + "			address\n" + "	) t";

			logger.info("资产统计导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

	public String find4() { // 3.按设备金额分类统计
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		try {
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "按照设备金额资产统计.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "部门", "金额" };// excel工作表的标题
			String[] keys = { "departmentName", "money" };

			sql = "SELECT\n" + "	@rownum :=@rownum + 1 AS rownum,\n" + "	t.*\n"
					+ "FROM\n" + "	(SELECT @rownum := 0) r,\n" + "	(\n"
					+ "		SELECT\n" + "			a.department_name,\n"
					+ "			SUM(a.price) money\n" + "		FROM\n" + "			product a\n"
					+ "		WHERE\n" + "			a.department_id IN (" + dataAuthId
					+ ")\n" + "		GROUP BY\n" + "			department_name\n" + "	) t";

			logger.info("资产统计导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

	public String find5() { // 5.按分户增减分类统计
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		try {
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "按照分户增减资产统计.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "部门", "第一季度（入库）", "第二季度（入库）", "第三季度（入库）",
					"第四季度（入库）", "第一季度（报废）", "第二季度（报废）", "第三季度（报废）", "第四季度（报废）" };// excel工作表的标题
			String[] keys = { "departmentName", "yQuarter1", "yQuarter2",
					"yQuarter3", "yQuarter4", "yQuarter5", "yQuarter6",
					"yQuarter7", "yQuarter8" };
			sql = "SELECT\n"
					+ "	IFNULL(m.department_name, 'total') AS department_name,\n"
					+ "	SUM(\n" + "		IF (\n" + "			m.y_quarter = '第一季度',\n"
					+ "			in_count,\n" + "			0\n" + "		)\n"
					+ "	) AS y_quarter1,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.y_quarter = '第二季度',\n" + "			in_count,\n"
					+ "			0\n" + "		)\n" + "	) AS y_quarter2,\n" + "	SUM(\n"
					+ "		IF (\n" + "			m.y_quarter = '第三季度',\n"
					+ "			in_count,\n" + "			0\n" + "		)\n"
					+ "	) AS y_quarter3,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.y_quarter = '第四季度',\n" + "			in_count,\n"
					+ "			0\n" + "		)\n" + "	) AS y_quarter4,\n" + "	SUM(\n"
					+ "		IF (\n" + "			m.y_quarter = '第一季度',\n"
					+ "			in_count,\n" + "			0\n" + "		)\n"
					+ "	) AS y_quarter5,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.y_quarter = '第二季度',\n" + "			in_count,\n"
					+ "			0\n" + "		)\n" + "	) AS y_quarter6,\n" + "	SUM(\n"
					+ "		IF (\n" + "			m.y_quarter = '第三季度',\n"
					+ "			in_count,\n" + "			0\n" + "		)\n"
					+ "	) AS y_quarter7,\n" + "	SUM(\n" + "		IF (\n"
					+ "			m.y_quarter = '第四季度',\n" + "			in_count,\n"
					+ "			0\n" + "		)\n" + "	) AS y_quarter8\n" + "FROM\n"
					+ "	(\n" + "		SELECT\n" + "			a.department_name,\n"
					+ "			(\n" + "				CASE\n"
					+ "				WHEN (a.y_quarter = '1') THEN\n" + "					'第一季度'\n"
					+ "				WHEN (a.y_quarter = '2') THEN\n" + "					'第二季度'\n"
					+ "				WHEN (a.y_quarter = '3') THEN\n" + "					'第三季度'\n"
					+ "				WHEN (a.y_quarter = '4') THEN\n" + "					'第四季度'\n"
					+ "				END\n" + "			) y_quarter,\n" + "			a.in_count,\n"
					+ "			ifnull(b.out_count, 0) out_count\n" + "		FROM\n"
					+ "			(\n" + "				SELECT\n" + "					a.department_name,\n"
					+ "					b.*, ifnull(a.in_count, 0) in_count\n"
					+ "				FROM\n" + "					(\n" + "						SELECT\n"
					+ "							a.department_name,\n" + "							a.y_quarter,\n"
					+ "							COUNT(a.department_id) in_count\n"
					+ "						FROM\n" + "							(\n" + "								SELECT\n"
					+ "									department_id,\n"
					+ "									department_name,\n"
					+ "									product_code,\n"
					+ "									substring(in_time, 1, 4) y_year,\n"
					+ "									QUARTER (in_time) y_quarter\n"
					+ "								FROM\n" + "									zcgl.product\n"
					+ "							) a\n" + "						GROUP BY\n"
					+ "							a.y_quarter,\n" + "							a.department_name\n"
					+ "					) a\n" + "				RIGHT JOIN (\n" + "					SELECT\n"
					+ "						select_val y_quarter\n" + "					FROM\n"
					+ "						select_item\n" + "					WHERE\n"
					+ "						select_name = '季度'\n"
					+ "				) b ON a.y_quarter = b.y_quarter\n" + "			) a\n"
					+ "		LEFT JOIN (\n" + "			SELECT\n"
					+ "				a.department_name,\n" + "				y_quarter,\n"
					+ "				COUNT(a.department_id) out_count\n" + "			FROM\n"
					+ "				(\n" + "					SELECT\n" + "						department_id,\n"
					+ "						department_name,\n"
					+ "						substring(last_out_time, 1, 4) y_year,\n"
					+ "						QUARTER (in_time) y_quarter\n" + "					FROM\n"
					+ "						zcgl.product\n" + "					WHERE\n"
					+ "						product_status = '报废'\n" + "				) a\n"
					+ "			GROUP BY\n" + "				a.y_quarter,\n"
					+ "				a.department_name\n"
					+ "		) b ON a.y_quarter = b.y_quarter\n" + "	) m\n"
					+ "GROUP BY\n" + "	m.department_name";

			logger.info("资产统计导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}
}
