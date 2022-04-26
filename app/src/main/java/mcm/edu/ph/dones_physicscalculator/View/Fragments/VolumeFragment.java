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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mcm.edu.ph.dones_physicscalculator.Controller.Calculation;
import mcm.edu.ph.dones_physicscalculator.R;
import mcm.edu.ph.dones_physicscalculator.View.Activities.MainActivity;
import mcm.edu.ph.dones_physicscalculator.databinding.FragmentVolumeBinding;
import pl.droidsonroids.gif.GifImageView;

public class VolumeFragment extends Fragment implements View.OnClickListener{

    private GifImageView gifVolume;
    private FragmentVolumeBinding binding;
    private Spinner spinner;
    private EditText etX1, etX2, etX3;
    private TextView txtVolume, txtFormula, txtVolumeCueSide, txtVolumeCudLength, txtVolumeCudWidth, txtVolumeCudHeight,
        txtVolumeSpRadius, txtVolumeCyHeight, txtVolumeCyRadius, txtVolumeCoRadius, txtVolumeCoHeight, txtResult;
    private ImageView volumeCube, volumeCuboid, volumeSphere, volumeCylinder, volumeCone;
    private View root, volumeSpRadius, volumeCyRadius, volumeCoRadius, volumeCoHeight;
    private Button btnSolve, btnRestart;
    private String TAG = "VolumeFragment";
    private double result, x1, x2, x3;
    private int shapeType;
    private final Calculation calc = new Calculation();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVolumeBinding.inflate(inflater, container, false);
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
        String[] paths = {"Cube", "Cuboid", "Sphere", "Cylinder", "Right Circular Cone"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {

                    case 0:
                        // Cube
                        Log.d(TAG, "Cube is selected");
                        shapeType = 1;
                        txtVolume.setText("Volume of a cube:");
                        txtFormula.setText(Html.fromHtml("(s)<sup>3</sup>"));
                        etX1.setHint("s");
                        etX2.setVisibility(View.GONE);
                        etX3.setVisibility(View.GONE);
                        break;

                    case 1:
                        // Cuboid
                        Log.d(TAG, "Cuboid is selected");
                        shapeType = 2;
                        txtVolume.setText("Volume of a cuboid:");
                        txtFormula.setText( "(l • w • h)" );
                        etX1.setHint("l");
                        etX2.setHint("w");
                        etX3.setHint("h");
                        etX2.setVisibility(View.VISIBLE);
                        etX3.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        // Sphere
                        Log.d(TAG, "Sphere is selected");
                        shapeType = 3;
                        txtVolume.setText("Volume of a sphere:");
                        txtFormula.setText(Html.fromHtml("(<sup>4</sup>/<sub>3</sub>)π(r)<sup>3</sup>"));
                        etX1.setHint("r");
                        etX2.setVisibility(View.GONE);
                        etX3.setVisibility(View.GONE);
                        break;

                    case 3:
                        // Cylinder
                        Log.d(TAG, "Cylinder is selected");
                        shapeType = 4;
                        txtVolume.setText("Volume of a cylinder:");
                        txtFormula.setText(Html.fromHtml("π(r)<sup>2</sup>(h)"));
                        etX1.setHint("r");
                        etX2.setHint("h");
                        etX2.setVisibility(View.VISIBLE);
                        etX3.setVisibility(View.GONE);
                        break;

                    case 4:
                        // Right Circular Cone
                        Log.d(TAG, "Right Circular Cone is selected");
                        shapeType = 5;
                        txtVolume.setText("Area of a right circular cone:");
                        txtFormula.setText(Html.fromHtml("π(r)<sup>2</sup>(<sup>h</sup>/<sub>3</sub>)"));
                        etX1.setHint("r");
                        etX2.setHint("h");
                        etX2.setVisibility(View.VISIBLE);
                        etX3.setVisibility(View.GONE);
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
            case R.id.btnVolumeSolve:
                solveVolume(); // calculates the volume
                break;

            // if "Restart" button is pressed
            case R.id.btnVolumeRestart:
                hideAll();
                txtResult.setVisibility(View.GONE);
                initSpinnerFooter(); // initializes the spinner again
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void solveVolume(){
        if (TextUtils.isEmpty(etX1.getText())){
            Toast.makeText(getActivity(), "Please input the required value(s).",
                    Toast.LENGTH_LONG).show();
        }
        else {

            String sX1 = etX1.getText().toString();
            String sX2 = etX2.getText().toString();
            String sX3 = etX3.getText().toString();
            x1 = Double.parseDouble(sX1);

            switch (shapeType) {

                case 1: // cube
                    result = calc.getCueVolume(x1);
                    txtFormula.setText(Html.fromHtml("(" + sX1 + ")<sup>3</sup>"));
                    txtVolumeCueSide.setText(sX1);
                    showCube();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                case 2: // cuboid
                    if (TextUtils.isEmpty(etX2.getText()) || TextUtils.isEmpty(etX3.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        x2 = Double.parseDouble(sX2);
                        x3 = Double.parseDouble(sX3);

                        result = calc.getCudVolume( x1, x2, x3 );
                        txtFormula.setText("(" + sX1 + " • " + sX2 + " • " + sX3 + ")");
                        txtVolumeCudLength.setText(sX1);
                        txtVolumeCudWidth.setText(sX2);
                        txtVolumeCudHeight.setText(sX3);

                        showCuboid();
                        txtResult.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3: // sphere
                    result = calc.getSpVolume(x1);
                    txtFormula.setText(Html.fromHtml("(<sup>4</sup>/<sub>3</sub>)π(" + sX1 + ")<sup>3</sup>"));
                    txtVolumeSpRadius.setText(sX1);
                    showSphere();
                    txtResult.setVisibility(View.VISIBLE);
                    break;

                case 4: // Cylinder
                    if (TextUtils.isEmpty(etX2.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        x2 = Double.parseDouble(sX2);

                        result = calc.getCyVolume(x1, x2);
                        txtFormula.setText(Html.fromHtml("π(" + sX1 + ")<sup>2</sup>(" + sX2 + ")"));
                        txtVolumeCyRadius.setText(sX1);
                        txtVolumeCyHeight.setText(sX2);
                        showCylinder();
                        txtResult.setVisibility(View.VISIBLE);
                    }
                    break;

                case 5: //cone
                    if (TextUtils.isEmpty(etX2.getText())) {
                        Toast.makeText(getActivity(), "Please input the required value(s).",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        x2 = Double.parseDouble(sX2);

                        result = calc.getCoVolume(x1, x2);
                        txtFormula.setText(Html.fromHtml("π(" + sX1 + ")<sup>2</sup>(<sup>" + sX2 + "</sup>/<sub>3</sub>)"));
                        txtVolumeCoRadius.setText(sX1);
                        txtVolumeCoHeight.setText(sX2);
                        showCone();
                        txtResult.setVisibility(View.VISIBLE);
                    }
                    break;

                default:
                    Toast.makeText(getActivity(), "Please choose a shape.",
                            Toast.LENGTH_LONG).show();
            }

            gifVolume.setVisibility(View.INVISIBLE);
            txtResult.setText("= " + result);

        }
    }

    private void hideAll() {
        gifVolume.setVisibility(View.VISIBLE);
        hideCube();
        hideCuboid();
        hideSphere();
        hideCylinder();
        hideCone();
    }

    private void hideCube() {
        volumeCube.setVisibility(View.GONE);
        txtVolumeCueSide.setVisibility(View.GONE);
    }

    private void showCube() {
        volumeCube.setVisibility(View.VISIBLE);
        txtVolumeCueSide.setVisibility(View.VISIBLE);
        hideCuboid();
        hideSphere();
        hideCylinder();
        hideCone();
    }

    private void hideCuboid() {
        volumeCuboid.setVisibility(View.GONE);
        txtVolumeCudLength.setVisibility(View.GONE);
        txtVolumeCudWidth.setVisibility(View.GONE);
        txtVolumeCudHeight.setVisibility(View.GONE);
    }

    private void showCuboid() {
        volumeCuboid.setVisibility(View.VISIBLE);
        txtVolumeCudLength.setVisibility(View.VISIBLE);
        txtVolumeCudWidth.setVisibility(View.VISIBLE);
        txtVolumeCudHeight.setVisibility(View.VISIBLE);
        hideCube();
        hideSphere();
        hideCylinder();
        hideCone();
    }

    private void hideSphere() {
        volumeSphere.setVisibility(View.GONE);
        volumeSpRadius.setVisibility(View.GONE);
        txtVolumeSpRadius.setVisibility(View.GONE);
    }

    private void showSphere() {
        volumeSphere.setVisibility(View.VISIBLE);
        volumeSpRadius.setVisibility(View.VISIBLE);
        txtVolumeSpRadius.setVisibility(View.VISIBLE);
        hideCube();
        hideCuboid();
        hideCylinder();
        hideCone();
    }

    private void hideCylinder() {
        volumeCylinder.setVisibility(View.GONE);
        volumeCyRadius.setVisibility(View.GONE);
        txtVolumeCyRadius.setVisibility(View.GONE);
        txtVolumeCyHeight.setVisibility(View.GONE);
    }

    private void showCylinder() {
        volumeCylinder.setVisibility(View.VISIBLE);
        volumeCyRadius.setVisibility(View.VISIBLE);
        txtVolumeCyRadius.setVisibility(View.VISIBLE);
        txtVolumeCyHeight.setVisibility(View.VISIBLE);
        hideCube();
        hideCuboid();
        hideSphere();
        hideCone();
    }

    private void hideCone() {
        volumeCone.setVisibility(View.GONE);
        volumeCoRadius.setVisibility(View.GONE);
        volumeCoHeight.setVisibility(View.GONE);
        txtVolumeCoRadius.setVisibility(View.GONE);
        txtVolumeCoHeight.setVisibility(View.GONE);
    }

    private void showCone() {
        volumeCone.setVisibility(View.VISIBLE);
        volumeCoRadius.setVisibility(View.VISIBLE);
        volumeCoHeight.setVisibility(View.VISIBLE);
        txtVolumeCoRadius.setVisibility(View.VISIBLE);
        txtVolumeCoHeight.setVisibility(View.VISIBLE);
        hideCube();
        hideCuboid();
        hideSphere();
        hideCylinder();
    }

    private void initUI() {

        gifVolume = root.findViewById(R.id.gifVolume);
        spinner = root.findViewById(R.id.dropdownVolume);
        txtVolume = root.findViewById(R.id.txtVolume);
        txtFormula= root.findViewById(R.id.txtVolumeFormula);
        etX1 = root.findViewById(R.id.etVolumeX1);
        etX2 = root.findViewById(R.id.etVolumeX2);
        etX3 = root.findViewById(R.id.etVolumeX3);
        btnSolve = root.findViewById(R.id.btnVolumeSolve);
        btnRestart = root.findViewById(R.id.btnVolumeRestart);

        volumeCube = root.findViewById(R.id.volumeCube);
        txtVolumeCueSide = root.findViewById(R.id.txtVolumeCueSide);

        volumeCuboid = root.findViewById(R.id.volumeCuboid);
        txtVolumeCudHeight = root.findViewById(R.id.txtVolumeCudHeight);
        txtVolumeCudLength = root.findViewById(R.id.txtVolumeCudLength);
        txtVolumeCudWidth = root.findViewById(R.id.txtVolumeCudWidth);

        volumeSphere = root.findViewById(R.id.volumeSphere);
        volumeSpRadius = root.findViewById(R.id.volumeSpRadius);
        txtVolumeSpRadius = root.findViewById(R.id.txtVolumeSpRadius);

        volumeCylinder = root.findViewById(R.id.volumeCylinder);
        volumeCyRadius = root.findViewById(R.id.volumeCyRadius);
        txtVolumeCyHeight = root.findViewById(R.id.txtVolumeCyHeight);
        txtVolumeCyRadius = root.findViewById(R.id.txtVolumeCyRadius);

        volumeCone = root.findViewById(R.id.volumeCone);
        volumeCoRadius = root.findViewById(R.id.volumeCoRadius);
        volumeCoHeight = root.findViewById(R.id.volumeCoHeight);
        txtVolumeCoRadius = root.findViewById(R.id.txtVolumeCoRadius);
        txtVolumeCoHeight = root.findViewById(R.id.txtVolumeCoHeight);

        txtResult = root.findViewById(R.id.txtVolumeResult);

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Volume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}