import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Links links = new Links();
        ExecutorService executorService;

        try {

            executorService = Executors.newFixedThreadPool(links.getUrlCategories().size());

            for (int i = 0; i < links.getUrlCategories().size(); i++)
                executorService.submit(new Parser(links, links.getUrlCategories().get(i)));

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);

        } catch (IllegalArgumentException e) {
            System.out.println("Resource not responding");
        }
    }

}
