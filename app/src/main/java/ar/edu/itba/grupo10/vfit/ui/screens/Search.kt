package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
) {

    val windowSize = rememberWindowInfo()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            var search by rememberSaveable { mutableStateOf("") }

           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
           ) {


                Row(

                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                ){

                    if(windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){
                        TextField(
                            value = search,
                            onValueChange = { search = it },
                            label = { Text(stringResource(R.string.search)) },
                            singleLine = true,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 25.dp))
                                .width(500.dp),
                            trailingIcon = {
                                IconButton(onClick = { /*TODO: search*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            }
                        )
                    }else{

                            TextField(
                            value = search,
                            onValueChange = { search = it },
                            label = { Text(stringResource(R.string.search)) },
                            singleLine = true,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 25.dp)),
                            trailingIcon = {
                                IconButton(onClick = { /*TODO: search*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            }
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier.width(160.dp),
                        onClick = { search="" }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }


                }
           }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,

                ){
                Text(
                    text = stringResource(R.string.look_for_results),
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top = 40.dp)
                )
                if(windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){
                Image(
                    modifier = Modifier
                        .width(700.dp)
                        .padding(top = 25.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp)),
                    painter = painterResource(id = R.drawable.background_search),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )}
                else{
                    Image(
                        modifier = Modifier
                            .width(400.dp)
                            .padding(top = 25.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp)),
                        painter = painterResource(id = R.drawable.background_search),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }

        }
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun SearchScreenPreview() {
    VFitTheme {
        SearchScreen()
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun SearchScreenPreview1() {
    VFitTheme {
        SearchScreen()
    }
}
