package com.jdc.pos.util;

public class MiniPosException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //error tat twr dr ko file anay nk htote thein poh;
	
	public MiniPosException(String errorLog) {
		super(errorLog);//Parent htl mhr exception log htl ka kg twae ko custom, String obj anay nk pyn poh pay loh ya; 
	}

}
