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


    public synchronized void setTitle(String title) {
        ((TextView) (findViewById(R.id.title))).setText(title);
    }


    public synchronized void setDescription(String description) {
        ((TextView) (findViewById(R.id.description))).setText(description);
    }

    public synchronized void setInfo() {
        ((TextView) (findViewById(R.id.info))).setText("Click to rate");
    }

}

