package com.atlassian.performance.tools.jiraactions.api.memories

import com.atlassian.performance.tools.jiraactions.api.page.IssuePage

interface JqlMemory : Memory<String> {
    fun observe(issuePage: IssuePage)
    fun recall (func : (String) -> Boolean) : String?
    fun recall(func : JqlTypes) : List<String> = throw NotImplementedError("Override this method")
}
