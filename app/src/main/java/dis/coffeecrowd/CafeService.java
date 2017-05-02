package dis.coffeecrowd;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CafeService {
    @GET("cafes")
    Call<ResponseBody> cafes();

    @GET("coffees")
    Call<ResponseBody> coffees();
}
