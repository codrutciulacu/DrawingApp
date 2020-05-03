package com.codrut.drawingapp.model.domain

data class Drawing constructor(var name: String,
                   var content: String) {
    constructor() : this("", "") {}
}