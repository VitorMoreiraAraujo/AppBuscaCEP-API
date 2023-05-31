package br.com.etecia.appbuscacep_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button btnBuscarCep;
    EditText txtCep, lblCEP, lblLogradouro, lblComplemento, lblBairro, lblCidade;

    String [] item = {"Estados"};

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtCep = findViewById(R.id.txtCep);
        lblCEP = findViewById(R.id.lblCEP);
        lblLogradouro = findViewById(R.id.lblLogradouro);
        lblComplemento = findViewById(R.id.lblComplemento);
        lblBairro = findViewById(R.id.lblBairro);
        lblCidade = findViewById(R.id.lblCidade);

        btnBuscarCep = findViewById(R.id.btnBuscaCep);
        spinner = findViewById(R.id.spinnerEstado);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, item);
        spinner.setAdapter(adapter);

        //String [] estado = getResources().getStringArray(R.array.estados_array);

        //spinner.setAdapter( new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_main, estado));.

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item[0] = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String endereco = txtCep.getText().toString().trim();

                try {
                    //preencher o cep no lblResposta do layout
                    CEP retorno = new HttpService(txtCep.getText().toString().trim()).execute().get();
                    lblCEP.setText(retorno.getCep().toString());
                    lblLogradouro.setText(retorno.getLogradouro().toString());
                    lblComplemento.setText(retorno.getComplemento().toString());
                    lblBairro.setText(retorno.getBairro().toString());
                    lblCidade.setText(retorno.getLocalidade().toString());
                    item [0] = retorno.getUf().toString();
                    //spinner.setText(retorno.getUf().toString());


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}