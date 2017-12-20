package android.whereismycar;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.whereismycar.Domain.Car;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dekeeu on 20/12/2017.
 */

public class CarListAdapter extends ArrayAdapter {
    private final Activity context;
    private final List<Car> cars;

    public CarListAdapter(Activity context1, List<Car> _cars) {
        super(context1, R.layout.carlistview_row, _cars);

        context = context1;
        cars =  _cars;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.carlistview_row, null, true);

        TextView plateTextView = (TextView)rowView.findViewById(R.id.carPlateTextView);
        TextView infoTextView = (TextView)rowView.findViewById(R.id.carInfoTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewID1);

        Car currentCar = cars.get(position);

        plateTextView.setText(currentCar.getLicensePlate());
        infoTextView.setText(currentCar.getBrand() + " - " + currentCar.getModel() + " - " + currentCar.getColor());
        imageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark);

        Log.w("CarListAdapter", plateTextView.getText().toString());

        return rowView;
    }
}
