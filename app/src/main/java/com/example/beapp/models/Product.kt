package com.example.beapp.models
class Product {

    var name: String? = null
    var price: Int? = null
    var number: Int? = null
    var date: String? = null
    var imgURL: String? = null

    constructor()

    constructor(name: String?, price: Int?, number: Int?, date: String?) {
        this.name = name
        this.price = price
        this.number = number
        this.date = date
    }

    constructor(name: String?, price: Int?, number: Int?, date: String?, imgURL: String?) {
        this.name = name
        this.price = price
        this.number = number
        this.date = date
        this.imgURL = imgURL
    }

}