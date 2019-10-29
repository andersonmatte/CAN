package br.com.can.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.can.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.input_name)
    EditText nome;
    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_password)
    EditText senha;
    @BindView(R.id.input_reEnterPassword)
    EditText repetirSenha;
    @BindView(R.id.btn_signup)
    Button botaoCadastrar;
    @BindView(R.id.link_login)
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novoCadastro();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.perfis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.perfis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Novo cadastro de usuário.
     */
    public void novoCadastro() {
        if (!this.validarCadastro()) {
            cadastroComFalha();
            return;
        }
        botaoCadastrar.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(CadastroActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.criando_conta));
        progressDialog.show();
        String name = nome.getText().toString();
        String email = this.email.getText().toString();
        String password = senha.getText().toString();
        String reEnterPassword = repetirSenha.getText().toString();

        // TODO: Implement your own novoCadastro logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either cadastroComSucesso or cadastroComFalha
                        // depending on success
                        cadastroComSucesso();
                        // cadastroComFalha();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void cadastroComSucesso() {
        botaoCadastrar.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void cadastroComFalha() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
        botaoCadastrar.setEnabled(true);
    }

    /**
     * Validações cadastrais dos dado informados na tela pelo usuário.
     */
    public boolean validarCadastro() {
        boolean valid = true;
        valid = this.isValidNome(valid);
        valid = this.isValidEmail(valid);
        valid = this.isValidSenha(valid);
        return valid;
    }

    /**
     * Valida se o nome informado pelo usuário é maior que três caracteres.
     */
    private boolean isValidNome(boolean valid) {
        if (this.nome.getText().toString().isEmpty() || this.nome.getText().toString().length() < 3) {
            this.nome.setError(getString(R.string.tres_caracteres));
            valid = false;
        } else {
            this.nome.setError(null);
        }
        return valid;
    }

    /**
     * Valida se o email informado pelo usuário é um email válido.
     */
    private boolean isValidEmail(boolean valid) {
        if (this.email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this.email.getText().toString()).matches()) {
            this.email.setError(getString(R.string.email_valido));
            valid = false;
        } else {
            this.email.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a senha informada pelo usuário é um númerico de tamnho seis e se a senha foi confirmada no campo de repetição de senha.
     */
    private boolean isValidSenha(boolean valid) {
        if (this.senha.getText().toString().length() != 6) {
            this.senha.setError(getString(R.string.seis_numericos));
            valid = false;
        } else {
            this.senha.setError(null);
        }

        if (!this.repetirSenha.getText().toString().equals(this.senha.getText().toString())) {
            this.repetirSenha.setError(getString(R.string.senha_nao_confere));
            valid = false;
        } else {
            this.repetirSenha.setError(null);
        }
        return valid;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

}
