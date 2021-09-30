package com.example.isekai.Diary

class Diary {
    /**set Data*/
    var location :String? = null
    var title:String? = null
    var story:String? = null
    var image:String? = null
    constructor(){}

    constructor(
        location:String?,
        title:String?,
        story:String?,
        image:String?
    ){
        this.location = location
        this.title = title
        this.story = story
        this.image = image
    }
}