package com.jju.net;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

	private Map<String, String> parameterParams;
	
	public Parameters() {
		parameterParams = new HashMap();
	}
	
	public Parameters add(String key,String value){
		parameterParams.put(key, value);
		return this;
	}
	
    public String  	toMyUrl(){
    	StringBuilder sb = new StringBuilder("");
    	parameterParams.keySet();
    	for(String s:parameterParams.keySet()){
    		sb.append("&"+s+"="+  parameterParams.get(s));
    	}
		return sb.toString();
	}

}
