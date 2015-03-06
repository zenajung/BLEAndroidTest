package kr.co.atratech.bletest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "zena";

    private BluetoothAdapter mBluetoothAdapter;
    //private LeDeviceListAdapter mLeDeviceListAdapter;
    private static final int REQUEST_ENABLE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            //return;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

        // Initializes list view adapter.
        //mLeDeviceListAdapter = new LeDeviceListAdapter();
        //setListAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            /*
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
            */
            //mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            //mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    Log.d(TAG, "length = " + scanRecord.length);
                    Log.d(TAG, String.format( "!!!!!!!%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,", scanRecord[0], scanRecord[1], scanRecord[2], scanRecord[3], scanRecord[4], scanRecord[5], scanRecord[6], scanRecord[7], scanRecord[8], scanRecord[9], scanRecord[10], scanRecord[11], scanRecord[12], scanRecord[13], scanRecord[14], scanRecord[15]));
                    Log.d(TAG, String.format( "!!!!!!!%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,", scanRecord[0x10], scanRecord[0x11], scanRecord[0x12], scanRecord[0x13], scanRecord[0x14], scanRecord[0x15], scanRecord[0x16], scanRecord[0x17], scanRecord[0x18], scanRecord[0x19], scanRecord[0x1A], scanRecord[0x1B], scanRecord[0x1C], scanRecord[0x1D], scanRecord[0x1E], scanRecord[0x1F]));
                    Log.d(TAG, String.format( "!!!!!!!%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,", scanRecord[0x20], scanRecord[0x21], scanRecord[0x22], scanRecord[0x23], scanRecord[0x24], scanRecord[0x25], scanRecord[0x26], scanRecord[0x27], scanRecord[0x28], scanRecord[0x29], scanRecord[0x2A], scanRecord[0x2B], scanRecord[0x3C], scanRecord[0x2D], scanRecord[0x2E], scanRecord[0x2F]));
                    Log.d(TAG, String.format( "!!!!!!!%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,%02X,", scanRecord[0x30], scanRecord[0x31], scanRecord[0x32], scanRecord[0x33], scanRecord[0x34], scanRecord[0x35], scanRecord[0x36], scanRecord[0x37], scanRecord[0x38], scanRecord[0x39], scanRecord[0x3A], scanRecord[0x3B], scanRecord[0x3C], scanRecord[0x3D]));
                    Log.d(TAG,"Device ="+device.getName());
                    //Log.d(TAG,"Device ="+device.getUuids()[0].toString());
                    /*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLeDeviceListAdapter.addDevice(device);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                    */
                }
            };

}
