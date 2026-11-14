package com.youki.dex.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ShellTaskParserTest {
    @Test fun readsHyperOsTaskFormat() {
        val output = checkNotNull(javaClass.getResource("/tasks-hyperos-actual.txt")).readText()
        assertEquals(listOf(
            263 to "com.youki.dex", 268 to "com.android.vending",
            265 to "moe.shizuku.privileged.api", 2 to "com.miui.home",
            254 to "com.android.settings", 261 to "example.browser",
            257 to "example.media"
        ), parseShellTasks(output))
    }

    @Test fun alsoReadsBaseIntentFormat() {
        assertEquals(listOf(42 to "example.app"), parseShellTasks(
            "  taskId=42 bounds=[0,0][800,600] running=true visible=true baseIntent=example.app/.Main"
        ))
    }

    @Test fun ignoresUnknownTasksAndConfigurationNumbers() {
        assertEquals(emptyList<Pair<Int, String>>(), parseShellTasks(
            "RootTask id=3 bounds=[0,0][800,600]\n  taskId=4: unknown bounds=[0,0][800,600]\n"
        ))
    }

    @Test fun keepsTaskIdsForMultipleWindowsOfOneApp() {
        assertEquals(listOf(5 to "example.app", 6 to "example.app"), parseShellTasks(
            "taskId=5: example.app/.First\ntaskId=6: example.app/.Second"
        ))
    }

    @Test fun doesNotTreatTopActivityOrUnrelatedTextAsAnotherTask() {
        assertEquals(listOf(7 to "example.base"), parseShellTasks(
            "  taskId=7: example.base/.Main topActivity=ComponentInfo{example.top/.Main}\nnoise taskId=8: example.noise/.Main"
        ))
    }

    @Test fun emptyTaskListStaysEmpty() {
        assertEquals(emptyList<Pair<Int, String>>(), parseShellTasks(""))
    }
}
