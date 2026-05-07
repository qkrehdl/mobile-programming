package com.example.mp0801

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MyAppNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable(
            route = "detail/{userId}",
            arguments = listOf(navArgument("userId"){ type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            DetailScreen(userId = userId ?: "Unknown", navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val sampleUsers = listOf ("Alice 123", "Bob 456", "Carol 789")
    Column( modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("사용자 목록", modifier = Modifier.align(Alignment.CenterHorizontally))
        sampleUsers.forEach { userId ->
            Button(onClick = { navController.navigate("detail/$userId") }) {
                Text("Go to Detail $userId")
            }
        }
    }
}

@Composable
fun DetailScreen(userId: String, navController: NavController) {
    Column( modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detail Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("User ID: $userId", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }
    }
}

