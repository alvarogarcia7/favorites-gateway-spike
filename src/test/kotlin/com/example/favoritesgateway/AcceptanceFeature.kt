package com.example.favoritesgateway

import arrow.core.Either
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

//@ContextConfiguration(classes = arrayOf(AcceptanceTest.BeanOverridesConfig::class))
@EnableAutoConfiguration
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [DemoApplication::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceFeature {

    @LocalServerPort
    var port: Int? = null

    @Autowired
    private var configuration: Configuration = Configuration()

    @Before
    fun setUp() {
        FuelManager.instance.basePath = "http://localhost:" + port!!
    }

    @Test
    fun `receives the parameters correctly`() {
        val response = favorites(listOf(Pair("user", "userid1"), Pair("country", "ES"), Pair("language", "es_CA")))

        assertThat(response.isRight())
        response.bimap(
                {
                    fail("expected a right")
                },
                { (response, result) ->
                    val get = result.get()
                    println(get)
//                    assertThat(response.statusCode).isEqualTo(200)
//                    when (result) {
//                        is com.github.kittinunf.result.Result.Success -> {
//                            println(result.value)
////                            assertThat(result.value.city).isEqualTo("Львов")
//                        }
//                        else -> {
//                            fail("expected a Result.success")
//                        }
//                    }
                })
    }

    private fun favorites(parameterList: List<Pair<String, String>>): Either<Exception, Pair<Response, com.github.kittinunf.result.Result<String, FuelError>>> {

        val url = generateUrl(parameterList)
        val request = url.httpPost().header("Content-Type" to "application/json")
        try {
            val (_, response, result) = request.responseString()
            return Either.right(Pair(response, result))
        } catch (e: Exception) {
            e.printStackTrace()
            return Either.left(e)
        }

    }

    private fun generateUrl(list: List<Pair<String, String>>): String {

        fun findParam(list: List<Pair<String, String>>, id: String): String {
            val x = list.filter { it.first == id }.first()
            return "${x.first}/${x.second}"
        }

        val parameters = list


        val url = "/favorites/${findParam(list, "user")}/${findParam(list, "country")}/${findParam(list, "language")}/graphql"
        return url

    }

    @org.springframework.context.annotation.Configuration
    @ComponentScan(basePackageClasses = arrayOf(Configuration::class))
    internal class BeanOverridesConfig {
    }


}