package mcm.edu.ph.dones_physicscalculator.View.Fragments;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import mcm.edu.ph.dones_physicscalculator.Calculation;
import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.View.Activities.MainActivity;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentAreaBinding;
import pl.droidsonroids.gif.GifImageView;

public class AreaFragment extends Fragment implements View.OnClickListener{

    private GifImageView gifArea;
    private FragmentAreaBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtArea, txtFormula, txtSquareSide, txtRectangleWidth, txtRectangleLength, txtCircleRadius,
            txtEqTriSide1, txtEqTriSide2, txtEqTriSide3, txtResult;
    private View root, areaSquare, squareBracket1, squareBracket2, squareBracket3, areaRectangle, rectangleBracket1, rectangleBracket2,
            rectangleBracket3, rectangleBracket4, rectangleBracket5, rectangleBracket6, areaCircle, circleBracket1, areaEqTriangle;
    private Button btnSolve, btnRestart;
    private String TAG = "PerimeterFragment";
    private double result, dLength, dWidth;
    private int shapeType;
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAreaBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        initUI();
        initSpinnerFooter();
        hideAll();

        btnSolve.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        return root;
    }

    // initializing spinner
    private void initSpinnerFooter() {
        String[] paths = {"Square", "Rectangle", "Circle", "Equilateral Triangle"};
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
                        txtArea.setText("Area of a square:");
                        txtFormula.setText( "(s)\u00B2" );
                        etX1.setHint("s");
                        etX2.setVisibility(View.GONE);
                        break;

                    case 1:
                        //rectangle
                        Log.d(TAG, "rectangle is selected");
                        shapeType = 2;
                        txtArea.setText("Area of a rectangle:");
                        txtFormula.setText( "(l * w)" );
                        etX1.setHint("l");
                        etX2.setHint("w");
                        etX2.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        //circle
                        Log.d(TAG, "circle is selected");
                        shapeType = 3;
                        txtArea.setText("Area of a circle:");
                        txtFormula.setText( "π(r)\u00B2" );
                        etX1.setHint("r");
                        etX2.setVisibility(View.GONE);
                        break;

                    case 3:
                        //triangle
                        Log.d(TAG, "triangle is selected");
                        shapeType = 4;
                        txtArea.setText("Area of an equilateral triangle:");
                        txtFormula.setText( "(\u221a3/4)(a\u00B2)" );
                        etX1.setHint("a");
                        etX2.setVisibility(View.GONE);
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
            case R.id.btnAreaSolve:
                solveArea(); // calculates the perimeter
                break;

            // if "Restart" button is pressed
            case R.id.btnAreaRestart:
                hideAll();
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solveArea(){
        if (TextUtils.isEmpty(etX1.getText())){
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        }
        else {

            String sX1 = etX1.getText().toString();
            double x1 = Double.parseDouble(sX1);

            switch (shapeType) {

                case 1: // square
                    result = calc.getSqArea(x1);
                    txtFormula.setText( "(" + sX1 + ")\u00B2" );
                    txtSquareSide.setText(sX1);
                    showSquare();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                case 2: // rectangle
                    if (TextUtils.isEmpty(etX2.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                                Toast.LENGTH_LONG).show();
                    }
                    else{

                        String sX2 = etX2.getText().toString();
                        double x2 = Double.parseDouble(sX2);

                        dWidth = calc.getReWidth( x1, x2 );
                        dLength = calc.getReLength( x1, x2 );
                        result = calc.getReArea( x1, x2 );

                        txtFormula.setText("(" + sX1 + " * " + sX2 + ")");
                        txtRectangleLength.setText(sX1);
                        txtRectangleWidth.setText(sX2);

                        ConstraintLayout.LayoutParams rectangle = (ConstraintLayout.LayoutParams)areaRectangle.getLayoutParams();
                        ConstraintLayout.LayoutParams width = (ConstraintLayout.LayoutParams)rectangleBracket1.getLayoutParams();
                        ConstraintLayout.LayoutParams length = (ConstraintLayout.LayoutParams)rectangleBracket4.getLayoutParams();
                        rectangle.width = calc.dpToPx(dLength, root.getContext());
                        length.width = calc.dpToPx(dLength, root.getContext());
                        width.height = calc.dpToPx(dWidth, root.getContext());
                        rectangle.height = calc.dpToPx(dWidth, root.getContext());
                        areaRectangle.setLayoutParams(rectangle);
                        rectangleBracket1.setLayoutParams(width);
                        rectangleBracket4.setLayoutParams(length);

                        showRectangle();
                        txtResult.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3: // circle
                    result = calc.getCiArea(x1);
                    txtFormula.setText( "π(" + sX1 + ")\u00B2" );
                    txtCircleRadius.setText(sX1);
                    showCircle();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                case 4: // equilateral triangle
                    result = calc.getTrArea(x1);
                    txtFormula.setText( "(\u221a3/4)(" + sX1 + "\u00B2)" );
                    txtEqTriSide1.setText(sX1);
                    txtEqTriSide2.setText(sX1);
                    txtEqTriSide3.setText(sX1);
                    showEqTriangle();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                default:
                    Toast.makeText(getActivity(), "Please choose a shape.",
                            Toast.LENGTH_LONG).show();
            }

            gifArea.setVisibility(View.INVISIBLE);
            txtResult.setText("= " + result);

        }
    }

    private void hideAll() {
        gifArea.setVisibility(View.VISIBLE);
        hideSquare();
        hideCircle();
        hideRectangle();
        hideEqTriangle();
    }

    private void hideSquare() {
        areaSquare.setVisibility(View.GONE);
        squareBracket1.setVisibility(View.GONE);
        squareBracket2.setVisibility(View.GONE);
        squareBracket3.setVisibility(View.GONE);
        txtSquareSide.setVisibility(View.GONE);
    }

    private void showSquare() {
        areaSquare.setVisibility(View.VISIBLE);
        squareBracket1.setVisibility(View.VISIBLE);
        squareBracket2.setVisibility(View.VISIBLE);
        squareBracket3.setVisibility(View.VISIBLE);
        txtSquareSide.setVisibility(View.VISIBLE);
        hideCircle();
        hideRectangle();
        hideEqTriangle();
    }

    private void hideCircle() {
        areaCircle.setVisibility(View.GONE);
        circleBracket1.setVisibility(View.GONE);
        txtCircleRadius.setVisibility(View.GONE);
    }

    private void showCircle() {
        areaCircle.setVisibility(View.VISIBLE);
        circleBracket1.setVisibility(View.VISIBLE);
        txtCircleRadius.setVisibility(View.VISIBLE);
        hideSquare();
        hideRectangle();
        hideEqTriangle();
    }

    private void hideRectangle() {
        areaRectangle.setVisibility(View.GONE);
        rectangleBracket1.setVisibility(View.GONE);
        rectangleBracket2.setVisibility(View.GONE);
        rectangleBracket3.setVisibility(View.GONE);
        rectangleBracket4.setVisibility(View.GONE);
        rectangleBracket5.setVisibility(View.GONE);
        rectangleBracket6.setVisibility(View.GONE);
        txtRectangleWidth.setVisibility(View.GONE);
        txtRectangleLength.setVisibility(View.GONE);
    }

    private void showRectangle() {
        areaRectangle.setVisibility(View.VISIBLE);
        rectangleBracket1.setVisibility(View.VISIBLE);
        rectangleBracket2.setVisibility(View.VISIBLE);
        rectangleBracket3.setVisibility(View.VISIBLE);
        rectangleBracket4.setVisibility(View.VISIBLE);
        rectangleBracket5.setVisibility(View.VISIBLE);
        rectangleBracket6.setVisibility(View.VISIBLE);
        txtRectangleWidth.setVisibility(View.VISIBLE);
        txtRectangleLength.setVisibility(View.VISIBLE);
        hideSquare();
        hideCircle();
        hideEqTriangle();
    }

    private void hideEqTriangle() {
        areaEqTriangle.setVisibility(View.GONE);
        txtEqTriSide1.setVisibility(View.GONE);
        txtEqTriSide2.setVisibility(View.GONE);
        txtEqTriSide3.setVisibility(View.GONE);
    }

    private void showEqTriangle() {
        areaEqTriangle.setVisibility(View.VISIBLE);
        txtEqTriSide1.setVisibility(View.VISIBLE);
        txtEqTriSide2.setVisibility(View.VISIBLE);
        txtEqTriSide3.setVisibility(View.VISIBLE);
        hideSquare();
        hideCircle();
        hideRectangle();
    }

    private void initUI() {

        gifArea = root.findViewById(R.id.gifArea);
        spinner = root.findViewById(R.id.dropdownArea);
        txtArea = root.findViewById(R.id.txtArea);
        txtFormula= root.findViewById(R.id.txtAreaFormula);
        etX1 = root.findViewById(R.id.etAreaX1);
        etX2 = root.findViewById(R.id.etAreaX2);
        btnSolve = root.findViewById(R.id.btnAreaSolve);
        btnRestart = root.findViewById(R.id.btnAreaRestart);

        areaSquare = root.findViewById(R.id.areaSquare);
        squareBracket1 = root.findViewById(R.id.areaSqBracket1);
        squareBracket2 = root.findViewById(R.id.areaSqBracket2);
        squareBracket3 = root.findViewById(R.id.areaSqBracket3);
        txtSquareSide = root.findViewById(R.id.txtAreaSqSide);

        areaRectangle = root.findViewById(R.id.areaRectangle);
        rectangleBracket1 = root.findViewById(R.id.areaReBracket1);
        rectangleBracket2 = root.findViewById(R.id.areaReBracket2);
        rectangleBracket3 = root.findViewById(R.id.areaReBracket3);
        rectangleBracket4 = root.findViewById(R.id.areaReBracket4);
        rectangleBracket5 = root.findViewById(R.id.areaReBracket5);
        rectangleBracket6 = root.findViewById(R.id.areaReBracket6);
        txtRectangleWidth = root.findViewById(R.id.txtAreaReWidth);
        txtRectangleLength = root.findViewById(R.id.txtAreaReLength);

        areaCircle = root.findViewById(R.id.areaCircle);
        circleBracket1 = root.findViewById(R.id.areaCiBracket1);
        txtCircleRadius = root.findViewById(R.id.txtAreaCiRadius);

        areaEqTriangle = root.findViewById(R.id.areaEqTriangle);
        txtEqTriSide1 = root.findViewById(R.id.txtAreaEqTriSide1);
        txtEqTriSide2 = root.findViewById(R.id.txtAreaEqTriSide2);
        txtEqTriSide3 = root.findViewById(R.id.txtAreaEqTriSide3);

        txtResult = root.findViewById(R.id.txtAreaResult);

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Area");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}