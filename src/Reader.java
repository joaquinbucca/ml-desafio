import java.io.BufferedReader;

/**
 * Created by joaquin on 30/10/14.
 */
public class Reader extends Thread {

    private Buffer buffer;
    private BufferedReader input;

    public Reader(Buffer buffer, BufferedReader input){
        this.buffer= buffer;
        this.input= input;
    }

    @Override
    public void run() {
        String line = null;
//        while (true){
            try {
                while ((line = input.readLine()) != null) {
                    if(!line.equals(" ")){
                        buffer.addLineToBuffer(line);
                    }
                }
                System.out.println("Lei todo: " + System.currentTimeMillis());
            }catch (Exception e){
                e.printStackTrace();
            }
//        }
    }
}
