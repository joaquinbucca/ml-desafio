import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, Map<String, Statistics>> map= new HashMap<String, Map<String, Statistics>>();


    public static void main(String[] args) {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Structure.getInstance().initDB();
        routine(input);
//        todo: 50mil lineas en 2 segundos masomenos, tengo q hacer que despues del cat siga con tail -f , guardarlo en base
        //todo: de datos, ver q no reemplaze el archivo sino q le agregue lineas, y despues ver de hacerlo un poco mas eficiente
        //todo: por ahi un buffer para escribir, hacer vista??
    }

    public static void  routine(BufferedReader input){
        System.out.println("Hello World!");
        System.out.println("start:    "+System.currentTimeMillis());
//        System.out.println("args = " + args[0]);
        Buffer buffer= new Buffer(1000000);
        Reader reader= new Reader(buffer, input);
        reader.start();
        for (int i = 0; i < 19; i++) {
            Process process= new Process(buffer);
            process.start();
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



