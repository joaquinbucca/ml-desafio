import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by joaquin on 30/10/14.
 */
public class Structure {

    private static Structure structure;

    private int counter;
    private String actualMinute;
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> map= new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Statistics>>();

    private Structure(){
        if (structure != null){
            //todo: alguna excepcion de q no
        }
    }

    public static Structure getInstance(){
        if(structure == null){
            structure= new Structure();
        }
        return structure;
    }


    public  String getActualMinute(){
        return actualMinute;
    }

    public void setActualMinute(String actualMinut){
        this.actualMinute= actualMinut;
    }

    public ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> getMap() {
        return map;
    }

    public int getCounter(){
        return counter;
    }

    public void increment(){
        counter++;
    }

    public void setMap(ConcurrentMap<Integer, ConcurrentMap<Integer, Statistics>> map) {
        this.map = map;
    }
}
