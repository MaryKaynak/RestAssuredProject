import Model.City;
import io.restassured.http.ContentType;
import Model.Country;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class CountryCityTest extends BaseTest {

    private String CountryId;

    @Test
    public void getBasePath() {
        given()
                .when()
                .log().body()
                .get()
                .then()
                .log().body()
                .statusCode( 200 )
        ;
    }

    @Test
    public void getCountries() {
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .log().body()
                .get( "/school-service/api/countries" )
                .then()
                .statusCode( 200 )
        ;
    }
    @Test
    public void getCity() {
        given()
                .cookies( cookies )
                .when()
                .log().body()
                .get( "/school-service/api/cities" )
                .then()
                .log().body()
                .statusCode( 200 )
        ; }


    @Test
    public void createCountry() {
        Country country = new Country();
        country.setName( name );
        country.setCode( code );

        // creating country
        CountryId = given()
                .cookies( cookies )
                .body( country )
                .contentType( ContentType.JSON )
                .when()
                .log().body()
                .post( "/school-service/api/countries" )
                .then()
                .log().body()
                .statusCode( 201 )
                .extract().jsonPath().getString( "id" );

        // deleting country
//        given()
//                .cookies( cookies )
//                .when()
//                .log().body()
//                .delete( "/school-service/api/countries/" + countryId )
//                .then()
//                .log().body()
//                .statusCode( 200 )
//        ;
    }
    @Test (dependsOnMethods = {"createCountry"})
    private void CreateCity(){
        City city = new City();
        city.setName(name);

        Country country = new Country();
        country.setId( CountryId );
        city.setCountry( country );

        given()
                .cookies(cookies)
                .body(city)
                .log().body()
                .contentType(ContentType.JSON)
                .when()
                .log().body()
                .post("/school-service/api/cities")
                .then()
                .log().body()
                .statusCode(201);
//                .extract().jsonPath().getString("id");
//        // deleting country
//        given()
//                .cookies( cookies )
//                .when()
//                .log().body()
//                .delete( "/school-service/api/cities/" + city )
//                .then()
//                .log().body()
//                .statusCode( 200 )
//        ;
    }



}
