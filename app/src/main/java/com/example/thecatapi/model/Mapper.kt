package com.example.thecatapi.model


    internal fun Cat.toCat(): Cat {
        return Cat(id = id, url = url)
    }
