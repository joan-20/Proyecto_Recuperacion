package TutoriasModelo;

import java.util.ArrayList;

public class Estudiante extends Persona{
    public int asis = 0;
    public int ause = 0;
    public ArrayList<String> asistencias;
    public Estudiante(String nombre, String cedula, String correo) {
        super(nombre, cedula, correo);
    }
    public void registrarAsistencia(String reg){
        asistencias.add(reg);
    }
}
