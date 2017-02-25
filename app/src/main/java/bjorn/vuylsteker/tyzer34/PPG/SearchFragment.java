package bjorn.vuylsteker.tyzer34.PPG;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;

import bjorn.vuylsteker.tyzer34.PPG.util.FragmentIntentIntegrator;
import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.db.DataHandler;

public class SearchFragment extends Fragment implements View.OnClickListener, ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    private EditText et_byName, et_byCode;
    private Button btn_byName, btn_byCode, btn_scanBarcode;
    private ListView listView;

    DataHandler handler;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    LinkedList<String[]> listItems = new LinkedList<String[]>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String[]> adapter;

    String filename = "last_searched";

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        et_byName = (EditText) rootView.findViewById(R.id.searchText_byName);
        et_byCode = (EditText) rootView.findViewById(R.id.searchText_byCode);
        btn_byName = (Button) rootView.findViewById(R.id.searchButton_byName);
        btn_byCode = (Button) rootView.findViewById(R.id.searchButton_byCode);
        btn_scanBarcode = (Button) rootView.findViewById(R.id.scanBarcode);
        listView = (ListView) rootView.findViewById(R.id.listView_lastSearched);

        btn_byName.setOnClickListener(this);
        btn_byCode.setOnClickListener(this);
        btn_scanBarcode.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        et_byCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() > 6) {
                    btn_byCode.setEnabled(true);
                } else {
                    btn_byCode.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        adapter = new ArrayAdapter<String[]>(getActivity().getBaseContext(), R.layout.simple_list_item_2, android.R.id.text1, listItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                String[] entry = listItems.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);
                return view;
            }
        };
        listView.setAdapter(adapter);
        loadLastSearched();

        return rootView;
    }

    public void loadLastSearched() {
        listItems.clear();
        try {
            FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_APPEND);

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getActivity().openFileInput(filename));
            BufferedReader r = new BufferedReader(inputStreamReader);
            String line;
            while ((line = r.readLine()) != null) {
                // ADD TO listView and notify adapter
                String[] entry = TextUtils.split(line, ";");
                if (entry.length > 1) {
                    listItems.add(entry);
                    adapter.notifyDataSetChanged();
                }
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search(String query){
        if (!query.equals("")) {
            Global.setLastSearch(query);
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.hideSoftKeyboard();
            }
            MainActivity.replaceFragment(new SearchResultsFragment());
        }
    }

    public String getQueryByBarcode(String barcode, boolean add){
        String name, number, type;
        name = "";
        type = "";
        number = "";
        handler = Global.getDbHandler();
        handler.open();
        Cursor c = handler.searchDataByBarcode(barcode);
        if (c == null) {
            Toast.makeText(getActivity().getBaseContext(), "Cursor c = NULL", Toast.LENGTH_LONG).show();
        } else if (c.moveToFirst()) {
            do {
                name = c.getString(0);
                type = c.getString(1);
                number = c.getString(2);
            } while (c.moveToNext());
        }
        handler.close();

        if (!name.equals("")) {
            Toast.makeText(getActivity().getBaseContext(), name + " - " + type + " - " + number, Toast.LENGTH_LONG).show();
            if (add) {
                listItems.addFirst(new String[]{name, type + " - " + number});
                adapter.notifyDataSetChanged();
                try {
                    OutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                    String toWrite = "";
                    for (int i = 0; i < listItems.size(); i++) {
                        if (i  < Global.getLastScannedCount()) {
                            String[] entry = listItems.get(i);
                            toWrite += entry[0] + ";" + entry[1] + "\n";
                        }
                    }
                    outputStream.write(toWrite.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String query = name + " " + type + " " + number;
            query = query.replace("(", "").replace(")", "").replace("/", "");
            return query;
        } else {
            Toast.makeText(getActivity().getBaseContext(), "POP! was not found within the database", Toast.LENGTH_LONG).show();
            return "";
        }
    }

    @Override
    public void onClick(View view) {
        String query = "";
        String barcode = "";
        switch (view.getId()) {

            case R.id.searchButton_byName:
                query = et_byName.getText().toString();
                search(query);
                break;

            case R.id.searchButton_byCode:
                barcode = et_byCode.getText().toString();
                query = getQueryByBarcode(barcode, true);
                search(query);
                break;

            case R.id.scanBarcode:
                FragmentIntentIntegrator scanIntegrator = new FragmentIntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent != null) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                et_byCode.setText(scanContent);
                String query = getQueryByBarcode(scanContent, true);
                search(query);
            } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] entry = listItems.get(position);
        String query = entry[0] + " " + entry[1].replace(" - ", " ");
        search(query);
    }

    public void removeItem(int pos){
        listItems.remove(pos);
        try {
            OutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            String toWrite = "";
            for (int i = 0; i < listItems.size(); i++) {
                if (i  < Global.getLastScannedCount()) {
                    String[] entry = listItems.get(i);
                    toWrite += entry[0] + ";" + entry[1] + "\n";
                }
            }
            outputStream.write(toWrite.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainActivity.replaceFragment(new SearchFragment());
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final String itemName = listItems.get(position)[0];
        AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
        alertDialog.setTitle("Remove Item");
        alertDialog.setMessage("Are you sure you want to remove " + itemName + " from your last scanned list?");
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                removeItem(position);
            }
        });
        alertDialog.show();
        return false;
    }
}