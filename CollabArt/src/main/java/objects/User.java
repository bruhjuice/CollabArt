package objects;

import org.json.simple.JSONObject;

public class User {
	private String username;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() { return username; }
	
	@Override
	public boolean equals(Object o) {
		User u = (User)o;
		return u.getUsername().equals(username);
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		return jsonObject.toString();
	}
}
