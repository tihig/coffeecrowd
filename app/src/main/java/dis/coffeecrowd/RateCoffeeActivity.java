package dis.coffeecrowd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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

public class RateCoffeeActivity extends AppCompatActivity {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final JsonAdapter<Review> jsonAdapter = new Moshi.Builder().build().adapter(Review.class);

    private Coffee coffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_coffee);

        Intent intent = getIntent();
        coffee = (Coffee) intent.getSerializableExtra("coffee");

        ((TextView) findViewById(R.id.coffee_name)).setText(coffee.name);

        Button rateButton = (Button) findViewById(R.id.send_rating_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer taste = Math.round(((RatingBar) findViewById(R.id.taste_rating)).getRating());
                Integer size = Math.round(((RatingBar) findViewById(R.id.size_rating)).getRating());
                Integer roast = Math.round(((RatingBar) findViewById(R.id.roast_rating)).getRating());
                Review review = new Review(coffee.id, 1, taste, size, roast);

                RequestBody body = RequestBody.create(JSON, jsonAdapter.toJson(review));
                Request request = new Request.Builder()
                        .url("http://kahvi-backend.herokuapp.com/reviews")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        finish();
                    }
                });
            }
        });
    }
}
