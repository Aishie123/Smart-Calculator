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
import mcm.edu.ph.dones_physicscalculator.View.Activities.MainActivity;
import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentPerimeterBinding;
import pl.droidsonroids.gif.GifImageView;

public class PerimeterFragment extends Fragment implements View.OnClickListener {

    private GifImageView gifPerimeter;
    private FragmentPerimeterBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2;
    private TextView txtPerimeter, txtFormula, txtSquareSide, txtRectangleWidth, txtRectangleLength, txtCircleRadius, txtResult;
    private View root, perimeterSquare, squareBracket1, squareBracket2, squareBracket3,
            perimeterRectangle, rectangleBracket1, rectangleBracket2, rectangleBracket3, rectangleBracket4, rectangleBracket5, rectangleBracket6,
            perimeterCircle, circleBracket1;
    private Button btnSolve, btnRestart;
    private String TAG = "PerimeterFragment";
    private double result, dLength, dWidth;
    private int shapeType;
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerimeterBinding.inflate(inflater, container, false);
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
        String[] paths = {"Square", "Rectangle", "Circle"};
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
                        txtPerimeter.setText("Perimeter of a rectangle:");
                        txtFormula.setText( "2(l + w)" );
                        etX1.setHint("l");
                        etX2.setHint("w");
                        etX2.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        //circle
                        Log.d(TAG, "circle is selected");
                        shapeType = 3;
                        txtPerimeter.setText("Circumference of a circle:");
                        txtFormula.setText( "2π(r)" );
                        etX1.setHint("r");
                        etX2.setVisibility(View.GONE);
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

            // if "Solve" button is pressed
            case R.id.btnPerimeterSolve:
                solvePerimeter(); // calculates the perimeter
                break;

            // if "Restart" button is pressed
            case R.id.btnPerimeterRestart:
                hideAll();
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solvePerimeter(){
        if (TextUtils.isEmpty(etX1.getText())){
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        }
        else {

            String sX1 = etX1.getText().toString();
            double x1 = Double.parseDouble(sX1);

            switch (shapeType) {

                case 1:
                    result = calc.getSqPerimeter(x1);
                    txtFormula.setText( "4(" + sX1 + ")" );
                    txtSquareSide.setText(sX1);
                    showSquare();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    if (TextUtils.isEmpty(etX2.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                            Toast.LENGTH_LONG).show();
                    }
                    else{

                        String sX2 = etX2.getText().toString();
                        double x2 = Double.parseDouble(sX2);

                        dWidth = calc.getReWidth( x1, x2 );
                        dLength = calc.getReLength( x1, x2 );
                        result = calc.getRePerimeter( x1, x2 );

                        txtFormula.setText("2(" + sX1 + " + " + sX2 + ")");
                        txtRectangleLength.setText(sX1);
                        txtRectangleWidth.setText(sX2);

                        ConstraintLayout.LayoutParams rectangle = (ConstraintLayout.LayoutParams)perimeterRectangle.getLayoutParams();
                        ConstraintLayout.LayoutParams width = (ConstraintLayout.LayoutParams)rectangleBracket1.getLayoutParams();
                        ConstraintLayout.LayoutParams length = (ConstraintLayout.LayoutParams)rectangleBracket4.getLayoutParams();
                        rectangle.width = calc.dpToPx(dLength, root.getContext());
                        length.width = calc.dpToPx(dLength, root.getContext());
                        width.height = calc.dpToPx(dWidth, root.getContext());
                        rectangle.height = calc.dpToPx(dWidth, root.getContext());
                        perimeterRectangle.setLayoutParams(rectangle);
                        rectangleBracket1.setLayoutParams(width);
                        rectangleBracket4.setLayoutParams(length);

                        showRectangle();
                        txtResult.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3:
                    result = calc.getCircumference(x1);
                    txtFormula.setText( "2π(" + sX1 + ")" );
                    txtCircleRadius.setText(sX1);
                    showCircle();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                default:
                    Toast.makeText(getActivity(), "Please choose a shape.",
                            Toast.LENGTH_LONG).show();
            }

            gifPerimeter.setVisibility(View.INVISIBLE);
            txtResult.setText("= " + result);

        }
    }

    private void hideAll() {
        gifPerimeter.setVisibility(View.VISIBLE);
        hideSquare();
        hideCircle();
        hideRectangle();
    }

    private void hideSquare() {
        perimeterSquare.setVisibility(View.GONE);
        squareBracket1.setVisibility(View.GONE);
        squareBracket2.setVisibility(View.GONE);
        squareBracket3.setVisibility(View.GONE);
        txtSquareSide.setVisibility(View.GONE);
    }

    private void showSquare() {
        perimeterSquare.setVisibility(View.VISIBLE);
        squareBracket1.setVisibility(View.VISIBLE);
        squareBracket2.setVisibility(View.VISIBLE);
        squareBracket3.setVisibility(View.VISIBLE);
        txtSquareSide.setVisibility(View.VISIBLE);
        hideCircle();
        hideRectangle();
    }

    private void hideCircle() {
        perimeterCircle.setVisibility(View.GONE);
        circleBracket1.setVisibility(View.GONE);
        txtCircleRadius.setVisibility(View.GONE);
    }

    private void showCircle() {
        perimeterCircle.setVisibility(View.VISIBLE);
        circleBracket1.setVisibility(View.VISIBLE);
        txtCircleRadius.setVisibility(View.VISIBLE);
        hideSquare();
        hideRectangle();
    }

    private void hideRectangle() {
        perimeterRectangle.setVisibility(View.GONE);
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
        perimeterRectangle.setVisibility(View.VISIBLE);
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
        squareBracket1 = root.findViewById(R.id.perimeterSqBracket1);
        squareBracket2 = root.findViewById(R.id.perimeterSqBracket2);
        squareBracket3 = root.findViewById(R.id.perimeterSqBracket3);
        txtSquareSide = root.findViewById(R.id.txtPerimeterSqSide);

        perimeterRectangle = root.findViewById(R.id.perimeterRectangle);
        rectangleBracket1 = root.findViewById(R.id.perimeterReBracket1);
        rectangleBracket2 = root.findViewById(R.id.perimeterReBracket2);
        rectangleBracket3 = root.findViewById(R.id.perimeterReBracket3);
        rectangleBracket4 = root.findViewById(R.id.perimeterReBracket4);
        rectangleBracket5 = root.findViewById(R.id.perimeterReBracket5);
        rectangleBracket6 = root.findViewById(R.id.perimeterReBracket6);
        txtRectangleWidth = root.findViewById(R.id.txtPerimeterReWidth);
        txtRectangleLength = root.findViewById(R.id.txtPerimeterReLength);

        perimeterCircle = root.findViewById(R.id.perimeterCircle);
        circleBracket1 = root.findViewById(R.id.perimeterCiBracket1);
        txtCircleRadius = root.findViewById(R.id.txtPerimeterCiRadius);

        txtResult = root.findViewById(R.id.txtPerimeterResult);

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Perimeter");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}