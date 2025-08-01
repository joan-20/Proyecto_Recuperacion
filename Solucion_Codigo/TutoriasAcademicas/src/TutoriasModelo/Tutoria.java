
package TutoriasModelo;

import java.util.ArrayList;

public abstract class Tutoria {
    public String nombre;
    public int avance;
    public int riesgo;
    public Tutor tutor;
    public String tipo;
    public ArrayList<Sesion> sesiones;
    public Tutoria(String nombre, String tipo, Tutor tutor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tutor = tutor;
    }
    public abstract void agregarEstudiante(Estudiante est);
}
