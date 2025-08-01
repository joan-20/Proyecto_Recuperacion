package TutoriasModelo;
public class Recurso {
    protected String nombre;
    protected String detalle;
    protected int cantidad;

    public Recurso() {
    }
    
    public Recurso(String nombre, String detalle, int cantidad) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.cantidad = cantidad;
    }
    
}
