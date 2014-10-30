import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static Map<String, Map<String, Statistics>> map= new HashMap<String, Map<String, Statistics>>();


    public static void main(String[] args) {

        System.out.println("Hello World!");
//        System.out.println("args = " + args[0]);
        Process process= new Process();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        int counter= 0;
        try {
            while( (line = input.readLine()) != null ) {
//                System.out.println(line);
                process.processLine(line);
                counter++;
                System.out.println("counter = " + counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    //metodo1, primero tiene q leer una linea como las del access log y entenderla, osea sacar datos de una linea
    //metodo2, una vez traducida la linea, se fija de q minuto es,
    //                              si es del minuto q se esta leyendo, se guarda en el mapa con las est
    //                              si no lo es, se escribe en disco los datos del mapa (mongoDB?) y se
    //                                          resetea guardando los datos de esa ultima linea
    // mapa del tipo Map(ipFrom, Map(ipTo, Stadistics))
    // forma de leer las cosas en paralelo?? cachear el map ??
    // hacer una vista con gráficos asi se puede visualizar un poco el resultado..
    // ademas de ser guardado en base de datos se va a tener q escribir en un archivo un poco mas legible
    // separado por tabs y con los campos q dice el texto



