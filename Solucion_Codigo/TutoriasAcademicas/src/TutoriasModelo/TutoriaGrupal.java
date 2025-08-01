package TutoriasModelo;

import java.util.ArrayList;

public class TutoriaGrupal extends Tutoria {
    public ArrayList<Estudiante> estudiantes;
    public TutoriaGrupal(String nombre, String tipo, Tutor tutor) {
        super(nombre, tipo, tutor);
    }

    public void agregarEstudiante(Estudiante est) {
        estudiantes.add(est);
    }
    
}
