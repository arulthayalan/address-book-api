package au.com.xyz.addressbook.api


import au.com.xyz.addressbook.entity.PhoneContactEntity
import au.com.xyz.addressbook.model.AddressBook
import au.com.xyz.addressbook.respository.AddressBookRepository
import au.com.xyz.addressbook.Application
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = [Application.class])
class AddressBookControllerFunctionalSpec extends Specification {

    @LocalServerPort
    int port

    @Autowired
    AddressBookRepository addressBookRepository

    def "Should return all address books and return HTTP status 200"() {

        expect:
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(new AddressBook([name: 'Book1', phoneContacts: book1PhoneContactEntities()]))
                .when()
                .post("/address-book-api/addressbook")
                .then()
                .statusCode(200)

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/address-book-api/addressbooks")
                .then()
                .statusCode(200)
                .body("data.addressBooks.size()", CoreMatchers.is(1))
                .body("data.addressBooks[0].phoneContacts.size()", CoreMatchers.is(3))
    }

    def "Should return all phone contacts and return HTTP status 200"() {

        given:
        addressBookRepository.deleteAll()
        createAddressBooks()

        expect:

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/address-book-api/addressbooks/contacts/ALL")
                .then()
                .statusCode(200)
                .body("data.phoneContacts.size()", CoreMatchers.is(6))
    }

    def "Should return unique phone contacts and return HTTP status 200"() {

        given:
        addressBookRepository.deleteAll()
        createAddressBooks()

        expect:

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/address-book-api/addressbooks/contacts/UNIQUE")
                .then()
                .statusCode(200)
                .body("data.phoneContacts.size()", CoreMatchers.is(2))
    }

    def createAddressBooks() {

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(new AddressBook([name: 'Book1', phoneContacts: book1PhoneContactEntities()]))
                .when()
                .post("/address-book-api/addressbook").then()
                .statusCode(200)

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(new AddressBook([name: 'Book2', phoneContacts: book2PhoneContactEntities()]))
                .when()
                .post("/address-book-api/addressbook")
                .then()
                .statusCode(200)
    }

    def List<PhoneContactEntity> book1PhoneContactEntities() {
        [new PhoneContactEntity([name: 'Bob', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Mary', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Jane', mobileNumber: '123456'])]
    }

    def List<PhoneContactEntity> book2PhoneContactEntities() {
        [new PhoneContactEntity([name: 'Mary', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'John', mobileNumber: '123456']),
         new PhoneContactEntity([name: 'Jane', mobileNumber: '123456'])]
    }

}
