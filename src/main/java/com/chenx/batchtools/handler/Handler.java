package com.chenx.batchtools.handler;

import java.util.List;


/**
 * 
 * @author chenx
 * @since 2019-01-08 09:47:24
 * @version 1.0.0
 * 
 *
 */

public interface Handler {

	List<String> exec(String file) throws Exception;
	
}
