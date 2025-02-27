package es.florida;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contrasenya implements Serializable {

	String passPlain;
	String passCript;

	public Contrasenya() {
		super();
	}
	
	public String getPassPlain() {
		return passPlain;
	}

	public void setPassPlain(String passPlain) {
		this.passPlain = passPlain;
	}

	public String getPassCript() {
		return passCript;
	}

	public void setPassCript(String passCript) {
		this.passCript = passCript;
	}
}
