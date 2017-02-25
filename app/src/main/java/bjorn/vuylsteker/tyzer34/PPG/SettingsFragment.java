package bjorn.vuylsteker.tyzer34.PPG;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;

public class SettingsFragment extends Fragment implements View.OnClickListener {
	
	public SettingsFragment(){}

    Spinner monetUnitSpinner;
    Spinner lastScannedCountSpinner;
	Button btn;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        monetUnitSpinner = (Spinner) rootView.findViewById(R.id.spinnerMonetUnit);
        lastScannedCountSpinner = (Spinner) rootView.findViewById(R.id.spinner_last_searched);
        btn = (Button) rootView.findViewById(R.id.save_settings_btn);

        btn.setOnClickListener(this);

        ArrayList<String> monet_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.monetary_unit_items)));
        monetUnitSpinner.setSelection(monet_list.indexOf(Global.getMonetaryUnit()));
        ArrayList<String> lastScanned_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.last_searched_count_items)));
        lastScannedCountSpinner.setSelection(lastScanned_list.indexOf(String.valueOf(Global.getLastScannedCount())));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Global.setMonetaryUnit(monetUnitSpinner.getSelectedItem().toString());
        Global.setLastScannedCount(Integer.parseInt(lastScannedCountSpinner.getSelectedItem().toString()));
        Global.storeSettings();
        Toast.makeText(getActivity().getBaseContext(), "Settings were successfully stored", Toast.LENGTH_SHORT).show();
    }
}
