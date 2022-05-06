package com.example.composeapp.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.R

data class DrawerContent(
    val header: @Composable () -> Unit,
    val items: List<List<DrawerItem>>
)

data class DrawerItem(val icon: Painter, val label: String, val onClick: () -> Unit = {})

@Composable
fun DrawerContentLayout(
    content: DrawerContent,
    selectedLabel: String,
    modifier: Modifier = Modifier,
    onDrawerItemSelected: (String) -> Unit = {}
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState(0))) {
        content.header()
        content.items.forEach { list ->
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
            list.forEach { item ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (selectedLabel == item.label)
                            MaterialTheme.colorScheme.primary.copy(alpha = .25f)
                        else
                            MaterialTheme.colorScheme.background
                    )
                    .clickable {
                        item.onClick()
                        onDrawerItemSelected(item.label)
                    }) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(CenterStart)
                    ) {
                        Image(
                            painter = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(32.dp),
                            colorFilter = if (selectedLabel == item.label)
                                ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            else
                                null
                        )
                        Text(
                            text = item.label,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(24.dp, 0.dp, 0.dp, 0.dp),
                            color = if (selectedLabel == item.label)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDrawerHeader(name: String, email: String, painter: Painter) {
    Column(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)) {
        RoundImage(
            painter = painter,
            modifier = Modifier
                .size(96.dp)
                .padding(16.dp)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(text = email, fontSize = 20.sp, modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewDrawerContentLayout() {
    val content = DrawerContent(
        header = {
            ProfileDrawerHeader(
                name = "Bob Ross",
                email = "bob_ross42@yahoo.com",
                painter = painterResource(id = R.drawable.download)
            )
        },
        items = listOf(
            listOf(
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 1"
                ),
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 2"
                ),
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 3"
                ),
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 4"
                ),
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 5"
                )
            ),
            listOf(
                DrawerItem(
                    icon = painterResource(id = R.drawable.ic_profile),
                    label = "Item 6"
                )
            )
        )
    )
    DrawerContentLayout(content = content, "") {}
}