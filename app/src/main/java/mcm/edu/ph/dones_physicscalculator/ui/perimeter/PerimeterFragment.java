package mcm.edu.ph.dones_physicscalculator.ui.perimeter;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;

import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentPerimeterBinding;
import pl.droidsonroids.gif.GifImageView;

public class PerimeterFragment extends Fragment implements View.OnClickListener {

    private GifImageView gifPerimeter;
    private FragmentPerimeterBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtPerimeter, txtFormula, txtSquareSide, txtResult;
    private View root, perimeterSquare, squareBracket1, squareBracket2, squareBracket3;
    private String sX1, sX2;
    private double x1, x2, result;
    private Button btnSolve, btnRestart;
    private int shapeType;
    private String TAG = "PerimeterFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerimeterViewModel perimeterViewModel =
                new ViewModelProvider(this).get(PerimeterViewModel.class);

        binding = FragmentPerimeterBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initSpinnerFooter();
        hideAll();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);

        //final TextView textView = binding.textPerimeter;
        //perimeterViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void initSpinnerFooter() {
        String[] paths = {"square", "rectangle", "circle"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {
                    case 0:
                        //square
                        Log.d(TAG, "square is selected");
                        shapeType = 1;
                        txtPerimeter.setText("Perimeter of a square:");
                        txtFormula.setText( "4(s)" );
                        etX1.setHint("s");
                        etX2.setVisibility(View.GONE);
                        break;
                    case 1:
                        //rectangle
                        Log.d(TAG, "rectangle is selected");
                        shapeType = 2;
                        hideSquare();
                        break;
                    case 2:
                        //circle
                        Log.d(TAG, "circle is selected");
                        shapeType = 3;
                        hideSquare();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View root) {
        switch (root.getId()) {
            case R.id.btnPerimeterSolve:
                solvePerimeter();
                break;
            case R.id.btnPerimeterRestart:
                hideAll();
                initSpinnerFooter();
                break;
        }
    }

    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }

    private void solvePerimeter(){
        if (TextUtils.isEmpty(etX1.getText())){
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        }
        else {

            sX1 = etX1.getText().toString();
            x1 = Double.parseDouble(sX1);

            switch (shapeType) {
                case 1:
                    result = roundTwoDecimals(4 * x1);
                    txtFormula.setText( "4(" + sX1 + ")" );
                    txtSquareSide.setText(sX1);
                    showSquare();
                    break;
                case 2:
                    if (TextUtils.isEmpty(etX2.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                            Toast.LENGTH_LONG).show();
                    }
                    else{
                        sX2 = etX2.getText().toString();
                        x2 = Double.parseDouble(sX2);
                        result = roundTwoDecimals(2 * (x1 + x2));
                    }
                    break;
                case 3:
                    result = roundTwoDecimals(2 * Math.PI * x1);
                    break;
                default:
                    Toast.makeText(getActivity(), "Please choose a shape.",
                            Toast.LENGTH_LONG).show();
            }
            txtResult.setText(String.valueOf(result));
        }
    }

    private void hideAll() {
        gifPerimeter.setVisibility(View.VISIBLE);
        hideSquare();
    }

    private void hideSquare() {
        perimeterSquare.setVisibility(View.GONE);
        squareBracket1.setVisibility(View.GONE);
        squareBracket2.setVisibility(View.GONE);
        squareBracket3.setVisibility(View.GONE);
        txtSquareSide.setVisibility(View.GONE);
        txtResult.setVisibility(View.GONE);
    }

    private void showSquare() {
        perimeterSquare.setVisibility(View.VISIBLE);
        squareBracket1.setVisibility(View.VISIBLE);
        squareBracket2.setVisibility(View.VISIBLE);
        squareBracket3.setVisibility(View.VISIBLE);
        txtSquareSide.setVisibility(View.VISIBLE);
        txtResult.setVisibility(View.VISIBLE);
        gifPerimeter.setVisibility(View.GONE);
    }

    private void initUI() {
        gifPerimeter = root.findViewById(R.id.gifPerimeter);
        spinner = root.findViewById(R.id.dropdownPerimeter);
        txtPerimeter = root.findViewById(R.id.txtPerimeter);
        txtFormula= root.findViewById(R.id.txtPerimeterFormula);
        etX1 = root.findViewById(R.id.etPerimeterX1);
        etX2 = root.findViewById(R.id.etPerimeterX2);
        btnSolve = root.findViewById(R.id.btnPerimeterSolve);
        btnRestart = root.findViewById(R.id.btnPerimeterRestart);

        perimeterSquare = root.findViewById(R.id.perimeterSquare);
        squareBracket1 = root.findViewById(R.id.squareBracket1);
        squareBracket2 = root.findViewById(R.id.squareBracket2);
        squareBracket3 = root.findViewById(R.id.squareBracket3);
        txtSquareSide = root.findViewById(R.id.txtSquareSide);
        txtResult = root.findViewById(R.id.txtPerimeterResult);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}