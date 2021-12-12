package org.sen.android.automation.message;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import org.sen.android.automation.message.databinding.FragmentFirstBinding;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCheckPermissions.setOnClickListener(onClickListener -> {
            Log.i(TAG, "Check permission button clicked!");
            checkForSmsPermission(getActivity(), view);
        });
    }

    private void checkForSmsPermission(Activity activity, View view) {
        if (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted!");
            Snackbar.make(view, "Permissions not available!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            Log.i(TAG, "Permission granted.");
            Snackbar.make(view, "Permissions granted.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}