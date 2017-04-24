package dis.coffeecrowd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CoffeeListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private static class CustomArrayAdapter extends
         ArrayAdapter<CoffeeDetails> {

    public CustomArrayAdapter(Context context, CoffeeDetails[] coffees) {
        super(context, R.layout.list_item, R.id.title, coffees);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CoffeeDetails coffee = (CoffeeDetails) parent.getAdapter().getItem(position);
        startActivity(new Intent(this, coffee.activityClass));
    }

}
