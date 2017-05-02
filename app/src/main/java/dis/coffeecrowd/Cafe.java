package dis.coffeecrowd;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cafe implements Serializable {

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("googleId")
    public String googleId;
    @SerializedName("lat")
    public Double lat;
    @SerializedName("lon")
    public Double lon;
    @SerializedName("coffees")
    public List coffee = null;


        public class Coffee {

            @SerializedName("id")
            public Integer id;
            @SerializedName("name")
            public String name;
            @SerializedName("price")
            public Double price;
            @SerializedName("averageTaste")
            public Double averageTaste;
            @SerializedName("averageRoast")
            public Double averageRoast;
            @SerializedName("averageSize")
            public Double averageSize;

        }
    }



