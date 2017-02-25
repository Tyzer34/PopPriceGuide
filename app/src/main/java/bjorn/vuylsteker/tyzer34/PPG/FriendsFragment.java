package bjorn.vuylsteker.tyzer34.PPG;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;

public class FriendsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    ArrayList<String> friendList = new ArrayList<String>();

    LinearLayout scrollView;
    EditText et;
    Button addButton;

    public final static String filename = "friendlist";

	public FriendsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        scrollView = (LinearLayout) rootView.findViewById(R.id.ScrollView_friends);
        et = (EditText) rootView.findViewById(R.id.addFriendText);
        addButton = (Button) rootView.findViewById(R.id.addFriendButton);

        addButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        friendList.clear();
        loadFriendList();
        for (int i = 0; i < friendList.size(); i++){
            addFriend(friendList.get(i), i);
        }
    }

    private void addFriend(String name, int pos){
        // Create Layout
        LinearLayout ll = new LinearLayout(this.getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int height = convertDPtoPX(50);
        params.height = height;
        params.topMargin = convertDPtoPX(10);
        params.bottomMargin = convertDPtoPX(10);
        ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        // Add TextView
        TextView tv = new TextView(this.getActivity());
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tvParams.width = convertDPtoPX(200);
        tvParams.leftMargin = convertDPtoPX(20);
        tvParams.height = height;
        tv.setLayoutParams(tvParams);
        tv.setText(name);
        tv.setTextSize(28);
        tv.setId(pos * 3);
        tv.setOnLongClickListener(this);
        ll.addView(tv);
        // Add Space
        LinearLayout space = new LinearLayout(this.getActivity());
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        spaceParams.width = convertDPtoPX(10);
        spaceParams.height = height;
        space.setLayoutParams(spaceParams);
        ll.addView(space);
        // Add Collection Button
        Button btn_col = new Button(this.getActivity());
        btn_col.setBackgroundResource(R.drawable.ic_collection_btn);
        RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnParams.width = convertDPtoPX(50);
        btnParams.height = height;
        btn_col.setLayoutParams(btnParams);
        btn_col.setId(pos * 3 + 1);
        btn_col.setOnClickListener(this);
        ll.addView(btn_col);
        // Add Space
        LinearLayout space2 = new LinearLayout(this.getActivity());
        LinearLayout.LayoutParams spaceParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        spaceParams2.width = convertDPtoPX(10);
        spaceParams2.height = height;
        space2.setLayoutParams(spaceParams2);
        ll.addView(space2);
        // Add Wantlist Button
        Button btn_want = new Button(this.getActivity());
        btn_want.setBackgroundResource(R.drawable.ic_wantedlist_btn);
        RelativeLayout.LayoutParams btnParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnParams2.width = convertDPtoPX(50);
        btnParams2.height = height;
        btn_want.setLayoutParams(btnParams2);
        btn_want.setId(pos * 3 + 2);
        btn_want.setOnClickListener(this);
        ll.addView(btn_want);
        scrollView.addView(ll);
        // Make Divider
        LinearLayout div = new LinearLayout(this.getActivity());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        height = convertDPtoPX(2);
        params.height = height;
        params.leftMargin = convertDPtoPX(20);
        params.rightMargin = convertDPtoPX(20);
        div.setLayoutParams(params);
        div.setBackgroundColor(getResources().getColor(R.color.friendList_divider));
        scrollView.addView(div);
    }

    public void removeFriend(String name, int pos){
        friendList.remove(pos);
        try {
            FileOutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            String write = "";
            for (int i = 0; i < friendList.size(); i++){
                String line = friendList.get(i) + "\n";
                write += line;
            }
            outputStream.write(write.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainActivity.friendsItem.setCount(String.valueOf(Integer.valueOf(MainActivity.friendsItem.getCount()) - 1));
        MainActivity.update();
        MainActivity.replaceFragment(new FriendsFragment());
    }

    public int convertDPtoPX(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void loadFriendList() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getActivity().openFileInput(filename));
            BufferedReader r = new BufferedReader(inputStreamReader);
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.equals(""))
                    friendList.add(line.trim());
            }
            r.close();
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addFriendButton) {
            String username = et.getText().toString().trim();
            if (username.equals("")){
                Toast.makeText(getActivity(), "Please enter a username", Toast.LENGTH_SHORT).show();
            } else {
                // Add To List
                friendList.add(username);
                try {
                    OutputStream outputStream = getActivity().openFileOutput(filename, Context.MODE_APPEND);
                    String toWrite = username + "\n";
                    outputStream.write(toWrite.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainActivity.friendsItem.setCount(String.valueOf(Integer.valueOf(MainActivity.friendsItem.getCount()) + 1));
                MainActivity.update();
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.hideSoftKeyboard();
                }
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_container, new FriendsFragment());
                ft.commit();
            }
        } else {
            if (v.getId() % 3 == 1){
                // Collection Button
                int pos = (v.getId() - 1) / 3;
                String username = friendList.get(pos);
                String url = "http://www.poppriceguide.com/guide/member/collection/" + username + "/";
                Global.setLastCollectionURL(url);
            } else if (v.getId() % 3 == 2) {
                // Wantlist Button
                int pos = (v.getId() - 2) / 3;
                String username = friendList.get(pos);
                String url = "http://www.poppriceguide.com/guide/member/wantlist/" + username + "/";
                Global.setLastCollectionURL(url);
            }
            MainActivity.replaceFragment(new CollectionFriendFragment());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        final int pos = v.getId() / 3;
        final TextView tv = (TextView) v;
        final String username = tv.getText().toString();
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle("Remove Friend");
        alertDialog.setMessage("Are you sure you want to remove " + username + " from your friendlist?");
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                removeFriend(username, pos);
            }
        });
        alertDialog.show();
        return false;
    }
}
