package petstore;



import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson (String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    @Test //identifica metodo
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/jsons/pet1.json");

        //rest- assured
        given()
                .contentType("application/json") //comum em api rest - antigos era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("ralf"))  //check nome do chachorro
                .body("status", is("available")) //check status
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        ;
    }
}
