package TutoriasVista;

import TutoriasModelo.Estudiante;
import TutoriasModelo.Tutor;
import TutoriasModelo.TutoriasControl;
import java.util.Scanner;

public class TutoriaVista {
    public static TutoriasControl tc = new TutoriasControl();
    public static Scanner tcl = new Scanner (System.in);
    public static void main(String[] args) {
        String opc;
        do {
            System.out.println("BIENVENIDO A LA PLATAFORMA DE TUTORIAS ACADEMICAS");
            System.out.println("-------------------------------------------------");
            System.out.println("1. Registrar docente tutor");
            System.out.println("2. Registrar estudiante");
            System.out.println("3. Registrar tutoria");
            System.out.println("4. Mostrar estadisticas");
            System.out.println("5. Salir");
            opc = tcl.nextLine().trim();
            switch (opc) {
                case "1":
                    registrarTutor();
                    break;
                case "2":
                    registrarEstudiante();
                    break;
                case "3":
                    
                    break;
                case "4":
                    
                    break;
                case "5":
                    
                    break;
                default:
                    System.out.println("Seleccione una opcion correcta");;
            }
        } while (opc.equals("5"));
    }
    public static void registrarTutoria(){
        System.out.println("Ingrese el nombre de la tutoria");
    }
    public static void registrarTutor(){
        System.out.println("Ingrese el nombre");
        String nom = tcl.nextLine().trim();
        System.out.println("Ingrese la cedula");
        String ced = tcl.nextLine().trim();
        System.out.println("Ingrese el correo");
        String mail = tcl.nextLine().trim();
        System.out.println("Ingrese la materia");
        String mat = tcl.nextLine().trim();   
        boolean verifi = true;
        for (Tutor t : tc.tutores) {
            if (t.cedula.equals(ced)) {
                verifi = false;
            }
        }
        if (verifi) {
            tc.ingresarTutor(new Tutor(nom, ced, mail, mat));
        }else
            System.out.println("Cedula ya registrada");
    }
    public static void registrarEstudiante(){
        System.out.println("Ingrese el nombre");
        String nom = tcl.nextLine().trim();
        System.out.println("Ingrese la cedula");
        String ced = tcl.nextLine().trim();
        System.out.println("Ingrese el correo");
        String mail = tcl.nextLine().trim();
        boolean verifi = true;
        for (Estudiante e : tc.estudiantes) {
            if (e.cedula.equals(ced)) {
                verifi = false;
            }
        }
        if (verifi) {
            tc.ingresarEstudiante(new Estudiante(nom, ced, mail));
        }else
            System.out.println("Cedula ya registrada");
    }
}
