package com.roms.component.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class UserDetail {

		private long id;
		private String username;
		private String password;
		private List<String> roles;

		public UserDetail(JSONObject json) {
			this.username=json.getString("sub");
			this.roles=new ArrayList<>();
			if(json.toString().contains("isUser")&&json.getBoolean("isUser"))
				this.roles.add("ROLE_USER");
			if(json.toString().contains("isAdmin")&&json.getBoolean("isAdmin"))
				this.roles.add("ROLE_ADMIN");
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
		

	}

