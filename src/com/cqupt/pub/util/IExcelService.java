package com.cqupt.pub.util;

import java.io.InputStream;

public interface IExcelService {
		InputStream getExcelInputStream(String[] title, String[] keys, String sql);   
} 

