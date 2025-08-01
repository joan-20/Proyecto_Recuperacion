package TutoriasModelo;

import java.util.ArrayList;

public class Persona {
    protected String nombre;
    public String cedula;
    protected String correo;
    protected ArrayList<Tutoria> tutorias;
    
    public Persona(String nombre, String cedula, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }
    
}
