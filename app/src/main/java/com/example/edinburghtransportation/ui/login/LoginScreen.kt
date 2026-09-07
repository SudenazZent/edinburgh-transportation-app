package com.example.edinburghtransportation.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val primaryBlue = Color(0xFF2F67B1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(
                horizontal = 24.dp,
                vertical = 70.dp
            ),

        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Giriş",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Hoş Geldiniz!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Devam etmek için giriş yapın.",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "E-posta",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = {
                Text("E-posta adresiniz")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryBlue,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Şifre",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text("Şifreniz")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryBlue,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        TextButton(
            onClick = {
                // Daha sonra şifre sıfırlama ekranına bağlanacak
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Şifremi Unuttum?",
                color = primaryBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                if (email.isBlank() || password.isBlank()) {

                    Toast.makeText(
                        context,
                        "Lütfen tüm alanları doldurun",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    auth.signInWithEmailAndPassword(
                        email,
                        password
                    ).addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Giriş başarılı!",
                                Toast.LENGTH_SHORT
                            ).show()

                            onLoginSuccess()

                        } else {

                            Toast.makeText(
                                context,
                                task.exception?.message
                                    ?: "Giriş başarısız",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue
            )
        ) {

            Text(
                text = "Giriş Yap",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Text(
                text = "Hesabınız yok mu? ",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Text(
                text = "Kayıt Ol",
                color = primaryBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}