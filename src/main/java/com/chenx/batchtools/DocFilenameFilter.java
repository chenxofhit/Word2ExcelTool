package com.chenx.batchtools;

import java.io.File;
import java.io.FilenameFilter;

/**
 * doc 文档选择Filter
 * 
 * @author chenx
 * @since 2019-01-07 21:00:41
 * @version 1.0.0
 * 
 */
public class DocFilenameFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		boolean flag = true;
		if (name.toLowerCase().endsWith(".doc")) {
			flag = false;
			
		} else if (name.toLowerCase().endsWith(".docx")) {

		} else {
			flag = false;
		}
		return flag;
	}

}