package com.example.weathercomposeapp.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.data.WeatherModel
import com.example.weathercomposeapp.ui.theme.Chosen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Chosen,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "20 июня 2022",
                        style = TextStyle(fontSize = 25.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        model = "http://openweathermap.org/img/wn/01d@2x.png",
                        contentDescription = "im2",
                        modifier = Modifier.size(35.dp),
                    )
                }

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Moscow",
                    style = TextStyle(fontSize = 30.sp),
                    color = Color.White,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "16 C",
                    style = TextStyle(fontSize = 35.sp),
                    color = Color.White,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Sunny",
                    style = TextStyle(fontSize = 25.sp),
                    color = Color.White,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "im3",
                            tint = Color.White
                        )
                    }
                    Text(text = "Delimiter")
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sync_24),
                            contentDescription = "im4",
                            tint = Color.White
                        )

                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daylist : MutableState<List<WeatherModel>>) {
    val tablist = listOf("HOURS", "DAYS")
    var pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp)
            .clip(RoundedCornerShape(5.dp)))
    {
            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { pos ->
                            TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(
                                    pagerState, pos
                                )
                            )
                },
                backgroundColor = Chosen,
                contentColor = Color.White) {
                tablist.forEachIndexed{index, text ->
                        Tab(selected = false,
                            onClick = {
                                      coroutineScope.launch {
                                          pagerState.animateScrollToPage(index)
                                      }
                        },
                            text = {
                                Text(text = text)
                            },
                        )
                }
            }
            HorizontalPager(count = tablist.size, state = pagerState, modifier = Modifier.weight(1.0f)) {
                index ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(
                            daylist.value
                    ){
                        _, item -> ListItem(item)
                    }
                }
            }
    }
}
