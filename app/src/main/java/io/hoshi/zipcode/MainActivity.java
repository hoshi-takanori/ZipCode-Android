package io.hoshi.zipcode;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements EditTextDialog.OnDismissListener {

    private View startButton;
    private TextView textView;

    private ZipCodeAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start);
        textView = (TextView) findViewById(R.id.textview);

        api = new Retrofit.Builder()
                .baseUrl(ZipCodeAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZipCodeAPI.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askZipCode();
            }
        });
    }

    private void askZipCode() {
        EditTextDialog dialog = new EditTextDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDismiss(int which, String text) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            query(text);
        }
    }

    private void query(String zipCode) {
        textView.setText("zipCode = " + zipCode);

        Call<ZipCodeResult> call = api.zipCodeSearch(zipCode, ZipCodeAPI.OUTPUT_JSON, ZipCodeAPI.API_KEY);
        call.enqueue(new Callback<ZipCodeResult>() {
            @Override
            public void onResponse(Call<ZipCodeResult> call, Response<ZipCodeResult> response) {
                ZipCodeResult result = response.body();
                textView.append("\n\nresult.resultInfo.count = " + result.resultInfo.count);
                if (result.feature != null) {
                    for (int i = 0; i < result.feature.length; i++) {
                        textView.append("\nresult.feature[" + i + "].property.address = " + result.feature[i].property.address);
                        textView.append("\nresult.feature[" + i + "].geometry.coordinates = " + result.feature[i].geometry.coordinates);
                    }
                }
            }

            @Override
            public void onFailure(Call<ZipCodeResult> call, Throwable error) {
                textView.append("\n\n" + error);
            }
        });
    }
}
