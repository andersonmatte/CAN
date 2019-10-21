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

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText nome;
    @BindView(R.id.input_address)
    EditText endereco;
    @BindView(R.id.input_email)
    EditText email;
    @BindView(R.id.input_mobile)
    EditText telefone;
    @BindView(R.id.input_password)
    EditText senha;
    @BindView(R.id.input_reEnterPassword)
    EditText repetirSenha;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        if (!validarCadastro()) {
            onSignupFailed();
            return;
        }
        signupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.criando_conta));
        progressDialog.show();
        String name = nome.getText().toString();
        String address = endereco.getText().toString();
        String email = this.email.getText().toString();
        String mobile = telefone.getText().toString();
        String password = senha.getText().toString();
        String reEnterPassword = repetirSenha.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.login_invalido), Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validarCadastro() {
        boolean valid = true;
        String name = nome.getText().toString();
        String address = endereco.getText().toString();
        String email = this.email.getText().toString();
        String mobile = telefone.getText().toString();
        String password = senha.getText().toString();
        String reEnterPassword = repetirSenha.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nome.setError("at least 3 characters");
            valid = false;
        } else {
            nome.setError(null);
        }

        if (address.isEmpty()) {
            endereco.setError("Enter Valid Address");
            valid = false;
        } else {
            endereco.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("enter a valid email address");
            valid = false;
        } else {
            this.email.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            telefone.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            telefone.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            senha.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            senha.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            repetirSenha.setError("Password Do not match");
            valid = false;
        } else {
            repetirSenha.setError(null);
        }

        return valid;
    }

}
