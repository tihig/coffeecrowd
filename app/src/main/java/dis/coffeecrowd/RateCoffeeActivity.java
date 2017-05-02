package dis.coffeecrowd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RateCoffeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_coffee);

        Intent intent = getIntent();
        CoffeeDetails coffee = (CoffeeDetails) intent.getSerializableExtra("coffee");

        ((TextView) findViewById(R.id.coffee_name)).setText(coffee.name);
    }
}
