import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author jql
 * @create 2018-02-07 15:23
 * @desc 线程demo
 **/
public class ThreadDemo {

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        List<Future<String>> results=new ArrayList<Future<String>>();

        for(int i=0;i<5;i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }

        for(Future<String> fs :results) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
class TaskWithResult implements Callable<String> {
    private int id;
    public TaskWithResult(int id) {
        this.id=id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult "+id;
    }
}