package com.youki.dex.utils

/** Task IDs and base packages from `am stack list`, including HyperOS's colon format. */
internal fun parseShellTasks(output: String): List<Pair<Int, String>> {
    val task = Regex("""^\s*taskId=(\d+)(?::\s*|[^\r\n]*?\bbaseIntent=)([a-zA-Z0-9_.]+)/""", RegexOption.MULTILINE)
    return task.findAll(output).mapNotNull { match ->
        match.groupValues[1].toIntOrNull()?.let { it to match.groupValues[2] }
    }.toList()
}
