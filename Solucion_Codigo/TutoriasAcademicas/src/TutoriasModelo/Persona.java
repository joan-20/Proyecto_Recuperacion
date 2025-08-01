package TutoriasModelo;

import java.util.ArrayList;

public class Persona {
    public String nombre;
    public String cedula;
    public String correo;
    public ArrayList<Tutoria> tutorias;
    
    public Persona(String nombre, String cedula, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }
    
}
