package org.ekosutrisno;

import lombok.AllArgsConstructor;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * @author Eko Sutrisno
 * Sabtu, 08/10/2022 22.17
 */
@AllArgsConstructor
@Path("/api/v1/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @GET
    @Transactional
    public Response getPersons() {
        var person = List.of(
                Person.builder()
                        .name("Eko")
                        .birth(LocalDate.of(1996, Month.MAY, 20))
                        .status(Status.Deceased)
                        .build(),
                Person.builder()
                        .name("Eko")
                        .birth(LocalDate.of(1996, Month.MAY, 20))
                        .status(Status.Alive)
                        .build(),
                Person.builder()
                        .name("Eko")
                        .birth(LocalDate.of(1996, Month.MAY, 20))
                        .status(Status.Alive)
                        .build(),
                Person.builder()
                        .name("Sutrisno")
                        .birth(LocalDate.of(1996, Month.MAY, 20))
                        .status(Status.Alive)
                        .build()
        );

        if (Person.count() > 0)
            return Response.ok(Person.listAll()).build();
        else
            persistAll(person);
        return Response.ok(person).build();
    }

    @GET
    @Path("/count")
    @Transactional
    public Response getPersonsDistinctCount(
            @QueryParam("name") @DefaultValue("") String productName,
            @QueryParam("brand") @DefaultValue("") String brandName
    ) {
        /*
         * This Query will be generated valid Query As bellow:
         * select
         *     count(distinct person0_.status) as col_0_0_
         * from
         *     Person person0_
         */
        long person = Person.find("SELECT DISTINCT p.status FROM Person p").count();

        return Response.ok(person).build();
    }

    /*Saving Some Data Dummy*/
    private void persistAll(List<Person> personList) {
        Person.persist(personList);
    }
}