package br.ufrn.ect.lar.acelerometro.acelerometro;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class EnviarDadosActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket socket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    ConnectionThread connect;
    private Button btnBlue;
    private EditText editTextBlue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_dados);
        String address = "";
        editTextBlue = (EditText) findViewById(R.id.edtB);
        btnBlue = (Button) findViewById(R.id.btnBlue);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address = extras.getString("address");
            //Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
        connect = new ConnectionThread(address);
        connect.start();

        /* Um descanso rápido, para evitar bugs esquisitos.
         */
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dados = editTextBlue.getText().toString() + "\n";
                connect.write(dados.getBytes());

            }
        });
    }
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            if(dataString.equals("---N"))
                Log.i("msg","Ocorreu um erro durante a conexão D:");
            else if(dataString.equals("---S"))
                Log.i("msg","Conectado :D");
            else {

                Log.i("msg","oi");
            }
        }
    };
}
