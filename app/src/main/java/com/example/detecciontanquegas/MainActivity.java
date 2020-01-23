package com.example.detecciontanquegas;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    EditText txtUsuario, txtPasswd;
    Button btnLogin, btnRegistro;
    TextView registrobtn;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    static final int GOOGLE_SIGN_IN = 123;
    ImageView googleicon;
    //datos para iniciar sesion
    String email;
    String password;
    //obtener informacion del usuario
    FirebaseAuth nAuth;
    // atributos para el video
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referencias a los controles

        nAuth = FirebaseAuth.getInstance();


        Videothing(); // SOBRE EL VIDEO

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_app_id)).requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        googleicon = (ImageView) findViewById(R.id.googleicon);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPasswd = (EditText) findViewById(R.id.txtPasswd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        registrobtn = (TextView) findViewById(R.id.signIn_text);
        registrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, formulario_registro.class));
            }
        });

        googleicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtUsuario.getText().toString();
                String password=txtPasswd.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email patter set error
                    txtUsuario.setError("Formato Incorrecto");
                    txtUsuario.setFocusable(true);
                }
                else if(TextUtils.isEmpty(email)){
                    txtUsuario.setError("SE REQUIERE CORREO ELECTRONICO");
                    txtUsuario.setFocusable(true);
                }
                else if(TextUtils.isEmpty(password)){
                    txtPasswd.setError("SE REQUIERE SU CONTRASEÑA");
                    txtPasswd.setFocusable(true);

                }
                else{
                    loginUser(email,password);
                }




            }
        });

    }

    private void loginUser(String email,String password){
        nAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = nAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this,PantallaPrincipal.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Videothing(){
        videoBG = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.fire); // and then finally add your video resource. Make sure it is stored
        // in the raw folder.
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(
                    data);
            try {
                GoogleSignInAccount account =task.getResult(ApiException.class);
                if (account != null)
                    firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Log.w("TAG", "Fallo el inicio de sesión con google.", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        nAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = nAuth.getCurrentUser();
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        //Obtengo el email y el id del usuario ingresado
                        String userEmail = user.getEmail();
                        String uid = user.getUid();
                        String nombre = user.getDisplayName();
                        //String nombres = user.getDisplayName();
                        //String nombre=acct.getGivenName().toString().toUpperCase();
                        //String apellido=acct.getFamilyName().toString().toUpperCase();
                        //Uri urlFoto= acct.getPhotoUrl()
                        //String telefono = user.getPhoneNumber();
                        //String tipoCuenta = getIntent().getStringExtra("tipo de cuenta");

                        //Guardo en un HashMap
                        HashMap<Object,String> hashMap = new HashMap<>();

                        hashMap.put("email",userEmail);

                        hashMap.put("name",nombre);
                        //Instancia de Firebase
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users");
                        //reference.child(uid).setValue(hashMap);
                    }
                    startActivity(new Intent(MainActivity.this,PantallaPrincipal.class));
                    finish();
                    //updateUI(user);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "Fallo en inicio de sesion",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }




            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //si el usuario ha iniciado sesion
        FirebaseUser currentUser = nAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(getApplicationContext(), PantallaPrincipal.class));

        }
    }



}
/*
    public void login(View view){
        String username= txtUsuario.getText().toString();
        String password=txtPasswd.getText().toString();
        nAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Hubo un error",Toast.LENGTH_LONG).show();
                }else{
                }
            }
        });
    }*/



    /* public void registrarse(View view) {
        Intent intent = new Intent(this, formulario_registro.class);
        startActivity(intent);
    }
    /*public void login(View view) {
        Toast toast=Toast.makeText(getApplicationContext(),"Usted no cuenta con un usuario",Toast.LENGTH_SHORT);
        toast.show();
    }*/



   /* public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            Log.d("mensaje","ïngreso");
        }else if(v.getId() == R.id.btnRegistro) {
        }
    }*/