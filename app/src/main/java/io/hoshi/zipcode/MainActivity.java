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

    private static int DIALOG_START = 1;
    private static int DIALOG_REACTIVE = 2;

    private View startButton;
    private View reactiveButton;
    private TextView textView;

    private ZipCodeAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start);
        reactiveButton = findViewById(R.id.reactive);
        textView = (TextView) findViewById(R.id.textview);

        api = new Retrofit.Builder()
                .baseUrl(ZipCodeAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZipCodeAPI.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askZipCode(DIALOG_START);
            }
        });

        reactiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askZipCode(DIALOG_REACTIVE);
            }
        });
    }

    private void askZipCode(int dialogCode) {
        EditTextDialog dialog = EditTextDialog.newInstance(dialogCode);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDismiss(int dialogCode, int whichButton, String text) {
        if (dialogCode == DIALOG_START && whichButton == DialogInterface.BUTTON_POSITIVE) {
            query(text);
        } else if (dialogCode == DIALOG_REACTIVE && whichButton == DialogInterface.BUTTON_POSITIVE) {
            queryReactive(text);
        }
    }

    private void query(String zipCode) {
        textView.setText("zipCode = " + zipCode);

        Call<ZipCodeResult> call = api.zipCodeSearch(zipCode, ZipCodeAPI.OUTPUT_JSON, ZipCodeAPI.API_KEY);
        call.enqueue(new Callback<ZipCodeResult>() {
            @Override
            public void onResponse(Call<ZipCodeResult> call, Response<ZipCodeResult> response) {
                textView.append("\n\n" + response.body());
            }

            @Override
            public void onFailure(Call<ZipCodeResult> call, Throwable error) {
                textView.append("\n\n" + error);
            }
        });
    }

    private void queryReactive(String zipCode) {
        textView.setText("reactive zipCode = " + zipCode);
    }
}
