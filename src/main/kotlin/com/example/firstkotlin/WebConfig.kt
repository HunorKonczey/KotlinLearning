package com.example.firstkotlin

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.google.gson.Gson
import org.bson.types.ObjectId
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.List


@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    private val gson: Gson = Gson()

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:9000", "http://localhost:9001")
            .allowCredentials(true)
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        val messageConverter = StringHttpMessageConverter()
        messageConverter.setSupportedMediaTypes(
            listOf(
                MediaType.APPLICATION_JSON,
                MediaType.TEXT_PLAIN,
                MediaType.ALL
            )
        )
        converters.add(messageConverter)
        val builder = Jackson2ObjectMapperBuilder()
        builder.serializerByType(ObjectId::class.java, ToStringSerializer())
        val converter = MappingJackson2HttpMessageConverter(builder.build())
        converters.add(converter)
    }
}