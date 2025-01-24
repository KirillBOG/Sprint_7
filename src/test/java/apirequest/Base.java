package apirequest;

import io.restassured.RestAssured;

public class Base {

    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

}