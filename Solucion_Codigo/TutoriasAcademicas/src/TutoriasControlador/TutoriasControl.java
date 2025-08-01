package TutoriasControlador;

import java.io.*;
import java.util.*;
import TutoriasModelo.*;

public class TutoriasControl {

    public ArrayList<Tutoria> tutorias;
    public ArrayList<Tutor> tutores;
    public ArrayList<Estudiante> estudiantes;

     public TutoriasControl() {
        this.tutores = new ArrayList<>();  
        this.estudiantes = new ArrayList<>();  
        this.tutorias = new ArrayList<>();  
    }

    public void cargarTutoresDesdeCSV() {
        File archivo = new File("tutores.csv");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();  
                System.out.println("Archivo 'tutores.csv' no existía, pero fue creado.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo 'tutores.csv'.");
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                String nombre = datos[0];
                String cedula = datos[1];
                String correo = datos[2];
                String materia = datos[3];
                tutores.add(new Tutor(nombre, cedula, correo, materia));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los tutores desde el archivo CSV.");
        }
    }

    public void cargarEstudiantesDesdeCSV() {
    File archivo = new File("estudiantes.csv");
    
        // Verificamos si el archivo existe; si no, lo creamos vacío
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();  // Crea el archivo si no existe
                System.out.println("Archivo 'estudiantes.csv' no existía, pero fue creado.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo 'estudiantes.csv'.");
                return;
            }
        }

        // Leemos el archivo CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                String nombre = datos[0];
                String cedula = datos[1];
                String correo = datos[2];
                estudiantes.add(new Estudiante(nombre, cedula, correo));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los estudiantes desde el archivo CSV.");
        }
    }


    public void cargarTutoriasDesdeCSV() {
    File archivo = new File("tutorias.csv");
    
    // Verificamos si el archivo existe; si no, lo creamos vacío
    if (!archivo.exists()) {
        try {
            archivo.createNewFile();  // Crea el archivo si no existe
            System.out.println("Archivo 'tutorias.csv' no existía, pero fue creado.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo 'tutorias.csv'.");
            return;
        }
    }

    // Leemos el archivo CSV
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] datos = line.split(",");
            String nombre = datos[0];
            String cedulaTutor = datos[1];
            String tipoTutoria = datos[2]; 
            String tipoAsistencia = datos[3];
            
       
            Tutor tutor = buscarTutorPorCedula(cedulaTutor);
            
            Tutoria tutoria = null;
            if (tipoTutoria.equalsIgnoreCase("individual")) {
                tutoria = new TutoriaIndividual(nombre, tipoAsistencia, tutor);
            } else if (tipoTutoria.equalsIgnoreCase("grupal")) {
                tutoria = new TutoriaGrupal(nombre, tipoAsistencia, tutor);
            }

          
            String[] estudiantesAsignados = datos[4].split(";");
            for (String cedEstudiante : estudiantesAsignados) {
                Estudiante est = buscarEstudiantePorCedula(cedEstudiante);
                if (est != null) {
                    tutoria.agregarEstudiante(est);
                }
            }

            tutorias.add(tutoria);
        }
    } catch (IOException e) {
        System.out.println("Error al cargar las tutorías desde el archivo CSV.");
    }
}

    // Métodos de Serialización (Guardar a archivos CSV)

    public void guardarTutoresEnCSV() {
        File archivo = new File("tutores.csv");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();  // Crea el archivo si no existe
                System.out.println("Archivo 'tutores.csv' no existía, pero fue creado.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo 'tutores.csv'.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Tutor tutor : tutores) {
                writer.write(tutor.nombre + "," + tutor.cedula + "," + tutor.correo + "," + tutor.materia);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los tutores en el archivo CSV.");
        }
    }

    public void guardarEstudiantesEnCSV() {
       File archivo = new File("estudiantes.csv");
       if (!archivo.exists()) {
           try {
               archivo.createNewFile();  // Crea el archivo si no existe
               System.out.println("Archivo 'estudiantes.csv' no existía, pero fue creado.");
           } catch (IOException e) {
               System.out.println("Error al crear el archivo 'estudiantes.csv'.");
               return;
           }
       }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
           for (Estudiante estudiante : estudiantes) {
               writer.write(estudiante.nombre + "," + estudiante.cedula + "," + estudiante.correo);
               writer.newLine();
           }
       } catch (IOException e) {
           System.out.println("Error al guardar los estudiantes en el archivo CSV.");
       }
   }

   public void guardarTutoriasEnCSV() {
       File archivo = new File("tutorias.csv");
       if (!archivo.exists()) {
           try {
               archivo.createNewFile();  // Crea el archivo si no existe
               System.out.println("Archivo 'tutorias.csv' no existía, pero fue creado.");
           } catch (IOException e) {
               System.out.println("Error al crear el archivo 'tutorias.csv'.");
               return;
           }
       }

       try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
           for (Tutoria tutoria : tutorias) {
               String estudiantesStr = "";

               // Si es una tutoria individual, solo hay un estudiante
               if (tutoria instanceof TutoriaIndividual) {
                   Estudiante est = ((TutoriaIndividual) tutoria).estudiante;
                   estudiantesStr = est.cedula; // Solo un estudiante
               } 
               // Si es una tutoria grupal, recorremos la lista de estudiantes
               else if (tutoria instanceof TutoriaGrupal) {
                   for (Estudiante est : ((TutoriaGrupal) tutoria).estudiantes) {
                       estudiantesStr += est.cedula + ";"; // Separamos por punto y coma
                   }
               }

               writer.write(tutoria.nombre + "," + tutoria.tutor.cedula + "," + tutoria.tipo + "," + 
                            estudiantesStr);  // Guardamos el tipo y los estudiantes
               writer.newLine();
           }
       } catch (IOException e) {
           System.out.println("Error al guardar las tutorías en el archivo CSV.");
       }
   }




    private Tutor buscarTutorPorCedula(String cedula) {
        for (Tutor tutor : tutores) {
            if (tutor.cedula.equals(cedula)) {
                return tutor;
            }
        }
        return null;
    }

    private Estudiante buscarEstudiantePorCedula(String cedula) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.cedula.equals(cedula)) {
                return estudiante;
            }
        }
        return null;
    }

    // Métodos para gestión de tutorías, estudiantes y sesiones

    public Tutoria ingresarTutoria(String nombre, String ced, String tipoTutoria, String tipoAsistencia) {
        Tutoria tutoria = null;
        Tutor tutor = buscarTutorPorCedula(ced);
        
        if (tipoTutoria.equalsIgnoreCase("individual")) {
            tutoria = new TutoriaIndividual(nombre, tipoAsistencia, tutor);
        } else if (tipoTutoria.equalsIgnoreCase("grupal")) {
            tutoria = new TutoriaGrupal(nombre, tipoAsistencia, tutor);
        }
        
        tutorias.add(tutoria);
        return tutoria;
    }

    public void addEstudiante(Tutoria tutoria, Estudiante estudiante) {
        tutoria.agregarEstudiante(estudiante);
    }

    public void ingresarSesion(String fecha, int duracion, Tutoria tutoria) {
        tutoria.sesiones.add(new Sesion(fecha, duracion));
    }

    public int contarSesionesCance(Tutoria tutoria) {
        int i = 0;
        for (Sesion sesion : tutoria.sesiones) {
            if (!sesion.activo) {
                i++;
            }
        }
        return i;
    }

    // Métodos para cálculo de avances y riesgos en las tutorías

    public void calcularAvRie(Tutoria tutoria) {
        if (tutoria instanceof TutoriaIndividual) {
            TutoriaIndividual tutoriaIndividual = (TutoriaIndividual) tutoria;
            for (String s : tutoriaIndividual.estudiante.asistencias) {
                if (s.equalsIgnoreCase("asistencia")) {
                    tutoriaIndividual.estudiante.asis++;
                } else if (s.equalsIgnoreCase("ausencia")) {
                    tutoriaIndividual.estudiante.ause++;
                }
            }
            tutoriaIndividual.avance = (tutoriaIndividual.estudiante.asis / tutoriaIndividual.sesiones.size()) * 100;
            tutoriaIndividual.riesgo = (contarSesionesCance(tutoriaIndividual) / tutoriaIndividual.sesiones.size()) * 10 + tutoriaIndividual.estudiante.ause;
        } else if (tutoria instanceof TutoriaGrupal) {
            TutoriaGrupal tutoriaGrupal = (TutoriaGrupal) tutoria;
            for (Estudiante e : tutoriaGrupal.estudiantes) {
                for (String s : e.asistencias) {
                    if (s.equalsIgnoreCase("asistencia")) {
                        e.asis++;
                    } else if (s.equalsIgnoreCase("ausencia")) {
                        e.ause++;
                    }
                }
                tutoriaGrupal.avance = (e.asis / tutoriaGrupal.sesiones.size()) * 100;
                tutoriaGrupal.riesgo = (contarSesionesCance(tutoriaGrupal) / tutoriaGrupal.sesiones.size()) * 10 + e.ause;
            }
        }
    }

    public void mostrarEstadisticas() {
        int totalTutorias = this.tutorias.size();
        int totalSesiones = 0;
        int sesionesCanceladas = 0;
        int totalMateriales = 0;
        int totalDuracion = 0;
        int sesionesConMateriales = 0;

        for (Tutoria tutoria : this.tutorias) {
            ArrayList<Sesion> sesiones = tutoria.sesiones;
            if (sesiones == null) continue;

            totalSesiones += sesiones.size();

            for (Sesion sesion : sesiones) {
                if (!sesion.activo) {
                    sesionesCanceladas++;
                }
                totalDuracion += sesion.duracion;

                if (sesion.materiales != null && !sesion.materiales.isEmpty()) {
                    sesionesConMateriales++;
                    totalMateriales += sesion.materiales.size();
                }
            }
        }

        System.out.println("==== Estadísticas Generales ====");
        System.out.println("Total de tutorías registradas: " + totalTutorias);
        System.out.println("Total de sesiones: " + totalSesiones);
        System.out.println("Sesiones canceladas: " + sesionesCanceladas);
        System.out.println("Sesiones con materiales: " + sesionesConMateriales);
        System.out.println("Total de recursos usados: " + totalMateriales);

        if (totalSesiones > 0) {
            double promedioDuracion = (double) totalDuracion / totalSesiones;
            System.out.printf("Duración promedio de sesiones: %.2f minutos%n", promedioDuracion);
        } else {
            System.out.println("No hay sesiones registradas para calcular duración promedio.");
        }
    }

    public void ingresarTutor(Tutor tutor) {
        tutores.add(tutor);
    }

    public void ingresarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public boolean verificarCedula(String cedula, int tipo) {
    boolean verificado = true;
    
    if (tipo == 1) { 
        if (tutores != null) {
            for (Tutor tutor : tutores) {
                if (tutor.cedula.equals(cedula)) {
                    verificado = false;
                    break;
                }
            }
        }
    } 
    
   
    else if (tipo == 2) {
        if (estudiantes != null) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.cedula.equals(cedula)) {
                    verificado = false;
                    break;
                }
            }
        }
    }
    return verificado;
}

            
            
}
