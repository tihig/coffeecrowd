package dis.coffeecrowd;

import android.util.Log;

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
import retrofit2.Response;
import retrofit2.Retrofit;


public class CoffeeCaller {
    private Coffee[] coffees;

    public  CoffeeCaller(){

    }
    public Coffee[] getCoffees(int cafeID) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kahvi-backend.herokuapp.com/")
                .client(client)
                .build();

        final CafeService service = retrofit.create(CafeService.class);

        final Call<ResponseBody> call = service.coffees(cafeID);

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
                    JsonAdapter<List<Coffee>> adapter = moshi.adapter(listMyData);

                    List<Coffee> coffeelist = adapter.fromJson(json);
                    Coffee[] coffeearray = coffeelist.toArray(new Coffee[coffeelist.size()]);
                    coffees = coffeearray;
                    setCoffees(coffees);
                    Log.w("Retrofit@Coffees", Integer.toString(coffees.length));
                   // System.out.println(coffees[0].name);


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

        //System.out.println(coffees[0].name);

        return coffees;

    }

    public void setCoffees(Coffee[] coffees){
        this.coffees = coffees;
    }

}
