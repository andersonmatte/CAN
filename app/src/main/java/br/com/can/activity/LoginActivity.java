package br.com.can.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.can.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST = 0;

    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_password)
    EditText senha;
    @BindView(R.id.btn_login)
    Button botaoLogin;
    @BindView(R.id.link_cadastro)
    TextView cadastro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        this.botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        redirecionaCadastro();
    }

    /**
     * redireciona o usuário para a craiação de um novo retgistro de usuário no APP
     */
    private void redirecionaCadastro() {
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivityForResult(intent, REQUEST);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Funcionamento do Login.
     */
    public void login() {
        if (!validarLogin()) {
            loginFalha();
            return;
        }
        botaoLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.autenticando));
        progressDialog.show();

        String email = this.email.getText().toString();
        String password = senha.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either loginSucesso or loginFalha
                        loginSucesso();
                        // loginFalha();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful novoCadastro logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void loginSucesso() {
        this.botaoLogin.setEnabled(true);
        finish();
    }

    public void loginFalha() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
        this.botaoLogin.setEnabled(true);
    }

    /**
     * Validação inicial do login, validando apenas os dados informados pelo usuário ainda sem fazer a consistência doas dados na API/BD.
     */
    public boolean validarLogin() {
        boolean valid = true;
        valid = validarEmail(valid);
        valid = validarSenha(valid);
        return valid;
    }

    /**
     * Valida se o email informado pelo usuário é um email válido.
     */
    private boolean validarEmail(boolean valid) {
        if (this.email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this.email.getText().toString()).matches()) {
            this.email.setError(getString(R.string.email_invalido));
            valid = false;
        } else {
            this.email.setError(null);
        }
        return valid;
    }

    /**
     * Valida se a senha informada pelo usuário é um númerico de tamnho seis.
     */
    private boolean validarSenha(boolean valid) {
        if (this.senha.getText().toString().length() != 6) {
            this.senha.setError(getString(R.string.seis_numericos));
            valid = false;
        } else {
            this.senha.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

}
