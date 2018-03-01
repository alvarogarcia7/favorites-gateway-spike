package com.example.favoritesgateway

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = [FavoritesGatewayController::class])
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


@RestController
@Configuration
class FavoritesGatewayController {

    @PostMapping("/favorites/user/{userId}/country/{country}/language/{language}/graphql")
    fun favorites(
            @PathVariable("userId") userId: String,
            @PathVariable("country") country: String,
            @PathVariable("language") language: String): Any {
        println(userId)
        println(country)
        println(language)

//        SchemaParser().parse(Paths.get("schema.graphqls").toFile())
//                .makeExecutableSchema()
        throw IllegalArgumentException("")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException() {
    }
}

class Query : GraphQLQueryResolver {
    private val productDao: ProductDao? = null
    fun getUserFavorites(userId: String, country: String, language: String): List<Favorite> {
        return productDao?.fetch(userId)!!.map { Favorite(id = "1", product = it) }
    }
}

class ProductDao {
    fun fetch(userId: String): List<Product> {
        return listOf(Product("1", "google.com/image.jpg"), Product("2", "yahoo.com/image.jpg"))
    }
}

data class Favorite(val id: String, val product: Product)
data class Product(val id: String, val image: String)

class FavoriteResolver : GraphQLResolver<Favorite> {
    private val authorDao: AuthorDao? = null

    fun getAuthor(post: Post): Author {
        return authorDao!!.getAuthorById(post.getAuthorId())
    }
}