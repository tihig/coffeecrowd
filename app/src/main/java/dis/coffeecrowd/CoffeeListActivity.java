package dis.coffeecrowd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CoffeeListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private Cafe cafe;
    private Button addCoffeeButton;

    private static class CustomArrayAdapter extends
         ArrayAdapter<Coffee> {

    public CustomArrayAdapter(Context context, Coffee[] coffees) {
        super(context, R.layout.list_item, R.id.title, coffees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeatureView featureView;
        if (convertView instanceof FeatureView) {
            featureView = (FeatureView) convertView;
        } else {
            featureView = new FeatureView(getContext());
        }

        Coffee coffee = getItem(position);

        featureView.setName(coffee.name);
        featureView.setPrice(coffee.price);
        featureView.setAverageTaste(coffee.averageTaste);
        featureView.setAverageSize(coffee.averageSize);
        featureView.setAverageRoast(coffee.averageRoast);
        featureView.setInfo();

        Resources resources = getContext().getResources();
        String name = coffee.name;
        featureView.setContentDescription(name);

        return featureView;
    }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_list);
        ListView list = (ListView) findViewById(R.id.list);

        ListAdapter adapter = new CustomArrayAdapter (this, CoffeeDetailsList.COFFEES);

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setEmptyView(findViewById(R.id.empty));

        addCoffeeButton = (Button) findViewById(R.id.button_add_coffee);
        addCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddCoffeeActivity();
            }
        });

        Intent intent = getIntent();
        cafe = (Cafe) intent.getSerializableExtra("cafe");
        setTitle(cafe.name);
    }

    private void launchAddCoffeeActivity() {
        Intent intent = new Intent(this, AddCoffeeActivity.class);
        intent.putExtra("cafe", cafe);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            return true;
    }

    //Opens coffee rating view when clicked
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Coffee coffee = (Coffee) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, coffee.activityClass);
        intent.putExtra("coffee", coffee);
        startActivity(intent);
    }

}
