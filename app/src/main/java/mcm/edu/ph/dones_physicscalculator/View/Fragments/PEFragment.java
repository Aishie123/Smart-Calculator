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
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentPeBinding;
import pl.droidsonroids.gif.GifImageView;

public class PEFragment extends Fragment implements View.OnClickListener{

    private GifImageView gifPE;
    private FragmentPeBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtPE, txtFormula, txtResult;
    private View root;
    private Button btnSolve, btnRestart;
    private double result;
    private int var;
    private final String TAG = "PEFragment";
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initSpinnerFooter();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        return root;
    }

    // initializing spinner
    private void initSpinnerFooter() {
        String[] paths = {"Potential Energy", "Mass", "Height"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {

                    case 0:
                        // Potential Energy
                        Log.d(TAG, "Potential Energy is selected");
                        var = 1;
                        txtPE.setText("Potential Energy:");
                        txtFormula.setText(Html.fromHtml("PE = mgh", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("m (in kg)");
                        etX2.setHint("h (in m)");
                        break;

                    case 1:
                        // Mass
                        Log.d(TAG, "Mass is selected");
                        var = 2;
                        txtPE.setText("Mass:");
                        txtFormula.setText(Html.fromHtml("m = <sup>PE</sup>/<sub>gh</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("PE (in J)");
                        etX2.setHint("h (in m)");
                        break;

                    case 2:
                        // Height
                        Log.d(TAG, "Height is selected");
                        var = 3;
                        txtPE.setText("Height:");
                        txtFormula.setText(Html.fromHtml("h = <sup>PE</sup>/<sub>gm</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("PE (in J)");
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
            case R.id.btnPESolve:
                solvePE(); // calculates the Potential Energy/Mass/Height
                break;

            // if "Restart" button is pressed
            case R.id.btnPERestart:
                gifPE.setImageResource(R.drawable.gif_pe1);
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solvePE() {
        if (TextUtils.isEmpty(etX1.getText()) || TextUtils.isEmpty(etX2.getText())) {
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        } else {
            String sX1 = etX1.getText().toString();
            String sX2 = etX2.getText().toString();
            double x1 = Double.parseDouble(sX1);
            double x2 = Double.parseDouble(sX2);

            switch (var) {

                case 1: // Potential Energy
                    result = calc.getPE(x1, x2);
                    txtFormula.setText(Html.fromHtml("PE = ("+x1+")(9.8)("+x2+")", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "J");
                    break;

                case 2: // Mass
                    result = calc.getPEMass(x1, x2);
                    txtFormula.setText(Html.fromHtml("m = <sup>("+x1+")</sup>/<sub>(9.8)("+x2+")</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "kg");
                    break;

                case 3: // Height
                    result = calc.getPEHeight(x1, x2);
                    txtFormula.setText(Html.fromHtml("h = <sup>("+x1+")</sup>/<sub>(9.8)("+x2+")</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "m");
                    break;

            }
            gifPE.setImageResource(R.drawable.gif_pe2);
            txtResult.setVisibility(View.VISIBLE);

        }
    }



    private void initUI() {

        gifPE = root.findViewById(R.id.gifPE);
        spinner = root.findViewById(R.id.dropdownPE);
        txtPE = root.findViewById(R.id.txtPE);
        txtFormula = root.findViewById(R.id.txtPEFormula);
        etX1 = root.findViewById(R.id.etPEX1);
        etX2 = root.findViewById(R.id.etPEX2);
        btnSolve = root.findViewById(R.id.btnPESolve);
        btnRestart = root.findViewById(R.id.btnPERestart);

        txtResult = root.findViewById(R.id.txtPEResult);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Potential Energy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}