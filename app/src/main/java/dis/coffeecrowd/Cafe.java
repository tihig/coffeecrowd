package dis.coffeecrowd;

import com.google.gson.annotations.SerializedName;

public class Cafe {

    @SerializedName("id")
    public final Integer cafeId;
    @SerializedName("name")
    public final String name;
    @SerializedName("googleId")
    public final Integer googleId;
    @SerializedName("lat")
    public Integer lat;
    @SerializedName("lon")
    public Integer lon;

    public Cafe(Integer cafeId, String name , Integer googleId, Integer lat, Integer lon) {
        this.cafeId = cafeId;
        this.name = name;
        this.googleId = googleId;
        this.lat = lat;
        this.lon = lon;
    }
    public Cafe(Integer cafeId, String name , Integer googleId) {
        this.cafeId = cafeId;
        this.name = name;
        this.googleId = googleId;
    }
}
