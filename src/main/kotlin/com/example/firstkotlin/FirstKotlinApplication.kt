package com.example.firstkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FirstKotlinApplication

fun main(args: Array<String>) {
	// app start
	runApplication<FirstKotlinApplication>(*args)
}
