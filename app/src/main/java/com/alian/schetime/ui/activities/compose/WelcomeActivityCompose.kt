package com.alian.schetime.ui.activities.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.activities.HomeActivity
import com.alian.schetime.ui.activities.auth.SignInActivity
import com.alian.schetime.ui.activities.auth.SignUpActivity
import com.alian.schetime.ui.activities.compose.ui.theme.ScheTimeTheme
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.example.schetime.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class WelcomeActivityCompose : ComponentActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
            val context = LocalContext.current
            ScheTimeTheme {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.welcome_logo),
                        contentDescription = stringResource(id = R.string.image_content_desc),
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth())
                    Text(
                        text = stringResource(id = R.string.label_welcome_title),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(top = 20.dp, bottom = 5.dp),
                        fontWeight = FontWeight.Bold)

                    Text(
                        text = stringResource(id = R.string.label_welcome_text),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(bottom = 20.dp))

                    Button(onClick = {
                        Intent(context, SignInActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }, modifier = Modifier
                        .width(300.dp)) {
                        Text(text = "Sign in")
                    }

                    OutlinedButton(onClick = {
                        Intent(context, SignUpActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }, modifier = Modifier
                        .width(300.dp)
                        .padding(top = 5.dp)) {
                        Text(text = "Sign up")
                    }
                }
            }

            viewModel.isLoggedIn.observe(this, {
                Intent(context, HomeActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(it)
                    finish()
                }
            })
        }
    }
}

