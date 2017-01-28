package org.balaguta.currconv.app

import org.balaguta.currconv.data.entity.User
import spock.lang.Specification

class AppUserSpec extends Specification {

    def "user proxy methods"() {
        def userEntity = new User()
        userEntity.firstName = "Joe"
        userEntity.lastName = "Sample"

        when:
        def user = new AppUser("joe@example.com", "", userEntity, Collections.emptyList())

        then:
        user.firstName == "Joe"
        user.lastName == "Sample"
        user.fullName == "Joe Sample"
    }
}
