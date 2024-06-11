package com.payable.ttt.dto;

public class MailSendElem extends BaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 202212201239L;
	
	
	private String firstName;	
	private String bEmail;	
	//private String apiKey;	
	private String password;	
	private String payable_logo_url;	
	private String merchant_portal_link;	
	private String payable_email;
	private String email_environment;
	private String email_portal_name;
	
	
	public MailSendElem() {
		 firstName="";	
		 bEmail="";		
		 password="";
		 payable_logo_url="";		
		 merchant_portal_link="";	
		 payable_email="";	
		 email_environment="";	
		 email_portal_name="";	
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getbEmail() {
		return bEmail;
	}
	public void setbEmail(String bEmail) {
		this.bEmail = bEmail;
	}
//	public String getApiKey() {
//		return apiKey;
//	}
//	public void setApiKey(String apiKey) {
//		this.apiKey = apiKey;
//	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPayable_logo_url() {
		return payable_logo_url;
	}
	public void setPayable_logo_url(String payable_logo_url) {
		this.payable_logo_url = payable_logo_url;
	}
	public String getMerchant_portal_link() {
		return merchant_portal_link;
	}
	public void setMerchant_portal_link(String merchant_portal_link) {
		this.merchant_portal_link = merchant_portal_link;
	}
	public String getPayable_email() {
		return payable_email;
	}
	public void setPayable_email(String payable_email) {
		this.payable_email = payable_email;
	}
	public String getEmail_environment() {
		return email_environment;
	}
	public void setEmail_environment(String email_environment) {
		this.email_environment = email_environment;
	}
	public String getEmail_portal_name() {
		return email_portal_name;
	}
	public void setEmail_portal_name(String email_portal_name) {
		this.email_portal_name = email_portal_name;
	}	
	

}
