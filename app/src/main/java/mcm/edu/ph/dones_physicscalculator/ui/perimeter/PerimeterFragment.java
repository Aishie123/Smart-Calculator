package mcm.edu.ph.dones_physicscalculator.ui.perimeter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private TextView txtPerimeter, txtFormula, txtSquareSide, txtRectangleWidth, txtRectangleLength, txtCircleRadius, txtResult;
    private View root, perimeterSquare, squareBracket1, squareBracket2, squareBracket3,
            perimeterRectangle, rectangleBracket1, rectangleBracket2, rectangleBracket3, rectangleBracket4, rectangleBracket5, rectangleBracket6,
            perimeterCircle, circleBracket1;
    private double result, dLength, dWidth;
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
            case R.id.btnPerimeterSolve:
                txtResult.setVisibility(View.VISIBLE);
                solvePerimeter();
                break;
            case R.id.btnPerimeterRestart:
                hideAll();
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter();
                break;
        }
    }

    private double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
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
                    result = getSqPerimeter(x1);
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

                        String sX2 = etX2.getText().toString();
                        double x2 = Double.parseDouble(sX2);

                        dWidth = getReWidth( x1, x2 );
                        dLength = getReLength( x1, x2 );
                        result = getRePerimeter( x1, x2 );

                        txtFormula.setText("2(" + sX1 + " + " + sX2 + " )");
                        txtRectangleLength.setText(sX1);
                        txtRectangleWidth.setText(sX2);

                        ConstraintLayout.LayoutParams rectangle = (ConstraintLayout.LayoutParams)perimeterRectangle.getLayoutParams();
                        ConstraintLayout.LayoutParams width = (ConstraintLayout.LayoutParams)rectangleBracket1.getLayoutParams();
                        ConstraintLayout.LayoutParams length = (ConstraintLayout.LayoutParams)rectangleBracket4.getLayoutParams();
                        rectangle.width = dpToPx(dLength, root.getContext());
                        length.width = dpToPx(dLength, root.getContext());
                        width.height = dpToPx(dWidth, root.getContext());
                        rectangle.height = dpToPx(dWidth, root.getContext());
                        perimeterRectangle.setLayoutParams(rectangle);
                        rectangleBracket1.setLayoutParams(width);
                        rectangleBracket4.setLayoutParams(length);

                        showRectangle();
                    }
                    break;

                case 3:
                    result = getCircumference(x1);
                    txtFormula.setText( "2π(" + sX1 + ")" );
                    txtCircleRadius.setText(sX1);
                    showCircle();
                    break;

                default:
                    Toast.makeText(getActivity(), "Please choose a shape.",
                            Toast.LENGTH_LONG).show();
            }

            gifPerimeter.setVisibility(View.INVISIBLE);
            txtResult.setText("= " + result);

        }
    }

    public double getSqPerimeter( double s ) { return roundTwoDecimals( 4 * s ); }
    public double getRePerimeter( double l, double w ) { return roundTwoDecimals( 2 * (l + w) ); }
    public double getCircumference( double r ) { return roundTwoDecimals(2 * Math.PI * r); }

    public double getReWidth ( double l, double w ){
        double n;
        if ( l > w ) { n = roundTwoDecimals((250 * w) / l); }
        else if ( w > l ) { n = 150; }
        else { n = 150; }
        return n;
    }

    public double getReLength ( double l, double w ){
        double n;
        if ( l > w ) { n = 250; }
        else if ( w > l ) { n = roundTwoDecimals( (150 * l) / w ); }
        else { n = 150; }
        return n;
    }

    public static int dpToPx(double dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
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
        squareBracket1 = root.findViewById(R.id.squareBracket1);
        squareBracket2 = root.findViewById(R.id.squareBracket2);
        squareBracket3 = root.findViewById(R.id.squareBracket3);
        txtSquareSide = root.findViewById(R.id.txtSquareSide);

        perimeterRectangle = root.findViewById(R.id.perimeterRectangle);
        rectangleBracket1 = root.findViewById(R.id.rectangleBracket1);
        rectangleBracket2 = root.findViewById(R.id.rectangleBracket2);
        rectangleBracket3 = root.findViewById(R.id.rectangleBracket3);
        rectangleBracket4 = root.findViewById(R.id.rectangleBracket4);
        rectangleBracket5 = root.findViewById(R.id.rectangleBracket5);
        rectangleBracket6 = root.findViewById(R.id.rectangleBracket6);
        txtRectangleWidth = root.findViewById(R.id.txtRectangleWidth);
        txtRectangleLength = root.findViewById(R.id.txtRectangleLength);

        perimeterCircle = root.findViewById(R.id.perimeterCircle);
        circleBracket1 = root.findViewById(R.id.circleBracket1);
        txtCircleRadius = root.findViewById(R.id.txtCircleRadius);

        txtResult = root.findViewById(R.id.txtPerimeterResult);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}