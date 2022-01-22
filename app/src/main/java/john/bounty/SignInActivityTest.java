package john.bounty;



import android.app.Activity;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SignInActivityTest {
    private TextView status;
    private Context context;
    private Activity activity;
    private ProgressBar progressBar;

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public Future<?> calculate() {
        return executor.submit(() -> {
            try {



                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  ;
        });
    }

}
