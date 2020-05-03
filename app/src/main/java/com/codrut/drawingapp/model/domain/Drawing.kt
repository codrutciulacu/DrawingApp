package com.codrut.drawingapp.model.domain

import com.google.firebase.firestore.Exclude

data class Drawing constructor(
    @Exclude var id: String,
    var name: String,
    var content: String
) {
    constructor() : this("", "", "") {}
}