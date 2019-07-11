package com.atlassian.performance.tools.jiraactions.api.memories.adaptive

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

internal class WebDriverMock : WebDriver {

    private val elements: MutableList<WebElement> = mutableListOf(
        WebElementMock("By.id:", "Some attribute 1", true, null),
        WebElementMock("By.cssSelector:", "Some attribute 2", true, null),
        WebElementMock("By.id:", "Some attribute 3", true,
            WebElementMock("By.cssSelector:", "Some attribute 4", true,
                WebElementMock("By.id:", "Some attribute 5", true, null)))

    )

    override fun findElements(by: By?): MutableList<WebElement> {
        return elements.filter { it.text == by.toString().split(" ")[0].trim() }.toMutableList()
    }

    override fun findElement(by: By?): WebElement {
        return elements
            .filter { it.text == by.toString().split(" ")[0].trim() }
            .getOrElse(0) { WebElementMock("sad", "sad", true, null) }
    }


    override fun getWindowHandles(): MutableSet<String> {
        throw NotImplementedError()
    }

    override fun getWindowHandle(): String {
        throw NotImplementedError()
    }

    override fun getPageSource(): String {
        throw NotImplementedError()
    }

    override fun navigate(): WebDriver.Navigation {
        throw NotImplementedError()
    }

    override fun manage(): WebDriver.Options {
        throw NotImplementedError()
    }

    override fun getCurrentUrl(): String {
        throw NotImplementedError()
    }

    override fun getTitle(): String {
        throw NotImplementedError()
    }

    override fun get(url: String?) {
        throw NotImplementedError()
    }

    override fun switchTo(): WebDriver.TargetLocator {
        throw NotImplementedError()
    }

    override fun close() {
        throw NotImplementedError()
    }

    override fun quit() {
        throw NotImplementedError()
    }
}
