package com.example.firstkotlin.filter

import java.beans.PropertyEditorSupport

class NullStringEditor : PropertyEditorSupport() {

    @Throws(IllegalArgumentException::class)
    override fun setAsText(text: String) {
        value = if ("null" == text) {
            null
        } else {
            text
        }
    }
}