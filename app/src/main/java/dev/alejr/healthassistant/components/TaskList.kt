package dev.alejr.healthassistant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.alejr.healthassistant.R
import dev.alejr.healthassistant.model.Task
import dev.alejr.healthassistant.model.TaskSummaryResponse

@Composable
fun TaskList(tasks: Array<Task>, title: String, onAccomplishTask: (task: Task) -> Unit, loading: Boolean ){

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if(!loading) tasks.forEach { task ->
            TaskCard(task, onAccomplishTask)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if(!loading && tasks.isEmpty()) EmptyState(stringResource(R.string.empty_task))


        if(loading){
            CircularProgressIndicator()
        }
    }

}