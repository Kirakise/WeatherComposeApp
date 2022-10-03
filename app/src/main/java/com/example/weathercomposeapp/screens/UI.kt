package com.example.weathercomposeapp.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercomposeapp.data.WeatherModel
import com.example.weathercomposeapp.ui.theme.Chosen


@Composable
@Preview
fun ListItem(item : WeatherModel) {
    Card(modifier = Modifier.fillMaxWidth().padding(top = 3.dp),
        backgroundColor = Chosen,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment =  Alignment.CenterVertically){
        Column(modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp)) {
            Text(text = item.time.ifEmpty { "12:00" }, color = Color.White)
            Text(text = item.condition.ifEmpty { "Sunny" }, color = Color.White)
        }
        Text(text = item.currentTemp.ifEmpty { "25C" }, color = Color.White, style = TextStyle(fontSize = 25.sp))
        AsyncImage(
            model = "http://openweathermap.org/img/wn/" + item.icon + "@2x.png",
            contentDescription = "im2",
            modifier = Modifier.size(35.dp),
        )
    }
    }
    
}