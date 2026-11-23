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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.edinburghtransportation.R
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

    // stringResource() çağrıları Composable alanında yapılır.
    // Daha sonra bu hazır String değerlerini callback'lerde kullanabiliriz.
    val fillAllFieldsText =
        stringResource(R.string.fill_all_fields)

    val passwordsDoNotMatchText =
        stringResource(R.string.passwords_do_not_match)

    val accountCreatedText =
        stringResource(R.string.account_created)

    val profileUpdateFailedText =
        stringResource(R.string.profile_update_failed)

    val registrationFailedText =
        stringResource(R.string.registration_failed)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.create_account)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(R.string.register_description)
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
                Text(
                    text = stringResource(R.string.full_name)
                )
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
                Text(
                    text = stringResource(R.string.email)
                )
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
                Text(
                    text = stringResource(R.string.password)
                )
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
                Text(
                    text = stringResource(R.string.confirm_password)
                )
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
                        fillAllFieldsText,
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (password != confirmPassword) {

                    Toast.makeText(
                        context,
                        passwordsDoNotMatchText,
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
                                            accountCreatedText,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // Başarılı kayıt sonrası Ana Sayfaya git
                                        onRegisterSuccess()

                                    } else {

                                        Toast.makeText(
                                            context,
                                            profileUpdateFailedText,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {

                            Toast.makeText(
                                context,
                                registrationFailedText,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.register)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onLoginClick
        ) {
            Text(
                text = stringResource(R.string.already_have_account)
            )
        }
    }
}