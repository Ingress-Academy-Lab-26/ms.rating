package az.ingress.mapper

import az.ingress.dao.entity.RatingDetailsEntity
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.ingress.mapper.RatingDetailsMapper.RATING_DETAILS_MAPPER


class RatingDetailsMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "BuildProductDetailsTest"() {
        given:
        def productId = random.nextObject(Long)
        def ratingCount = random.nextObject(Integer)
        def averageRating = random.nextObject(BigDecimal)

        when:
        def entity = RATING_DETAILS_MAPPER.buildRatingDetails(productId, ratingCount, averageRating)
        then:
        entity.productId == productId
        entity.voteCount == ratingCount
        entity.averageRating == entity.averageRating
    }

    def UpdateRatingDetailsTest() {
        given:
        def ratingCount = random.nextObject(Integer)
        def averageRating = random.nextObject(BigDecimal)
        def entity = random.nextObject(RatingDetailsEntity)

        when:
        RATING_DETAILS_MAPPER.updateRatingDetails(entity, ratingCount, averageRating)

        then:
        entity.averageRating == averageRating
        entity.voteCount == ratingCount


    }


}
