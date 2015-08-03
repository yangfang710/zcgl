package com.cqupt.pub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;

public class ATest {
	
	public static void main(String[] args) {
		DataStormSession session = null;
		String sql = "";
		List brands = null, versions = null;
		Map<String, String> standardVersions = new HashMap<String, String>();
		Map<String,List<String>> categories = new HashMap<String,List<String>>();
		Map<String,List<String>> datas = new HashMap<String,List<String>>();
		StringBuilder sb = new StringBuilder();
		try {
			session = DataStormSession.getInstance();
			sql = " select infobase.brand_name name,sum(infobase.ratio)*100 as y from (SELECT	base.terminal_type,	base.brand_name,	base.version_name,sum(base.counter / total.total) AS ratio FROM(SELECT terminal_type,	brand_name,	version_name,	version_id,	serial_number,	count(*) counter FROM zcgl.case_accept GROUP BY serial_number	) AS base,(	SELECT count(DISTINCT serial_number) AS total	FROM zcgl.case_accept) AS total WHERE	base.counter >= 2 GROUP BY	base.version_id ORDER BY	base.brand_name) as infobase GROUP BY infobase.brand_name ORDER BY infobase.brand_name asc";
			brands = session.findSql(sql);
			sql = "SELECT base.brand_name,base.version_name,sum(base.counter / total.total)*100 AS ratio FROM(	SELECT terminal_type,brand_name,	version_name,	version_id,	serial_number,			count(*) counter	FROM	zcgl.case_accept	GROUP BY serial_number	) AS base,	(	SELECT	count(DISTINCT serial_number) AS total FROM	zcgl.case_accept	) AS total WHERE	base.counter >= 2 GROUP BY	base.version_id ORDER BY base.brand_name asc";
			versions = session.findSql(sql);
			
			for(int i = 0; i < brands.size(); i ++) {
				Map map = (Map) brands.get(i);
				standardVersions.put(map.get("name").toString(), map.get("y").toString());
				categories.put(map.get("name").toString(), new ArrayList<String>());
				datas.put(map.get("name").toString(), new ArrayList<String>());
				for(int j = 0; j < versions.size(); j ++) {
					Map m = (Map) versions.get(j);
					String brandName = m.get("brandName").toString();
					if(map.get("name").equals(brandName)) {
						List<String> category = categories.get(brandName); 
						List<String> data = datas.get(brandName); 
						category.add(m.get("versionName").toString());
						data.add(m.get("ratio").toString());
					}
				}
			}
			//System.out.println("standardVersions" + standardVersions);
			//System.out.println("categories" + categories);
			//System.out.println("datas" + datas);
			
			//组装特定格式json
			sb.append("[");
			for(int i = 0; i < brands.size(); i ++) {
				String brandName = ((Map)brands.get(i)).get("name").toString();
				sb.append("{");
				sb.append("y:" + standardVersions.get(brandName) + ",\n");
				sb.append("name:'" + brandName + "',\n");
				//组装categories
				List<String> l2 = categories.get(brandName);
				sb.append("categories:");
				sb.append("[");
				for(int k = 0; k < l2.size(); k++) {
					sb.append("'" + l2.get(k) + "'");
					if(k != l2.size() - 1) sb.append(","); 
				}
				sb.append("],\n");
				//组装datas
				l2 = datas.get(brandName);
				sb.append("datas:");
				sb.append("[");
				for(int k = 0; k < l2.size(); k++) {
					sb.append(l2.get(k));
					if(k != l2.size() - 1) sb.append(","); 
				}
				sb.append("]\n");
				
				sb.append("}");
				if(i != brands.size() - 1) sb.append(",\n"); 
			}
			sb.append("]");
			
			System.out.println(sb.toString());
			
			session.closeSession();
		} catch (CquptException e) {
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
}
