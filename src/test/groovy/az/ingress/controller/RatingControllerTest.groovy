package az.ingress.controller


import az.ingress.exception.ErrorHandler
import az.ingress.model.request.RatingRequest
import az.ingress.service.abstraction.RatingService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import java.net.http.HttpHeaders
import static org.springframework.http.HttpHeaders.AUTHORIZATION;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post


class RatingControllerTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private RatingService ratingService
    private MockMvc mockMvc

    void setup() {
        ratingService = Mock()
        def ratingController = new RatingController(ratingService)
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }


    @Unroll
    def "TestInsertOrUpdateRating "() {
        given:
        def url = "/v1/ratings"
        def accessToken = random.nextObject(String)
        def ratingRequest = new RatingRequest(userId, productId, score)
        def requestBody = """   
                                {
                                 "userId": "$ratingRequest.userId",
                                 "productId": "$ratingRequest.productId",
                                 "score": "$ratingRequest.score"
                                 }                                 
                            """

        when:
        def result = mockMvc.perform(post(url)
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn()

        then:
        1 * ratingService.insertOrUpdateRating(ratingRequest)

        def response = result.response
        response.status == HttpStatus.CREATED.value()

        where:
        userId | productId | score
        1L     | 3L        | 3
        2L     | 4L        | 5
        3L     | 1L        | 4
    }
    def "TestGetUserRating"() {
        given:
        def productId = random.nextLong()
        def accessToken = random.nextObject(String)
        def userId = random.nextLong()
        def url = "/v1/ratings"

        when:
         def result = mockMvc.perform(get(url)
                .header(AUTHORIZATION, accessToken)
                .param("productId", productId.toString())
                .param("userId", userId.toString()))
                 .andReturn()
        then:
        1 * ratingService.getUserRating(productId, userId)
        def response = result.response
        response.status == HttpStatus.OK.value()



    }

    def "TestDeleteRating"(){
        given:
        def productId = random.nextObject(Long)
        def accessToken = random.nextObject(String)
        def userId = random.nextObject(Long)
        def url = "/v1/ratings"

        when:
        def result = mockMvc.perform(delete(url)
                .header(AUTHORIZATION, accessToken)
                .param("productId", productId.toString())
                .param("userId", userId.toString()))
                .andReturn()

        then:
        1 * ratingService.deleteRating(productId,userId)
        def response = result.response
        response.status == HttpStatus.NO_CONTENT.value()
    }


}
