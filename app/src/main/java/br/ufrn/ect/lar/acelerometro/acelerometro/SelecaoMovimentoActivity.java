package br.ufrn.ect.lar.acelerometro.acelerometro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SelecaoMovimentoActivity extends AppCompatActivity {
    private Button frente;
    private Button esquerda;
    private Button loop;
    private Button re;
    private Button direita;
    private Button botaoIf;
    private Button botaoBluetooh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_movimento);
        frente = (Button) findViewById(R.id.frente);
        esquerda = (Button) findViewById(R.id.esquerda);
        loop = (Button) findViewById(R.id.loop);
        re = (Button) findViewById(R.id.re);
        direita = (Button) findViewById(R.id.direita);
        botaoIf = (Button) findViewById(R.id.botaoif);
        botaoBluetooh  = (Button) findViewById(R.id.bluetooh);
        Boolean statusLoad = false;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            statusLoad = extras.getBoolean("statusPasta");
        }
        final Boolean statusPasta = statusLoad;
        frente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("frente", statusPasta);
            }
        });
        esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("esquerda", statusPasta);
            }
        });
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("loop", statusPasta);
            }
        });
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("re", statusPasta);
            }
        });
        direita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("direita", statusPasta);
            }
        });
        botaoIf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaActivityMain("frente", statusPasta);
            }
        });
        botaoBluetooh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BluetoohActivity.class));
            }
        });
    }
    private void irParaActivityMain(String botaoClicado, Boolean status){
        String nomePasta;
        if(!status){
            nomePasta = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
            //Toast.makeText(this, nomePasta, Toast.LENGTH_SHORT).show();
        }else{
            Bundle extras = getIntent().getExtras();
            nomePasta = extras.getString("nomePasta");
        }
        //Toast.makeText(this, nomePasta, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("nomePasta",nomePasta);
        intent.putExtra("botaoClicado",botaoClicado);
        intent.putExtra("statusPasta",status);
        startActivity(intent);
    }
}
