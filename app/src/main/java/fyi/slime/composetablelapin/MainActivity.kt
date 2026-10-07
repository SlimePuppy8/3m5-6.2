package fyi.slime.composetablelapin

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fyi.slime.composetablelapin.ui.theme.ComposeTableLapinTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTableLapinTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Tape le lapin") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    EcranPrincipale(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EcranPrincipale(modifier: Modifier = Modifier) {
    var nbPaf by remember{mutableIntStateOf(0)}
	var nbFlop by remember{mutableIntStateOf(0)}

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AffichageScores(
            nbPaf = nbPaf,
            nbFlop = nbFlop,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        TitreApplication(
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        GrilleTuiles(
            incrementerPafs = {nbPaf++},
            incrementerFlops = {nbFlop++},
            modifier = Modifier.fillMaxSize().weight(5f)
        )
    }
}

@Composable
private fun TitreApplication(modifier : Modifier = Modifier) {
    Box (modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = "Tape le lapin",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AffichageScores(nbPaf : Int = 0, nbFlop : Int = 0, modifier : Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text(
                text = "${nbPaf} Pafs",
                color = Color.Green,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text(
                text = "${nbFlop} Flops",
                color = Color.Red,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GrilleTuiles(
    incrementerPafs : () -> Unit,
    incrementerFlops : () -> Unit,
    modifier: Modifier = Modifier
) {
    var positionLapin by remember { mutableIntStateOf(Random.nextInt(9)) }

    Column(modifier = modifier) {
        for (i: Int in 0..2) {
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                for (j: Int in 0..2) {
                    var indexTuile = i*3 +j
                    Tuile(
                        incrementerPafs = incrementerPafs,
                        incrementerFlops = incrementerFlops,
                        estLapin = indexTuile == positionLapin,
                        modifier = modifier.padding(6.dp).weight(1f),
                        reInitPosLapin = {positionLapin = Random.nextInt(9)}
                    )
                }
            }
        }
    }
}

@Composable
private fun Tuile(
    incrementerPafs : () -> Unit,
    incrementerFlops : () -> Unit,
    reInitPosLapin : () -> Unit,
    modifier: Modifier,
    estLapin: Boolean
) {
    Button(
        onClick = {
			if(estLapin) {
                incrementerPafs()
                reInitPosLapin()
            } else incrementerFlops()
		},
        modifier = modifier
    ) {
        Text(
            text = if(estLapin) "Lapin" else "Taupe",
            fontSize = 25.sp
        )
    }
}
