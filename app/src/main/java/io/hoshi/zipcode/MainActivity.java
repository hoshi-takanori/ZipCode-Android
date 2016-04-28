package io.hoshi.zipcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private View startButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start);
        textView = (TextView) findViewById(R.id.textview);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Hello, World!");

                Gson gson = new Gson();
                String json = "{\"ResultInfo\":{\"Count\":1},\"Feature\":[{\"Geometry\":{\"Coordinates\":\"139.74816650,35.65757726\"},\"Property\":{\"Address\":\"東京都港区芝公園\"}}]}";
                ZipCodeResult result = gson.fromJson(json, ZipCodeResult.class);
                textView.append("\n\nresult.resultInfo.count = " + result.resultInfo.count);
                for (int i = 0; i < result.feature.length; i++) {
                    textView.append("\nresult.feature[" + i + "].property.address = " + result.feature[i].property.address);
                    textView.append("\nresult.feature[" + i + "].geometry.coordinates = " + result.feature[i].geometry.coordinates);
                }
            }
        });
    }
}
