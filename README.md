# endowments-website

endowments_tribunal : angular app (discarded)

tribunalTs : React App for frontend

EndowmentsTribunal : SpringBoot(3.4.0) backend Java 21, gradle build(8.4.x)
    dependencies: 
        spring dev tools,
        spring starter actuator,
        spring starter web,
        spring data jpa,
        lombok,
        mysql driver,
        spring security,
        spring starter validation,
        spring oauth resource server .

example usage:

    GET : fetch all documents  
        url - http://localhost:8080/api/documents
        response - [{"id", "pdf_url", "date", "panel" }, {"id", "pdf_url", "date", "panel" },  .....]

    GET : fetch document with id 5 (example) 
        url - http://localhost:8080/api/documents/5
        response - {"id", "pdf_url", "date", "panel" }

    POST : upload document
        url - http://localhost:8080/api/documents
        request body - {"pdf_url", "date", "panel"}
        response - http status 200 - ok, 400 - failed, etc

    PUT : update document with id 5
        url - http://localhost:8080/api/documents/5
        request body - {"pdf_url", "date", "panel"}
        response - http status 200 - ok, 400 - failed, etc

    DELETE : delete document with id 5
        url - http://localhost:8080/api/documents/5
        response - http status 200 - ok, 400 - failed, etc

    
