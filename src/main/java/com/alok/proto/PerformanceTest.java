package com.alok.proto;

import com.alok.json.JPerson;
import com.alok.models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerformanceTest {
    public static void main(String[] args) {

        //json ser
        JPerson person = new JPerson();
                person.setName("sam");
                person.setAge(10);
        ObjectMapper mapper = new ObjectMapper();

        Runnable json = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(person);
                JPerson person1= mapper.readValue(bytes,JPerson.class);
            }catch(Exception e){
                e.printStackTrace();
            }

        };

        //protobuf  deser
        Person sam = Person.newBuilder()
                .setName("sam")
                .setAge(10)
                .build();

        Runnable proto = () -> {
            try {
                byte[] bytes = sam.toByteArray();
                Person sam1= Person.parseFrom(bytes);
            }catch(Exception e){
                e.printStackTrace();
            }
        };


        for (int j = 0; j < 5; j++) {
            runPerformanceTest(json,"JSON");
            runPerformanceTest(proto,"Proto");

        }
    }

    private static void runPerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();
        for (int i=0; i <= 1_000_000; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();

        System.out.println(
                method + ":" +(time2-time1) + "ms"
        );
    }

}
