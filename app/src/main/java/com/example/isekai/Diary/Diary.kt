package com.example.isekai.Diary

class Diary {
    /**set Data*/
    var title: String? = null
    var image: String? = null
    var attraction: String? = null
    var context: String? = null
    var timeStamp: String? = null

    constructor(){}

    constructor(
        title: String, image: String, attraction: String, context: String, timeStamp: String
    ){
        this.title = title
        this.image = image
        this.attraction = attraction
        this.context = context
        this.timeStamp = timeStamp
    }

//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//            "title" to title,
//            "image" to image,
//            "attraction" to attraction,
//            "context" to context,
//            "timeStamp" to timeStamp
//        )
//    }
}