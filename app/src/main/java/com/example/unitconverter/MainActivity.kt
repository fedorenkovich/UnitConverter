package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6F00), // Оранжевый
    secondary = Color(0xFFFFA000), // Светлый оранжевый
    background = Color(0xFFFFF3E0), // Светлый фон
    surface = Color(0xFFFFF3E0), // Светлая поверхность
    onPrimary = Color(0xFFFFFFFF), // Белый текст на основном цвете
    onSecondary = Color(0xFF000000), // Черный текст на вторичном цвете
    onBackground = Color(0xFF000000), // Черный текст на фоне
    onSurface = Color(0xFF000000), // Черный текст на поверхности
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF6F00), // Оранжевый
    secondary = Color(0xFFFFA000), // Светлый оранжевый
    background = Color(0xFF121212), // Темный фон
    surface = Color(0xFF1E1E1E), // Темная поверхность
    onPrimary = Color(0xFF000000), // Черный текст на основном цвете
    onSecondary = Color(0xFFFFFFFF), // Белый текст на вторичном цвете
    onBackground = Color(0xFFFFFFFF), // Белый текст на фоне
    onSurface = Color(0xFFFFFFFF), // Белый текст на поверхности
)

@Composable
fun UnitConverterAppTheme(
    useDarkTheme: Boolean = false, // По умолчанию используем светлую тему
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun UnitConverter(){
    UnitConverterAppTheme {
        var inputValue by remember { mutableStateOf("") }
        var outputValue by remember { mutableStateOf("0.0") }
        var inputUnit by remember { mutableStateOf("Meters") }
        var outputUnit by remember { mutableStateOf("Meters") }
        var inputExpanded by remember { mutableStateOf(false) }
        var outputExpanded by remember { mutableStateOf(false) }
        var inputConversionFactor = remember { mutableDoubleStateOf(1.0) }
        var outputConversionFactor = remember { mutableDoubleStateOf(1.0) }

        fun convertUnits() {
            val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
            val result =
                (inputValueDouble * inputConversionFactor.doubleValue * 100.0 / outputConversionFactor.doubleValue).roundToInt() / 100.0
            outputValue = result.toString()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Unit Converter",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = inputValue, onValueChange = {
                    inputValue = it
                    convertUnits()
                },
                label = { Text("Enter Value") })
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                //Input Box
                Box {
                    //Input Button
                    Button(onClick = { inputExpanded = true }) {
                        Text(text = inputUnit)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Down"
                        )
                    }
                    //Input Dropdown Menu
                    DropdownMenu(
                        expanded = inputExpanded,
                        onDismissRequest = { inputExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Centimeters") },
                            onClick = {
                                inputExpanded = false
                                inputUnit = "Centimeters"
                                inputConversionFactor.doubleValue = 0.01
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Meters") },
                            onClick = {
                                inputExpanded = false
                                inputUnit = "Meters"
                                inputConversionFactor.doubleValue = 1.0
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                inputExpanded = false
                                inputUnit = "Feet"
                                inputConversionFactor.doubleValue = 0.3048
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Millimeters") },
                            onClick = {
                                inputExpanded = false
                                inputUnit = "Millimeters"
                                inputConversionFactor.doubleValue = 0.001
                                convertUnits()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(32.dp))
                //Output Box
                Box {
                    //Output Button
                    Button(onClick = { outputExpanded = true }) {
                        Text(text = outputUnit)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Down"
                        )
                    }
                    //Output Dropdown Menu
                    DropdownMenu(
                        expanded = outputExpanded,
                        onDismissRequest = { outputExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Centimeters") },
                            onClick = {
                                outputExpanded = false
                                outputUnit = "Centimeters"
                                outputConversionFactor.doubleValue = 0.01
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Meters") },
                            onClick = {
                                outputExpanded = false
                                outputUnit = "Meters"
                                outputConversionFactor.doubleValue = 1.00
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                outputExpanded = false
                                outputUnit = "Feet"
                                outputConversionFactor.doubleValue = 0.3048
                                convertUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Millimeters") },
                            onClick = {
                                outputExpanded = false
                                outputUnit = "Millimeters"
                                outputConversionFactor.doubleValue = 0.001
                                convertUnits()
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Result: $outputValue $outputUnit",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}
