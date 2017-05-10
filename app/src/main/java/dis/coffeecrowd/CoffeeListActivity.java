package dis.coffeecrowd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

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
        if (coffee != null) {
        featureView.setName(coffee.name);
        featureView.setPrice(coffee.price);
        if(coffee.averageTaste != null) {
            featureView.setAverageTaste(coffee.averageTaste);
        }
        if(coffee.averageSize != null) {
            featureView.setAverageSize(coffee.averageSize);
        }
        if(coffee.averageRoast != null) {
            featureView.setAverageRoast(coffee.averageRoast);
        }
        featureView.setInfo();

        Resources resources = getContext().getResources();
        String name = coffee.name;
        featureView.setContentDescription(name);

        return featureView;
        }
        return null;
    }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_list);
        final ListView list = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        cafe = (Cafe) intent.getSerializableExtra("cafe");

        addCoffeeButton = (Button) findViewById(R.id.button_add_coffee);
        addCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddCoffeeActivity();
            }
        });

        if(cafe.name != null) {
            setTitle(cafe.name);
        }else{
            setTitle("Kahvila");
        }
        //Fetch coffees from DB
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kahvi-backend.herokuapp.com/")
                .client(client)
                .build();

        final CafeService service = retrofit.create(CafeService.class);

        final Call<ResponseBody> call = service.coffees(cafe.id);

        //Fetching cafes from DB
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {

                    String json = response.body().string();
                    System.out.println(response.toString());
                    Log.w("Retrofit@Response", response.body().string() + "Kaikki OK!");


                    Moshi moshi = new Moshi.Builder().build();
                    Type listMyData = Types.newParameterizedType(List.class, Coffee.class);
                    JsonAdapter<List<Coffee>> JSONadapter = moshi.adapter(listMyData);

                    List<Coffee> coffeelist = JSONadapter.fromJson(json);
                    Coffee[] coffeearray = coffeelist.toArray(new Coffee[coffeelist.size()]);

                    Log.w("Retrofit@Coffees", Integer.toString(coffeearray.length));
                    // System.out.println(coffees[0].name);

                    final Coffee[] empty = new Coffee[]{new Coffee(cafe.id,
                            "Kahvilalla ei ole kahveja",
                            0.0,
                            0.0,
                            0.0,
                            0.0)};
                    ListAdapter adapter= new CustomArrayAdapter(CoffeeListActivity.this, empty );
                    if(coffeearray != null) {
                        adapter = new CustomArrayAdapter(CoffeeListActivity.this, coffeearray);
                    }
                    System.out.println(adapter);

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(CoffeeListActivity.this);
                    list.setEmptyView(findViewById(R.id.empty));




                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("Retrofit@Error", "Error in Json parsing");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.w("Retrofit@Failure", "Failed to call server");
            }

        });

                    // System.out.println(coffees[0].name);


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
        if(coffee.price > 0.0) {
            Intent intent = new Intent(this, RateCoffeeActivity.class);
            intent.putExtra("coffee", coffee);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Lisää uusi kahvi vihreästä napista", Toast.LENGTH_LONG).show();
        }
    }

}
