package dis.coffeecrowd;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface CafeService {
    @GET("cafes")
    Call<ResponseBody> cafes();

    @GET("cafes")
    Call<List<Cafe>> all();

    @GET("cafes/{id}")
    Call<Cafe> cafe(@Path("id") int id);

    @GET("coffees/{cafeId}")
    Call<ResponseBody> coffees(@Path("cafeId") int cafeId);
}
