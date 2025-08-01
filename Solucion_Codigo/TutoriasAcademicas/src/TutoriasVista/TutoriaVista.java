package TutoriasVista;

import TutoriasModelo.Estudiante;
import TutoriasModelo.Tutor;
import TutoriasControlador.TutoriasControl;
import TutoriasModelo.Tutoria;
import TutoriasModelo.TutoriaGrupal;
import TutoriasModelo.TutoriaIndividual;
import java.util.Scanner;

public class TutoriaVista {
    public static TutoriasControl tc = new TutoriasControl();
    public static Scanner tcl = new Scanner (System.in);
    public static void main(String[] args) {
        tc.cargarTutoresDesdeCSV();
        tc.cargarEstudiantesDesdeCSV();
        tc.cargarTutoriasDesdeCSV();
        String opc;
 
    do {
        System.out.println("BIENVENIDO A LA PLATAFORMA DE TUTORIAS ACADEMICAS");
        System.out.println("-------------------------------------------------");
        System.out.println("1. Registrar docente tutor");
        System.out.println("2. Registrar estudiante");
        System.out.println("3. Registrar tutoria");
        System.out.println("4. Ingresar nueva sesion");
        System.out.println("5. Registrar asistencia");
        System.out.println("6. Calcular avance y riesgo");
        System.out.println("7. Mostrar estadisticas");
        System.out.println("8. Salir");
        opc = tcl.nextLine().trim();
        
        switch (opc) {
            case "1":
                registrarTutor();
                break;
            case "2":
                registrarEstudiante();
                break;
            case "3":
                registrarTutoria();
                break;
            case "4":
                ingresarNuevaSesion();
                break;
            case "5":
                registrarAsistencia();
                break;
            case "6":
                calcular();
                break;
            case "7":
              
                tc.mostrarEstadisticas(); 

                break;
            case "8":
                System.out.println("Adiós!!");
                break;
            default:
                System.out.println("Seleccione una opción correcta");
        }
            } while (!opc.equals("8"));
        }

    
    public static void calcular(){
        Tutoria tut = buscarTutoria();
        tc.calcularAvRie(tut);
    }
    public static Tutoria buscarTutoria(){
        System.out.println("Ingrese el nombre de la tutoria");
        String nom = tcl.nextLine().trim();
        Tutoria tut = null;
        for (Tutoria t : tc.tutorias) {
            if (t.nombre.equals(nom)) {
                tut = t;
            }
        }
        return tut;
    }
    public static Estudiante buscarEstudiante(String ced){
        Estudiante est = null;
        for (Estudiante e : tc.estudiantes) {
            if (e.cedula.equals(ced)) {
                est = e;
            }
        }
        return est;
    }
    public static void registrarAsistencia(){
        Tutoria tut = buscarTutoria();
        
        if (tut instanceof TutoriaGrupal) {
            TutoriaGrupal tutg = (TutoriaGrupal) tut;
            for (int i = 1; i <= tutg.sesiones.size(); i++) {
                System.out.println("Cancelar sesion? : si/no");
                String opc = tcl.nextLine().toLowerCase().trim();
                if (opc.equalsIgnoreCase("si")) {
                    tutg.sesiones.get(i).cancelarSesion();
                }else{
                    for (Estudiante e :tutg.estudiantes) {
                        System.out.println("Secion numero " + i);
                        System.out.println("Asistencia de " + e.nombre + "asistencia/ausencia");
                        String reg = tcl.nextLine().toLowerCase().trim();
                        e.registrarAsistencia(reg);
                    } 
                }
            }
            
        }else if (tut instanceof TutoriaIndividual) {
            TutoriaIndividual tuti = (TutoriaIndividual) tut;
            for (int i = 1; i <= tuti.sesiones.size(); i++) {
                System.out.println("Cancelar sesion? : si/no");
                String opc = tcl.nextLine().toLowerCase().trim();
                if (opc.equalsIgnoreCase("si")) {
                    tuti.sesiones.get(i).cancelarSesion();
                }else{
                    System.out.println("Secion numero " + i);
                    System.out.println("Asistencia de " + tuti.estudiante.nombre + "asistencia/ausencia");
                    String reg = tcl.nextLine().toLowerCase().trim();
                    tuti.estudiante.registrarAsistencia(reg);
                }
            }
        }
    }
    public static void ingresarNuevaSesion(){
        Tutoria tut = buscarTutoria();
        if (tut != null) {
            ingresarSesion(tut);
        }else
            System.out.println("No existe la tutoria");
    }
    public static void ingresarSesion(Tutoria t){
        System.out.println("Ingrese datos de la sesion");
        System.out.println("fecha: dia-mes-anio");
        String fecha = tcl.nextLine().trim();
        System.out.println("duracion en minutos: ");
        int duracion = tcl.nextInt();
        tc.ingresarSesion(fecha, duracion, t);
    }
    public static void registrarTutoria() {
    System.out.println("Ingrese el nombre de la tutoria");
    String nom = tcl.nextLine().trim();
    System.out.println("Seleccione el tipo de tutoria : individual/grupal");
    String tipo = tcl.nextLine().toLowerCase().trim();
    System.out.println("Seleccione el tipo de asistencia : virtual/presencial");
    String tipoA = tcl.nextLine().toLowerCase().trim();
    System.out.println("Ingrese la cedula del Tutor");
    String ced = tcl.nextLine().trim();
    boolean verifi = tc.verificarCedula(ced, 1);
    System.out.println((verifi) ? "Cédula no encontrada" : "Tutoria creada");
    Tutoria t = tc.ingresarTutoria(nom, ced, tipo, tipoA);
    ingresarSesion(t);
    ingresarEstudiante(t);
    tc.guardarTutoriasEnCSV(); 
    System.out.println("Tutoria registrada y guardada en CSV.");
    }

    public static void ingresarEstudiante(Tutoria t){
        if (t instanceof TutoriaIndividual) {
            System.out.println("Ingrese la cedula del estudiante");
            String ced = tcl.nextLine().trim();
            boolean verifi = tc.verificarCedula(ced, 2);
            if (verifi) {
                Estudiante est = buscarEstudiante(ced);
                t.agregarEstudiante(est);
            }
        }else if (t instanceof TutoriaGrupal) {
            String opc;
            do {
                System.out.println("Ingrese la cedula del estudiante");
                String ced = tcl.nextLine().trim();
                boolean verifi = tc.verificarCedula(ced, 2);
                if (verifi) {
                    Estudiante est = buscarEstudiante(ced);
                    t.agregarEstudiante(est);
                }  
                System.out.println("Ingresar un nuevo alumno : si/no");
                opc = tcl.nextLine().toLowerCase().trim();
            } while (opc.equals("si"));
            
        }
    }
    public static void registrarTutor() {
    System.out.println("Ingrese el nombre");
    String nom = tcl.nextLine().trim();
    System.out.println("Ingrese la cedula");
    String ced = tcl.nextLine().trim();
    System.out.println("Ingrese el correo");
    String mail = tcl.nextLine().trim();
    System.out.println("Ingrese la materia");
    String mat = tcl.nextLine().trim();   
    boolean verifi = tc.verificarCedula(ced, 1);

    if (verifi) {
        tc.ingresarTutor(new Tutor(nom, ced, mail, mat));
        tc.guardarTutoresEnCSV(); // <- AÑADE ESTA LÍNEA
        System.out.println("Tutor registrado y guardado en CSV.");
    } else {
        System.out.println("Cédula ya registrada.");
     }
    }

   public static void registrarEstudiante() {
    System.out.println("Ingrese el nombre");
    String nom = tcl.nextLine().trim();
    System.out.println("Ingrese la cedula");
    String ced = tcl.nextLine().trim();
    System.out.println("Ingrese el correo");
    String mail = tcl.nextLine().trim();
    boolean verifi = tc.verificarCedula(ced, 2);
    if (verifi) {
        tc.ingresarEstudiante(new Estudiante(nom, ced, mail));
        tc.guardarEstudiantesEnCSV(); // <- AÑADE ESTA LÍNEA
        System.out.println("Estudiante registrado y guardado en CSV.");
    } else {
        System.out.println("Cédula ya registrada.");
        }
    }

}
