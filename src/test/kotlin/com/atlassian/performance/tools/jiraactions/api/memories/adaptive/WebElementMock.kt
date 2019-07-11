package com.atlassian.performance.tools.jiraactions.api.memories.adaptive

import org.openqa.selenium.*

internal class WebElementMock(
    private val id: String,
    private val attribute: String,
    private val display: Boolean,
    private val elementTree: WebElementMock?
) : WebElement {

    override fun click() {}

    override fun isDisplayed(): Boolean {
        return this.display
    }

    override fun findElements(by: By?): MutableList<WebElement> {
        val list = this.elementTree?.findElements(by) ?: mutableListOf()
        if (this.id == by.toString()) {
            list.add(this)
        }
        return list
    }

    override fun getText(): String {
        return this.id
    }

    override fun getAttribute(name: String?): String {
        return this.attribute
    }

    override fun clear() {
        throw NotImplementedError()
    }

    override fun submit() {
        throw NotImplementedError()
    }

    override fun getLocation(): Point {
        throw NotImplementedError()
    }

    override fun <X : Any?> getScreenshotAs(target: OutputType<X>?): X {
        throw NotImplementedError()
    }

    override fun findElement(by: By?): WebElement {
        throw NotImplementedError()
    }

    override fun getTagName(): String {
        throw NotImplementedError()
    }

    override fun getSize(): Dimension {
        throw NotImplementedError()
    }

    override fun isSelected(): Boolean {
        throw NotImplementedError()
    }

    override fun isEnabled(): Boolean {
        throw NotImplementedError()
    }

    override fun sendKeys(vararg keysToSend: CharSequence?) {
        throw NotImplementedError()
    }

    override fun getRect(): Rectangle {
        throw NotImplementedError()
    }

    override fun getCssValue(propertyName: String?): String {
        throw NotImplementedError()
    }
}
