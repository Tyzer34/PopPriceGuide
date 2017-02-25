package bjorn.vuylsteker.tyzer34.PPG.util;

/**
 * Created by Tyzer34 on 25/05/2016.
 */
import android.content.Intent;
import android.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

public final class FragmentIntentIntegrator extends IntentIntegrator {

    private final Fragment fragment;

    public FragmentIntentIntegrator(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

    @Override
    protected void startActivityForResult(Intent intent, int code) {
        fragment.startActivityForResult(intent, code);
    }
}