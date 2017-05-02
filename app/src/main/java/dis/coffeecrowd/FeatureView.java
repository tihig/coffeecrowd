package dis.coffeecrowd;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Helper class that sets each coffees title and description into view
 */
public final class FeatureView extends FrameLayout {

    public FeatureView(Context context) {
        super(context);

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.list_item, this);
    }


    public synchronized void setName(String name) {
        ((TextView) (findViewById(R.id.name))).setText(name);
    }

    public synchronized void setPrice(Double price) {
        ((TextView) (findViewById(R.id.price))).setText(price.toString());
    }

    public synchronized void setAverageTaste(Double averageTaste) {
        ((TextView) (findViewById(R.id.average_taste))).setText(averageTaste.toString());
    }

    public synchronized void setAverageSize(Double averageSize) {
        ((TextView) (findViewById(R.id.average_size))).setText(averageSize.toString());
    }

    public synchronized void setAverageRoast(Double averageRoast) {
        ((TextView) (findViewById(R.id.average_roast))).setText(averageRoast.toString());
    }

    public synchronized void setInfo() {
        ((TextView) (findViewById(R.id.info))).setText("Click to rate");
    }

}

