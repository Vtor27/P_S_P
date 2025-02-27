
package es.florida.ejemplo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ejemplo_Contrasenya implements Serializable {	//Para poder compartirse entre clases en forma de bytes.
    private String passPlain;

    public Ejemplo_Contrasenya(String passPlain) {
        this.passPlain = passPlain;
    }

    public String getPassPlain() {
        return passPlain;
    }

    public void setPassPlain(String passPlain) {
        this.passPlain = passPlain;
    }
}
