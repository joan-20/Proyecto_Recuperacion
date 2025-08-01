package TutoriasModelo;

import java.util.ArrayList;

public class Sesion {
    protected String fecha;
    protected int duracion;
    public boolean activo;
    protected ArrayList<Recurso> materiales;

    public Sesion(String fecha, int duracion) {
        this.fecha = fecha;
        this.duracion = duracion;
    }
    public void cancelarSesion(){
        this.activo = false;
    }
}
