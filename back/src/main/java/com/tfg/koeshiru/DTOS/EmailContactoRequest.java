package com.tfg.koeshiru.DTOS;

public class EmailContactoRequest {

		private String userEmail;
	    private String subject;
	    private String body;
	    
		
	    public EmailContactoRequest(String subject, String body, String userEmail) {
			super();
			this.subject = subject;
			this.body = body;
			this.userEmail = userEmail;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
	    
		
	    
	    
}
