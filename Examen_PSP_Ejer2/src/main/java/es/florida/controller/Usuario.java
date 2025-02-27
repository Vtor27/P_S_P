package es.florida.controller;

public class Usuario {
	String user, passwors, online;

	public Usuario(String user, String passwors, String online) {
		super();
		this.user = user;
		this.passwors = passwors;
		this.online = online;
	}

	public Usuario() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswors() {
		return passwors;
	}

	public void setPasswors(String passwors) {
		this.passwors = passwors;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
}
