package dev.alejr.healthassistant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alejr.healthassistant.R
import dev.alejr.healthassistant.model.Task
import dev.alejr.healthassistant.model.TaskSummaryResponse



@Composable
fun TaskSummary(
    summary: TaskSummaryResponse,
    loading: Boolean,
    patientName: String,
    onAccomplishTask: (task: Task) -> Unit,
    onSignOut: () -> Unit
) {

    val state = rememberScrollState()



    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(state)
    ) {

        Text(
            text = stringResource(R.string.greeting) + " " +  patientName,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = stringResource(R.string.your_tasks),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        TaskList(
            tasks = summary.overdue,
            title = stringResource(R.string.overdue_tasks),
            onAccomplishTask,
            loading
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaskList(
            tasks = summary.open,
            title = stringResource(R.string.open_tasks),
            onAccomplishTask,
            loading
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaskList(
            tasks = summary.completed,
            title = stringResource(R.string.closed_tasks),
            onAccomplishTask,
            loading
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                onSignOut()
            }, modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_out),
                fontSize = 16.sp
            )
        }
    }
}