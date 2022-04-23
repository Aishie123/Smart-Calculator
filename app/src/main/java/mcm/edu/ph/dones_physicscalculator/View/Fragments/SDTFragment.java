package mcm.edu.ph.dones_physicscalculator.View.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mcm.edu.ph.dones_physicscalculator.Calculation;
import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.View.Activities.MainActivity;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentSdtBinding;
import pl.droidsonroids.gif.GifImageView;

public class SDTFragment extends Fragment implements View.OnClickListener {

    private GifImageView gifSDT;
    private FragmentSdtBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtSDT, txtFormula, txtResult;
    private View root;
    private Button btnSolve, btnRestart;
    private double result, speed;
    private int var;
    private static int CAR_ORIG_POS;
    private ObjectAnimator carAnim;
    private final String TAG = "SDTFragment";
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSdtBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initCar();
        initSpinnerFooter();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        return root;
    }

    // initializing spinner
    private void initSpinnerFooter() {
        String[] paths = {"Speed", "Distance", "Time"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {

                    case 0:
                        // Speed
                        Log.d(TAG, "Speed is selected");
                        var = 1;
                        txtSDT.setText("Speed:");
                        txtFormula.setText("speed = distance • time");
                        etX1.setHint("d");
                        etX2.setHint("t");
                        break;

                    case 1:
                        // Distance
                        Log.d(TAG, "Distance is selected");
                        var = 2;
                        txtSDT.setText("Distance:");
                        txtFormula.setText(Html.fromHtml("distance = <sup>speed</sup>/<sub>time</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("s");
                        etX2.setHint("t");
                        break;

                    case 2:
                        // Time
                        Log.d(TAG, "Time is selected");
                        var = 3;
                        txtSDT.setText("Time:");
                        txtFormula.setText(Html.fromHtml("time = <sup>speed</sup>/<sub>distance</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("s");
                        etX2.setHint("d");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View root) {
        switch (root.getId()) {

            // if "Solve" button is pressed
            case R.id.btnSDTSolve:
                solveSDT(); // calculates the speed/distance/time
                break;

            // if "Restart" button is pressed
            case R.id.btnSDTRestart:
                txtResult.setVisibility(View.GONE);
                stopCar();
                gifSDT.setX(CAR_ORIG_POS);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solveSDT(){
        if (TextUtils.isEmpty(etX1.getText()) || TextUtils.isEmpty(etX2.getText())){
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        }
        else {
            String sX1 = etX1.getText().toString();
            String sX2 = etX2.getText().toString();
            double x1 = Double.parseDouble(sX1);
            double x2 = Double.parseDouble(sX2);

            switch (var) {

                case 1: // speed
                    result = calc.getSpeed(x1, x2);
                    txtFormula.setText(x1 + " • " + x2);
                    speed = result;
                    break;

                case 2: // distance
                    result = calc.getDistance(x1, x2);
                    txtFormula.setText(Html.fromHtml("<sup>" + x1 + "</sup>/<sub>" + x2 + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    speed = x1;
                    break;

                case 3: // time
                    result = calc.getTime(x1, x2);
                    txtFormula.setText(Html.fromHtml("<sup>" + x1 + "</sup>/<sub>" + x2 + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    speed = x1;
                    break;
            }

            moveCar();
            txtResult.setText("= " + result);
            txtResult.setVisibility(View.VISIBLE);

        }
    }

    private void moveCar() {
        gifSDT.setX(CAR_ORIG_POS - 800);
        carAnim = ObjectAnimator.ofFloat(gifSDT,"translationX", 800);

        if (speed <= 100 && speed > 10){
            carAnim.setDuration((long) (50000/speed));
        }
        else if (speed <= 10){
            carAnim.setDuration(50000);
        }
        else {
            carAnim.setDuration(500);
        }

        carAnim.start();
        carAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                carAnim.start();
            }
        });
    }

    private void stopCar() {
        carAnim.removeAllUpdateListeners();
        carAnim.removeAllListeners();
        carAnim.end();
        carAnim.cancel();
        carAnim = null;
    }

    private void initCar() {
        gifSDT.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CAR_ORIG_POS = gifSDT.getLeft(); // gets X position of hero
                gifSDT.getViewTreeObserver().removeOnGlobalLayoutListener(this); // removes the listener to prevent being called again
            }
        });
    }


    private void initUI() {

        gifSDT = root.findViewById(R.id.gifSDT);
        spinner = root.findViewById(R.id.dropdownSDT);
        txtSDT = root.findViewById(R.id.txtSDT);
        txtFormula= root.findViewById(R.id.txtSDTFormula);
        etX1 = root.findViewById(R.id.etSDTX1);
        etX2 = root.findViewById(R.id.etSDTX2);
        btnSolve = root.findViewById(R.id.btnSDTSolve);
        btnRestart = root.findViewById(R.id.btnSDTRestart);

        txtResult = root.findViewById(R.id.txtSDTResult);

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Speed, Distance, Time");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}