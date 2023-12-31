package com.example.ebrain.feature_task.presentation.task.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebrain.ui.theme.Dark
import java.time.LocalDate
import java.util.Calendar

@Composable
fun CurrentDateSection(
    padding: PaddingValues
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = padding.calculateTopPadding())
                .shadow(
                    elevation = 30.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
                    ambientColor = MaterialTheme.colorScheme.background,
                    spotColor = Dark
                )
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center) {

                    Text(
                        text = LocalDate.now().dayOfMonth.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
                Spacer(modifier = Modifier.padding(4.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = LocalDate.now().month.toString(),
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = LocalDate.now().year.toString(),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                Text(
                    text = LocalDate.now().dayOfWeek.toString(),
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}