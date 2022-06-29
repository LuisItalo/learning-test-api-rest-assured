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
    @Test(priority = 0) //identifica metodo
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/jsons/pet1.json");

        //rest- assured
        given()
                .contentType("application/json") //comum em api rest - antigos era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri) //inclui animal
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("ralf"))  //check nome do chachorro
                .body("status", is("available")) //check status
                .body("category.name", is("LI1232DUDA"))
                .body("tags.name", contains("data"))
        ;
    }
    @Test(priority = 1)
    public void consultarPet(){

        String petId = "202220061991";

        String token =
        given()
                .contentType("application/jason")
                .log().all()
        .when()
                .get(uri + "/" + petId) //consulta animal
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("ralf"))
                .body("category.name", is("LI1232DUDA"))
                .body("status", is("available"))
        .extract()
                .path("category.name")
        ;
        System.out.println("o token é "+ token);
    }
    @Test(priority = 2)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/jsons/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri) //altera
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("ralf"))
                .body("status", is("vendido"))
                ;
    }
    @Test(priority = 3)
    public void excluirPet(){
        String petId = "202220061991";

        given()
                .contentType("application/jason")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
                ;
    }
}
