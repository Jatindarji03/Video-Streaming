package com.example.videostreaming.route

enum class Route(val route:String) {
    Login("login"),
    Signup("signup"),
    Home("home"),
    Main("main"),
    Favourites("favourites"),
    Search("search"),
    Profile("profile"),
    ContentInfo("content_info/{id}"),
    DisplayContent("display_content/id")
}