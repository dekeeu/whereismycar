package android.whereismycar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.whereismycar.Domain.Report;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dekeeu on 07/01/2018.
 */

public class ReportListAdapter extends ArrayAdapter {
    private final Activity context;
    private final List<Report> reports;

    public ReportListAdapter(Activity context1, List<Report> _reports) {
        super(context1, R.layout.carlistview_row, _reports);

        context = context1;
        reports =  _reports;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.reportlistview_row, null, true);

        TextView streetNameTextView = (TextView)rowView.findViewById(R.id.streetNameTextView);
        TextView streetNoTextView = (TextView)rowView.findViewById(R.id.streetNoTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewID2);

        Report currentReport = reports.get(position);

        streetNameTextView.setText(currentReport.getStreetName());

        if(currentReport.getStatus().equals("pending")){
            streetNoTextView.setText(currentReport.getStreetNo() + " - PENDING");
        }else {
            streetNoTextView.setText(currentReport.getStreetNo());
        }
        //imageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark);

        //Log.w("CarListAdapter", plateTextView.getText().toString());

        return rowView;
    }
}
