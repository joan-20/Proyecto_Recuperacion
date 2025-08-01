package TutoriasModelo;

import java.util.ArrayList;

public class TutoriasControl {
    protected ArrayList<Tutoria> tutorias;
    public ArrayList<Tutor> tutores;
    public ArrayList<Estudiante> estudiantes;
    public void ingresarTutoria(Tutoria t){
        tutorias.add(t);
    }
    public void ingresarTutor(Tutor t){
        tutores.add(t);
    }
    public void ingresarEstudiante(Estudiante e){
        estudiantes.add(e);
    }
}
