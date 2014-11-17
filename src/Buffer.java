import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by joaquin on 30/10/14.
 */
public class Buffer {
    private LinkedBlockingQueue<String> list;
    private Integer limit;
    public static final Integer DEFAULT_LIMIT = 20;

    private final Object lock = new Object();

    public Buffer(){
        this(DEFAULT_LIMIT);
    }

    public Buffer(Integer limit){
        this.limit = limit;
        list = new LinkedBlockingQueue<String>(limit);
    }

    public void addLineToBuffer(String line) {
//    public void addLineToBuffer(String line) throws InterruptedException{
//        synchronized (lock) {
//            boolean added = true;
//            while(added){
//                if(getListSize() < limit){
//                    lock.notifyAll();
        list.add(line);
//        notifyAll();
//                    added = false;
//                }else{
//                    System.out.println("reader waiting");
//                    lock.wait();
//                }
//            }
//        }
    }

    public String processLineFromBuffer() {
//    public String processLineFromBuffer() throws InterruptedException{
//        synchronized (lock) {
//            String line = null;
//
//            while (line == null) {
//                if(getListSize() > 0){
//                    lock.notifyAll();
//                    line = list.poll();
//                }else{
//                    lock.wait();
//                }
//            }
//            return line;
//        }
        return list.poll();
    }

    public int getListSize() {
        return list.size();
    }

    public int getLimit() {
        return limit;
    }
}
