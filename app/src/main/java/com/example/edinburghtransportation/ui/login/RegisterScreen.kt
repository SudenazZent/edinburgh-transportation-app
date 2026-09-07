package com.example.edinburghtransportation.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hesap Oluştur"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Yolculuğunuza başlamak için bir hesap oluşturun"
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Ad Soyad")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("E-posta")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Şifre")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = {
                Text("Şifre Tekrar")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                if (
                    name.isBlank() ||
                    email.isBlank() ||
                    password.isBlank() ||
                    confirmPassword.isBlank()
                ) {

                    Toast.makeText(
                        context,
                        "Lütfen tüm alanları doldurun",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (password != confirmPassword) {

                    Toast.makeText(
                        context,
                        "Şifreler eşleşmiyor",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    auth.createUserWithEmailAndPassword(
                        email,
                        password
                    ).addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            val profileUpdates =
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()

                            auth.currentUser
                                ?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileTask ->

                                    if (profileTask.isSuccessful) {

                                        Toast.makeText(
                                            context,
                                            "Hesabınız başarıyla oluşturuldu!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // Başarılı kayıt sonrası Ana Sayfaya git
                                        onRegisterSuccess()

                                    } else {

                                        Toast.makeText(
                                            context,
                                            "Profil güncellenemedi",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {

                            Toast.makeText(
                                context,
                                "Kayıt işlemi başarısız oldu",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kayıt Ol")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onLoginClick
        ) {
            Text("Zaten hesabınız var mı? Giriş Yap")
        }
    }
}