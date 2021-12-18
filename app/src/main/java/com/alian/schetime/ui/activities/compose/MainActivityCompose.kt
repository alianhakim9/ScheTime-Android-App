package com.alian.schetime.ui.activities.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.alian.schetime.ui.activities.compose.ui.theme.ScheTimeTheme
import com.alian.schetime.ui.base.viewmodels.AuthViewModel
import com.alian.schetime.ui.base.viewmodels.factory.AuthViewModelFactory
import com.example.schetime.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivityCompose : ComponentActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        setContent {
            ScheTimeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = stringResource(id = R.string.image_content_desc),
                            modifier = Modifier.size(200.dp))

                        Text(
                            text = stringResource(id = R.string.label_splash_text),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 100.dp),
                            textAlign = TextAlign.Center)

                        Button(
                            onClick = {
                                viewModel.saveValueOnlyOnce()
                                Intent(context, WelcomeActivityCompose::class.java).also {
                                    context.startActivity(it)
                                    finish()
                                }
                            }, modifier = Modifier
                                .width(300.dp)
                                .padding(top = 20.dp)) {
                            Text(text = "Get Started")
                        }
                    }
                }
            }
        }
        viewModel.onlyOnce.observe(this, {
            Intent(this, WelcomeActivityCompose::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }
}