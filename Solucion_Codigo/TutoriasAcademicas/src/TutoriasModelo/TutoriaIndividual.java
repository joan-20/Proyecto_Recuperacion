package TutoriasModelo;
public class TutoriaIndividual extends Tutoria{
    public Estudiante estudiante;

    public TutoriaIndividual(String nombre, String tipo, Tutor tutor) {
        super(nombre, tipo, tutor);
    }

    public void agregarEstudiante(Estudiante est) {
       this.estudiante = est;
    }
    
}
