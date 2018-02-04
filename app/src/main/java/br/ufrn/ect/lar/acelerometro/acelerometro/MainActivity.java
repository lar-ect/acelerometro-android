package br.ufrn.ect.lar.acelerometro.acelerometro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvX;
    private TextView tvY;
    private TextView tvZ;
    private TextView tvProblem;
    private Button btnCapturar;
    private EditText edtArquivo;
    private Boolean liberarTamanho = false;
    ArrayList posicoes = new ArrayList();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvX = (TextView) findViewById(R.id.tvX);
        tvY = (TextView) findViewById(R.id.tvY);
        tvZ = (TextView) findViewById(R.id.tvZ);
        edtArquivo = findViewById(R.id.edtArquivo);
        btnCapturar = (Button) findViewById(R.id.btnCapturar);
        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, 500000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {

        btnCapturar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        flag = true;
                        posicoes.clear();
                        break;
                    case MotionEvent.ACTION_UP:
                        flag = false;
                        liberarTamanho = true;
                        break;
                }
                return false;
            }
        });
        if(flag){
            Float x = event.values[0];
            Float y = event.values[1];
            Float z = event.values[2];
            tvX.setText("Posição x: "+ x);
            tvY.setText("Posição y: "+y);
            tvZ.setText("Posição z: "+z);
            posicoes.add(x);
            posicoes.add(y);
            posicoes.add(z);
        }
        if(liberarTamanho){
            tvX.setText("Aguardando ação");
            tvY.setText("Aguardando ação");
            tvZ.setText("Aguardando ação");
            //Toast.makeText(this, "Tamanho da lista criada: " + posicoes.size(), Toast.LENGTH_SHORT).show();
            try {
                String nome;
                nome = edtArquivo.getText().toString();
                File myFile = new File("/sdcard/"+nome);
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                for(int i =0; i<posicoes.size(); i++) {
                    myOutWriter.append(posicoes.get(i) + "\n");
                }
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Arquivo '"+nome+"'" + "criado na raiz do telefone.",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }



            liberarTamanho = false;
        }
    }
}
