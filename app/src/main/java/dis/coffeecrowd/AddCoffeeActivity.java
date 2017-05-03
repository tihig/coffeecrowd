package dis.coffeecrowd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddCoffeeActivity extends AppCompatActivity {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final JsonAdapter<CoffeeStub> jsonAdapter = new Moshi.Builder().build().adapter(CoffeeStub.class);

    private Cafe cafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);

        Intent intent = getIntent();
        cafe = (Cafe) intent.getSerializableExtra("cafe");
        setTitle(cafe.name);

        Button rateButton = (Button) findViewById(R.id.add_coffee_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.add_coffee_name)).getText().toString();
                Double price = Double.parseDouble(((EditText) findViewById(R.id.add_coffee_price)).getText().toString());

                CoffeeStub coffeeStub = new CoffeeStub(cafe.id, name, price);

                RequestBody body = RequestBody.create(JSON, jsonAdapter.toJson(coffeeStub));
                Request request = new Request.Builder()
                        .url("http://kahvi-backend.herokuapp.com/coffees")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            finish();
                        }
                    }
                });
            }
        });
    }
}
