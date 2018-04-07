package Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.R;


public class MainActivity extends Activity {

    TextView text = null;
    private Intent intent = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////Récupération des views/////////////////
        text = findViewById(R.id.text);
    }
    public void onScreenClick(View view) {

        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        //Ne rien faire
    }

}
