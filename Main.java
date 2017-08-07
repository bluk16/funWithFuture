import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //printNames();

        Future<Integer> future = calculate(10);

            while(!future.isDone()) {
            System.out.println("Calculating...");
            Thread.sleep(300);
        }

        Integer result = future.get();

        System.out.println(result);

        System.out.println("===============================");

        List<Integer> rawInts = new ArrayList<>(3);
        rawInts.add(2);
        rawInts.add(10);
        rawInts.add(100);

        List<Future<Integer>> futures = calculateList(rawInts);

        for(Future<Integer> value: futures){
            Integer individualResult = value.get();
            System.out.println(individualResult);
        }
    }

    public static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static Future<Integer> calculate(Integer input) {

        Callable<Integer> n = () -> {
            Thread.sleep(1000);
            return input * input;
        };

//        return executor.submit(() -> {
//            Thread.sleep(1000);
//            return input * input;
//        });

        return executor.submit(n);
    }

    public static List<Future<Integer>> calculateList(List<Integer> rawInts) throws InterruptedException {

        List<Callable<Integer>> todo = new ArrayList<Callable<Integer>>(3);

        for(Integer value: rawInts){
            Callable<Integer> n = () -> {
                Thread.sleep(1000);
                System.out.println("calculating value: "+value);
                return value * value;
            };

            todo.add(n);
        }
        return executor.invokeAll(todo);
    }
}