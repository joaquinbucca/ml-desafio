import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by joaquin on 30/10/14.
 */
public class Buffer {
    private LinkedBlockingQueue<String> list;
    private int limit;
    public static final Integer DEFAULT_LIMIT = 20;

    private final Object lock = new Object();


    public Buffer(int limit){
        this.limit = limit;
        list = new LinkedBlockingQueue<String>(limit);
    }

    public void addLineToBuffer(String line) {
        list.add(line);
    }

    public String processLineFromBuffer() {
        return list.poll();
    }

    public int getListSize() {
        return list.size();
    }

    public int getLimit() {
        return limit;
    }
}
