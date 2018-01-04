package br.com.ericksprengel.android.baking.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class BakingApiError {

	@SerializedName("status_message")
	private String statusMessage;

	@SerializedName("status_code")
	private int statusCode;

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		//TODO stringfy
		return statusMessage == null ? "Error" : statusMessage;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	@Override
 	public String toString(){
		return 
			"BakingApiError{" +
			"status_message = '" + statusMessage + '\'' + 
			",status_code = '" + statusCode + '\'' + 
			"}";
		}
}