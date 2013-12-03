
package me.habel.mybeaconsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.TextView;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import java.text.DecimalFormat;
import java.util.Collection;

public class MainActivity extends Activity implements IBeaconConsumer {

    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iBeaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iBeaconManager.unBind(this);
    }

    @Override
    public void onIBeaconServiceConnect() {
        iBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                if (iBeacons.size() > 0) {
                    double distance = iBeacons.iterator().next().getAccuracy();
                    DecimalFormat decimalFormat = new DecimalFormat("##.##");
                    double distanceFormatted = Double.valueOf(decimalFormat.format(distance));
                    TextView distanceTextView = (TextView) findViewById(R.id.am_tv_distance);
                    distanceTextView.setText(String.valueOf(distanceFormatted) + " m");
                }
            }
        });
        try {
            iBeaconManager.startRangingBeaconsInRegion(new Region("com.devfright.myRegion", null,
                    null, null));
        } catch (RemoteException e) {

        }
    }
}
