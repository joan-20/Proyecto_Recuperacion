package TutoriasModelo;
public class Tutor extends Persona {
    public String materia;
    public Tutor(String nombre, String cedula, String correo, String materia) {
        super(nombre, cedula, correo);
        this.materia = materia;
    }
}
