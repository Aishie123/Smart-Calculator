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
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentGasBinding;
import pl.droidsonroids.gif.GifImageView;

public class GasFragment extends Fragment implements View.OnClickListener{

    private GifImageView gifGas;
    private FragmentGasBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2, etX3;
    private TextView txtGas, txtFormula, txtResult;
    private View root;
    private Button btnSolve, btnRestart;
    private double result;
    private int var;
    private final String TAG = "GasFragment";
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGasBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initSpinnerFooter();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        return root;
    }

    // initializing spinner
    private void initSpinnerFooter() {
        String[] paths = {"Pressure", "Volume", "Moles", "Temperature"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {

                    case 0:
                        // Pressure
                        Log.d(TAG, "Pressure is selected");
                        var = 1;
                        txtGas.setText("Pressure:");
                        txtFormula.setText(Html.fromHtml("P = <sup>nRT</sup>/<sub>V</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("n");
                        etX2.setHint("T");
                        etX3.setHint("V");
                        break;

                    case 1:
                        // Volume
                        Log.d(TAG, "Volume is selected");
                        var = 2;
                        txtGas.setText("Volume:");
                        txtFormula.setText(Html.fromHtml("V = <sup>nRT</sup>/<sub>P</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("n");
                        etX2.setHint("T");
                        etX3.setHint("P");
                        break;

                    case 2:
                        // Moles
                        Log.d(TAG, "Moles is selected");
                        var = 3;
                        txtGas.setText("Moles:");
                        txtFormula.setText(Html.fromHtml("n = <sup>PV</sup>/<sub>RT</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("P");
                        etX2.setHint("V");
                        etX3.setHint("T");
                        break;

                    case 3:
                        // Temperature
                        Log.d(TAG, "Temperature is selected");
                        var = 4;
                        txtGas.setText("Temperature:");
                        txtFormula.setText(Html.fromHtml("T = <sup>PV</sup>/<sub>nR</sub>", Html.FROM_HTML_MODE_LEGACY));
                        etX1.setHint("P");
                        etX2.setHint("V");
                        etX3.setHint("n");
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
            case R.id.btnGasSolve:
                solveGas(); // calculates the Pressure/Volume/Moles
                break;

            // if "Restart" button is pressed
            case R.id.btnGasRestart:
                gifGas.setImageResource(R.drawable.gif_science1);
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solveGas() {
        if (TextUtils.isEmpty(etX1.getText()) || TextUtils.isEmpty(etX2.getText()) || TextUtils.isEmpty(etX3.getText())) {
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        } else {
            String sX1 = etX1.getText().toString();
            String sX2 = etX2.getText().toString();
            String sX3 = etX3.getText().toString();
            double x1 = Double.parseDouble(sX1);
            double x2 = Double.parseDouble(sX2);
            double x3 = Double.parseDouble(sX3);

            switch (var) {

                case 1: // Pressure
                    result = calc.getGasPressure(x1, x2, x3);
                    txtFormula.setText(Html.fromHtml("P = <sup>" + x1 + "•R•" + x2 + "</sup>/<sub>" + x3 + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "atm");
                    break;

                case 2: // Volume
                    result = calc.getGasVolume(x1, x2, x3);
                    txtFormula.setText(Html.fromHtml("V = <sup>" + x1 + "•R•" + x2 + "</sup>/<sub>" + x3 + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "L");
                    break;

                case 3: // Moles
                    result = calc.getGasMoles(x1, x2, x3);
                    txtFormula.setText(Html.fromHtml("n = <sup>" + x1 + "•" + x2 + "</sup>/<sub>R•" + x3 + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + " moles");
                    break;

                case 4: // Temperature
                    result = calc.getGasTemperature(x1, x2, x3);
                    txtFormula.setText(Html.fromHtml("T = <sup>" + x1 + "•" + x2 + "</sup>/<sub>" + x3 + "•R" + "</sub>", Html.FROM_HTML_MODE_LEGACY));
                    txtResult.setText("= " + result + "K");
                    break;

            }
            gifGas.setImageResource(R.drawable.gif_science2);
            txtResult.setVisibility(View.VISIBLE);

        }
    }



    private void initUI() {

        gifGas = root.findViewById(R.id.gifGas);
        spinner = root.findViewById(R.id.dropdownGas);
        txtGas = root.findViewById(R.id.txtGas);
        txtFormula = root.findViewById(R.id.txtGasFormula);
        etX1 = root.findViewById(R.id.etGasX1);
        etX2 = root.findViewById(R.id.etGasX2);
        etX3 = root.findViewById(R.id.etGasX3);
        btnSolve = root.findViewById(R.id.btnGasSolve);
        btnRestart = root.findViewById(R.id.btnGasRestart);

        txtResult = root.findViewById(R.id.txtGasResult);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Ideal Gas Equation");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
