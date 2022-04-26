package mcm.edu.ph.dones_physicscalculator.View.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mcm.edu.ph.dones_physicscalculator.Controller.Calculation;
import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.View.Activities.MainActivity;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentKeBinding;
import pl.droidsonroids.gif.GifImageView;

public class KEFragment extends Fragment implements View.OnClickListener {
    
    private GifImageView gifKE;
    private FragmentKeBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtKE, txtFormula, txtResult;
    private View root;
    private Button btnSolve, btnRestart;
    private double result;
    private int var;
    private final String TAG = "KEFragment";
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentKeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initSpinnerFooter();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        return root;
    }

    // initializing spinner
    private void initSpinnerFooter() {
        String[] paths = {"Kinetic Energy", "Mass", "Velocity"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {

                    case 0:
                        // Kinetic Energy
                        Log.d(TAG, "Kinetic Energy is selected");
                        var = 1;
                        txtKE.setText("Kinetic Energy:");
                        txtFormula.setText(Html.fromHtml("K.E = <sup>1</sup>/<sub>2</sub>(mass)(velocity)<sup>2</sup>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("m (in kg)");
                        etX2.setHint("v (in m/s)");
                        break;

                    case 1:
                        // Mass
                        Log.d(TAG, "Mass is selected");
                        var = 2;
                        txtKE.setText("Mass:");
                        txtFormula.setText(Html.fromHtml("m = <sup>2K.E</sup>/<sub>(velocity)<sup>2</sup></sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("K.E (in J)");
                        etX2.setHint("v (in m/s)");
                        break;

                    case 2:
                        // Velocity
                        Log.d(TAG, "Velocity is selected");
                        var = 3;
                        txtKE.setText("Velocity:");
                        txtFormula.setText(Html.fromHtml("v = \u221a(<sup>2K.E</sup>/<sub>(mass)</sub>)", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("K.E (in J)");
                        etX2.setHint("m (in kg)");
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
            case R.id.btnKESolve:
                solveKE(); // calculates the Kinetic Energy/Mass/Velocity
                break;

            // if "Restart" button is pressed
            case R.id.btnKERestart:
                gifKE.setImageResource(R.drawable.gif_ke1);
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solveKE() {
        if (TextUtils.isEmpty(etX1.getText()) || TextUtils.isEmpty(etX2.getText())) {
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        } else {
            String sX1 = etX1.getText().toString();
            String sX2 = etX2.getText().toString();
            double x1 = Double.parseDouble(sX1);
            double x2 = Double.parseDouble(sX2);

            switch (var) {

                case 1: // Kinetic Energy
                    result = calc.getKE(x1, x2);
                    txtFormula.setText(Html.fromHtml("K.E = <sup>1</sup>/<sub>2</sub>("+x1+")("+x2+")<sup>2</sup>)", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "J");
                    break;

                case 2: // Mass
                    result = calc.getKEMass(x1, x2);
                    txtFormula.setText(Html.fromHtml("m = <sup>2("+x1+")</sup>/<sub>("+x2+")<sup>2</sup></sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "kg");
                    break;

                case 3: // Velocity
                    result = calc.getKEVelocity(x1, x2);
                    txtFormula.setText(Html.fromHtml("v = \u221a(<sup>2("+x1+")</sup>/<sub>("+x2+")</sub>)", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "m/s");
                    break;

            }
            gifKE.setImageResource(R.drawable.gif_ke2);
            txtResult.setVisibility(View.VISIBLE);

        }
    }



    private void initUI() {

        gifKE = root.findViewById(R.id.gifKE);
        spinner = root.findViewById(R.id.dropdownKE);
        txtKE = root.findViewById(R.id.txtKE);
        txtFormula = root.findViewById(R.id.txtKEFormula);
        etX1 = root.findViewById(R.id.etKEX1);
        etX2 = root.findViewById(R.id.etKEX2);
        btnSolve = root.findViewById(R.id.btnKESolve);
        btnRestart = root.findViewById(R.id.btnKERestart);

        txtResult = root.findViewById(R.id.txtKEResult);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Kinetic Energy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}